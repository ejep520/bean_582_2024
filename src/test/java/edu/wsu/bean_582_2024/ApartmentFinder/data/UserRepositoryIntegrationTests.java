package edu.wsu.bean_582_2024.ApartmentFinder.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wsu.bean_582_2024.ApartmentFinder.dao.UserDao;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import jakarta.persistence.EntityManagerFactory;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryIntegrationTests {
  private final UserRepository userRepository;
  private final static String USERNAME_1 = "testAdmin";
  private final static String USERNAME_2 = "testOwner";
  private final static String USERNAME_3 = "testUser";
  private final static String PASSWORD_1 = "foo";
  private final static String PASSWORD_2 = "bar";
  private final static String PASSWORD_3 = "foobar";
  private final static Role ROLE_1 = Role.ADMIN;
  private final static Role ROLE_2 = Role.OWNER;
  private final static Role ROLE_3 = Role.USER;
  private User user_1 = new User(USERNAME_1, PASSWORD_1, ROLE_1);
  private User user_2 = new User(USERNAME_2, PASSWORD_2, ROLE_2);
  private User user_3 = new User(USERNAME_3, PASSWORD_3, ROLE_3);
  private final List<User> userList = List.of(user_1, user_2, user_3);

  @Autowired
  public UserRepositoryIntegrationTests(EntityManagerFactory entityManagerFactory) {
    userRepository = new UserRepositoryImpl(new UserDao(entityManagerFactory));
  }
  
  @BeforeEach
  public void resetDatabase() {
    List<User> users = userRepository.getAll();
    for (User user : users) userRepository.delete(user);
    user_1 = new User(USERNAME_1, PASSWORD_1, ROLE_1);
    user_2 = new User(USERNAME_2, PASSWORD_2, ROLE_2);
    user_3 = new User(USERNAME_3, PASSWORD_3, ROLE_3);
  }
  
  @Test
  public void getAllUsersTest() {
    List<User> initial = userRepository.getAll();
    assertEquals(Collections.emptyList(), initial);

    for (User user : userList) userRepository.add(user);
    
    List<User> result = userRepository.getAll();
    assertEquals(userList, result);
  }
  
  @Test
  public void addUserTest() {
    List<User> initial, result;
    initial = userRepository.getAll();
    assertEquals(Collections.emptyList(), initial);
    
    userRepository.add(user_1);
    
    result = userRepository.getAll();
    assertEquals(List.of(user_1), result);
  }
  
  @Test
  public void updateUserTest() {
    userRepository.add(user_1);
    
    user_1.setPassword("baz");
    
    userRepository.update(user_1);
    
    User result = userRepository.getUserById(user_1.getId()).orElse(null);
    
    if (result == null) fail("Could not retrieve user.");
    assertTrue(result.checkPassword("baz"));
  }
  
  @Test
  public void deleteUserTest() {
    for (User user : userList) userRepository.add(user);
    assertEquals(3, userRepository.getAll().size());
    
    userRepository.delete(user_2);

    Optional<User> result = userRepository.getUserById(user_2.getId());
    
    assertTrue(result.isEmpty());
  }
}
