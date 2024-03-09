package edu.wsu.bean_582_2024.ApartmentFinder.service;

import edu.wsu.bean_582_2024.ApartmentFinder.dao.UserDao;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserDao userDao;
  public UserService (UserDao userDao) {
    this.userDao = userDao;
  }
  public List<User> getAllUsers() {
    return userDao.getAll();
  }
  
  public List<User> findUsers(String userFilter) {
    if (userFilter == null || userFilter.isEmpty() || userFilter.isBlank())
      return getAllUsers();
    User result = userDao.findUser(userFilter);
    if (result == null) return Collections.emptyList();
    else return List.of(result);
  }
  
  public void deleteUser(User user) {
    userDao.delete(user);
  }
  
  public void saveUser(User user) {
    if (user == null) return;
    userDao.save(user);
  }
  public Optional<User> findUserByUsername(String username) {
    return Optional.ofNullable(userDao.findUser(username));
  }
  public Optional<User> findUserById(Long id) {
    return userDao.get(id);
  }
  
  public boolean addUnit(User user, Unit unit) {
    return user.getUnits().add(unit);
  } 
}
