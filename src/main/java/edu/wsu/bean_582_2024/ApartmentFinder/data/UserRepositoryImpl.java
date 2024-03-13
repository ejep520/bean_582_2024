package edu.wsu.bean_582_2024.ApartmentFinder.data;

import edu.wsu.bean_582_2024.ApartmentFinder.dao.UserDao;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryImpl implements UserRepository {

  private final UserDao userDao;
  
  public UserRepositoryImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public List<User> getAll() {
    return userDao.getAll();
  }

  @Override
  public Optional<User> getUserById(Long id) {
    return userDao.get(id);
  }

  @Override
  public void add(User user) {
    userDao.save(user);
  }

  @Override
  public void update(User user) {
    userDao.update(user);
  }

  @Override
  public void delete(User user) {
    userDao.delete(user);
  }

  @Override
  public Long count() {
    return userDao.count();
  }

  @Override
  public User getUserByUsername(String username) {
    return userDao.findUser(username);
  }
}
