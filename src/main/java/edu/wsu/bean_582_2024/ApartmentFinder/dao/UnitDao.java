package edu.wsu.bean_582_2024.ApartmentFinder.dao;

import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UnitDao extends DaoHelper implements Dao<Unit>{
  
  public UnitDao(EntityManagerFactory entityManagerFactory) {
    super(entityManagerFactory, LoggerFactory.getLogger(UnitDao.class));
  }


  @Override
  public Optional<Unit> get(long id) {
    return Optional.ofNullable(entityManager.find(Unit.class, id));
  }

  @Override
  public List<Unit> getAll() {
    return castList(Unit.class,
        entityManager
          .createQuery("SELECT e FROM Unit e")
          .getResultList());
  }

  @Override
  @Transactional(propagation = Propagation.NEVER)
  public void save(Unit unit) {
    EntityManager localManager = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = localManager.getTransaction();
    transaction.begin();
    localManager.persist(unit);
    transaction.commit();
  }

  @Override
  public void update(Unit unit) {
      executeInsideTransaction(entityManager -> entityManager.merge(unit));
  }
  
  @Override
  @Transactional(propagation = Propagation.NEVER)
  public void delete(Unit unit) {
    EntityManager manager = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = manager.getTransaction();
    transaction.begin();
    try {
      manager.createQuery("delete from Unit where id = :id")
          .setParameter("id", unit.getId())
          .executeUpdate();
    } catch (PersistenceException err) {
      transaction.rollback();
    }
    transaction.commit();
  }
  
  public List<Unit> find(String searchKey) {
    if ((searchKey == null) ||  searchKey.isBlank()) return getAll();
    return castList(Unit.class, entityManager
        .createQuery("SELECT e from Unit e where lower(e.address) LIKE :searchKey OR lower(e.livingRoom) LIKE :searchKey OR lower(e.kitchen) LIKE :searchKey")
        .setParameter("searchKey", searchKey.toLowerCase())
        .getResultList());
  }
  
  public Long count() {
    return (Long) entityManager
        .createQuery("select count (e.id) from Unit e")
        .getSingleResult();
  }
  
  public List<Unit> findByUser(User user) {
    return castList(Unit.class,
        entityManager
            .createQuery("select e from Unit e where e.user = :userKey")
            .setParameter("userKey", user)
            .getResultList());
  }
  
  public List<Unit> findOwnedUnitsByFilter(User user, String searchKey) {
    return castList(Unit.class,
        entityManager
            .createQuery("select e from Unit e where e.user = :userKey and (lower(e.address) like :searchKey or lower(e.kitchen) like :searchKey or lower(e.livingRoom) like :searchKey)")
            .setParameter("userKey", user)
            .setParameter("searchKey", searchKey.toLowerCase())
            .getResultList());
  }
}
