package edu.wsu.bean_582_2024.ApartmentFinder.service;

import edu.wsu.bean_582_2024.ApartmentFinder.data.userService;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final userService userService;
  public UserService (userService userService) {
    this.userService = userService;
  }
  public List<User> getAllUsers() {
    return userService.getAll();
  }
  
  public List<User> findUsers(String userFilter) {
    if (userFilter == null || userFilter.isEmpty() || userFilter.isBlank())
      return getAllUsers();
    User result = userService.getUserByUsername(userFilter);
    if (result == null) return Collections.emptyList();
    else return List.of(result);
  }
  
  public void deleteUser(User user) {
    userService.delete(user);
  }
  
  public void saveUser(User user) {
    if (user == null) return;
    if (user.getId() == null)
      userService.add(user);
    else 
      userService.update(user);
  }
  public Optional<User> findUserByUsername(String username) {
    return Optional.ofNullable(userService.getUserByUsername(username));
  }
  public Optional<User> findUserById(Long id) {
    return userService.getUserById(id);
  }
  
  public boolean addUnit(User user, Unit unit) {
    return user.getUnits().add(unit);
  } 
}
