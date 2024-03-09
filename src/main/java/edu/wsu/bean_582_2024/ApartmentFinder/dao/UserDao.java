package edu.wsu.bean_582_2024.ApartmentFinder.dao;

import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserDao extends DaoHelper implements Dao<User>{

  public UserDao(EntityManager entityManager) {
    super(entityManager, LoggerFactory.getLogger(UserDao.class));
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
  public void update(User user, Object... params) {
    Objects.requireNonNull(user, "User may not be null.").setUsername(Objects
        .requireNonNull((String)params[0], "Username may not be null."));
    if (user.isPasswordSaltEmpty()) {
      user.generateSalt();
    }
    user.setPassword(Objects.requireNonNull((String)params[1], "Password may not be null."));
    user.getUnits().clear();
    List<Unit> castedList = castList(Unit.class, (Collection<?>)params[2]);
    user.getUnits().addAll(Objects
        .requireNonNull(castedList, "Units list may not be null."));
    executeInsideTransaction(entityManager -> entityManager.merge(user));
  }

  @Override
  public void delete(User user) {
    executeInsideTransaction(entityManager -> entityManager.remove(user));
  }
  
  public User findUser(String searchKey) {
    List<User> listUser = new ArrayList<>();
    for (Object o : entityManager.createQuery("SELECT e from User e WHERE e.username LIKE :searchKey")
        .setParameter("searchKey", searchKey)
        .getResultList()) {
      try {
        listUser.add((User) o);
      } catch (ClassCastException err) {}
    }
    try {
      return listUser.get(0);
    } catch (IndexOutOfBoundsException err) {
      return null;
    }
  }
}
