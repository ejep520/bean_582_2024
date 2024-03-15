package edu.wsu.bean_582_2024.ApartmentFinder.service;

import edu.wsu.bean_582_2024.ApartmentFinder.data.AuthorityRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UnitRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UserRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Authority;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final AuthorityRepository authRepository;
  private final UnitRepository unitRepository;
  public UserService (UserRepository userRepository, @Qualifier("AuthImpl") AuthorityRepository authRepository, UnitRepository unitRepository) {
    this.userRepository = userRepository;
    this.authRepository = authRepository;
    this.unitRepository = unitRepository;
  }
  public List<User> getAllUsers() {
    return userRepository.getAll();
  }
  
  public List<User> findUsers(String userFilter) {
    if (userFilter == null || userFilter.isEmpty() || userFilter.isBlank())
      return getAllUsers();
    User result = userRepository.getUserByUsername(userFilter);
    if (result == null) return Collections.emptyList();
    else return List.of(result);
  }
  
  public void deleteUser(User user) {
    for (Authority authority : user.getAuthorities()) {
      authRepository.delete(authority);
      
    }
    for (Unit unit : user.getUnits()) {
      unitRepository.delete(unit);
    }
    userRepository.delete(user);
  }
  
  public void saveUser(User user) {
    if (user == null) return;
    if (user.getId() == null)
      userRepository.add(user);
    else 
      userRepository.update(user);
  }
  public Optional<User> findUserByUsername(String username) {
    return Optional.ofNullable(userRepository.getUserByUsername(username));
  }
  public Optional<User> findUserById(Long id) {
    return userRepository.getUserById(id);
  }
}
