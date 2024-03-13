package edu.wsu.bean_582_2024.ApartmentFinder.data;

import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository {
  List<User> getAll();
  Optional<User> getUserById(Long id);
  void add(User user);
  void update(User user);
  void delete(User user);
  Long count();
  User getUserByUsername(String username);
}
