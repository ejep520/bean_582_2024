package edu.wsu.bean_582_2024.ApartmentFinder.dao;

import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import java.util.ArrayList;
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
          .createQuery("SELECT e FROM unit e")
          .getResultList());
  }

  @Override
  @Transactional(propagation = Propagation.NEVER)
  public void save(Unit unit) {
    executeInsideTransaction(entityManager -> entityManager.persist(unit));
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
      manager.createQuery("delete from unit where id = :id")
          .setParameter("id", unit.getId())
          .executeUpdate();
    } catch (PersistenceException err) {
      transaction.rollback();
    }
    transaction.commit();
  }
  
  public List<Unit> find(String searchKey) {
    if ((searchKey == null) || searchKey.isEmpty() || searchKey.isBlank()) return getAll();
    List<Unit> returnValue = new ArrayList<>();
    for (Object o : entityManager
        .createQuery("SELECT e from unit e where lower(e.address) LIKE :searchKey OR lower(e.livingRoom) LIKE :searchKey OR lower(e.kitchen) LIKE :searchKey")
        .setParameter("searchKey", searchKey.toLowerCase())
        .getResultList()) {
      try {
        returnValue.add((Unit) o);
      } catch (ClassCastException err) {
        logger.atWarn().log(String.format("Unable to cast to Unit. %s", err));
      }
    }
    return returnValue;
  }
  
  public Long count() {
    return (Long) entityManager
        .createQuery("select count (e.id) from unit e")
        .getSingleResult();
  }
  
  public List<Unit> findByUser(User user) {
    return castList(Unit.class,
        entityManager
            .createQuery("select e from unit e where user = :userKey")
            .setParameter("userKey", user)
            .getResultList());
  }
  
  public List<Unit> findOwnedUnitsByFilter(User user, String searchKey) {
    return castList(Unit.class,
        entityManager
            .createQuery("select e from unit e where User = :userKey and (lower(e.address) like :searchKey or lower(e.kitchen) like :searchKey or lower(e.livingRoom) like :searchKey)")
            .setParameter("userKey", user)
            .setParameter("searchKey", searchKey.toLowerCase())
            .getResultList());
  }
}
