package edu.wsu.bean_582_2024.ApartmentFinder.service;

import edu.wsu.bean_582_2024.ApartmentFinder.data.UserRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;
  public UserService (UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }
  
  public List<User> findUsers(String userFilter) {
    if (userFilter == null || userFilter.isEmpty() || userFilter.isBlank())
      return getAllUsers();
    User result = userRepository.getUserByUsername(userFilter);
    if (result == null) return Collections.emptyList();
    else return List.of(result);
  }
  
  public long getUserCount() {
    return userRepository.count();
  }
  public void deleteUser(User user) {
    userRepository.delete(user);
  }
  
  public void saveUser(User user) {
    if (user == null) return;
    userRepository.save(user);
  }
}
