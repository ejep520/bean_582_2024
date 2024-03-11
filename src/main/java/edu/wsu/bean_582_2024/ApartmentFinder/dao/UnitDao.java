package edu.wsu.bean_582_2024.ApartmentFinder.dao;

import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UnitDao extends DaoHelper implements Dao<Unit>{
  
  public UnitDao(EntityManager entityManager) {
    super(entityManager, LoggerFactory.getLogger(UnitDao.class));
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
  @Transactional(propagation = Propagation.NEVER)
  public void update(Unit unit, Object... params) {
      Objects.requireNonNull(unit)
          .setAddress((String) Objects.requireNonNull(params[0]));
      unit.setBedrooms((Integer) Objects.requireNonNull(params[1]));
      unit.setBathrooms((Double) Objects.requireNonNull(params[2]));
      unit.setLivingRoom((String) Objects.requireNonNull(params[3]));
      unit.setKitchen((String) Objects.requireNonNull(params[4]));
      unit.setFeatured((Boolean) Objects.requireNonNull(params[5]));
      unit.setUser((User) Objects.requireNonNull(params[6]));
      executeInsideTransaction(entityManager -> entityManager.merge(unit));
  }

  @Override
  @Transactional(propagation = Propagation.NEVER)
  public void delete(Unit unit) {
    executeInsideTransaction(entityManager -> entityManager.remove(unit));
  }
  
  public List<Unit> find(String searchKey) {
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
            .createQuery("select e from unit e where User = :userKey")
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
