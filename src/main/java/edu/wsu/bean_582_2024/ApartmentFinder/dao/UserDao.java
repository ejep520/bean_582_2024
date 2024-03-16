package edu.wsu.bean_582_2024.ApartmentFinder.dao;

import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserDao extends DaoHelper implements Dao<User>{

  public UserDao(EntityManagerFactory entityManagerFactory) {
    super(entityManagerFactory, LoggerFactory.getLogger(UserDao.class));
  }
  
  @Override
  public Optional<User> get(long id) {
    return Optional.ofNullable(entityManager.find(User.class, id));
  }

  @Override
  public List<User> getAll() {
    Query query = entityManager.createQuery("SELECT e FROM User e");
    return castList(User.class, query.getResultList());
  }

  @Override
  public void save(User user) {
    executeInsideTransaction(entityManager -> entityManager.persist(user));
  }

  @Override
  public void update(User user) {
    EntityManager localManager = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = localManager.getTransaction();
    transaction.begin();
    localManager.merge(user);
    transaction.commit();
  }

  @Override
  public void delete(User user) {
    executeInsideTransaction(entityManager ->
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user)));
  }
  
  public User findUser(String searchKey) {
    List<User> listUser = castList(User.class,
        entityManager.createQuery("SELECT e from User e WHERE e.username LIKE :searchKey")
            .setParameter("searchKey", searchKey)
            .getResultList());
    try {
      return listUser.get(0);
    } catch (IndexOutOfBoundsException err) {
      return null;
    }
  }
  
  public Long count() {
    Long returnValue;
    try {
      returnValue = (Long) entityManager
          .createQuery("select count(e.id) from User e")
          .getSingleResult();
    } catch (ClassCastException err) {
      logger.atError().log(String.format("Unable to cast to Long. %s", err));
      returnValue = 0L;
    }
    return returnValue;
  }
}
