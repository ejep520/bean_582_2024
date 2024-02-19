package edu.wsu.bean_582_2024.ApartmentFinder.service;

import edu.wsu.bean_582_2024.ApartmentFinder.data.UserRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
  public Optional<User> findUserByUsername(String username) {
    return Optional.ofNullable(userRepository.getUserByUsername(username));
  }
  public Optional<User> findUserById(Long id) {
    Optional<User> returnValue;
    try {
      returnValue = Optional.of(userRepository.getReferenceById(id));
    } catch (EntityNotFoundException e) {
      returnValue = Optional.empty();
    }
    return returnValue;
  }
}
