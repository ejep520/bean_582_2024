package edu.wsu.bean_582_2024.ApartmentFinder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.wsu.bean_582_2024.ApartmentFinder.data.AuthorityRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UnitRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UserRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * UserService unit tests
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
  @Mock
  private UserRepository userRepository;
  private UserService userService;
  private final static String userName1 = "TestAdmin";
  private final static String userPassword1 = "foo";
  private final static Role userRole1 = Role.ADMIN;
  private final static User user1 = new User(userName1, userPassword1, userRole1);
  private final static String userName2 = "TestOwner";
  private final static String userPassword2 = "bar";
  private final static Role userRole2 = Role.OWNER;
  private final static User user2 = new User(userName2, userPassword2, userRole2);
  private final static String username3 = "TestUser";
  private final static String userPassword3 = "foobar";
  private final static Role userRole3 = Role.USER;
  private final static User user3 = new User(username3, userPassword3, userRole3);
  private final static List<User> allUsers = List.of(user1, user2, user3);
  private final static String badUsername = "BadUsername";  
  
  @AfterEach
  public void resetField() {
    userService = null;
    userRepository = Mockito.mock(UserRepository.class);
  }
  
  @Test
  @DisplayName("Initialization of UserService returns not null")
  public void initializationResultsInNotNull() {
    userRepository = Mockito.mock(UserRepository.class);
    initializeUserService();
    assertNotNull(userService);
  }
  
  @Test
  @DisplayName("FindAll method returns all users")
  public void findAllReturnsAll() {
    Mockito.when(userRepository.getAll()).thenReturn(allUsers);
    initializeUserService();
    List<User> result = userService.getAllUsers();
    assertEquals(allUsers, result);
  }
  
  @Test
  @DisplayName("FindUsers with null returns all users")
  public void findUsersNullReturnsAllUsers() {
    Mockito.when(userRepository.getAll()).thenReturn(allUsers);
    initializeUserService();
    List<User> result = userService.findUsers(null);
    assertEquals(allUsers, result);
  }

  @Test
  @DisplayName("FindUsers with empty string returns all users")
  public void findUsersEmptyReturnsAllUsers() {
    Mockito.when(userRepository.getAll()).thenReturn(allUsers);
    initializeUserService();
    List<User> result = userService.findUsers("");
    assertEquals(allUsers, result);
  }
  
  @Test
  @DisplayName("FindUsers with blank string returns all users")
  public void findUsersBlankReturnsAllUsers() {
    Mockito.when(userRepository.getAll()).thenReturn(allUsers);
    initializeUserService();
    List<User> result = userService.findUsers(" ");
    assertEquals(allUsers, result);
  }
  
  @Test
  @DisplayName("FindUsers with a valid user returns one User")
  public void findUsersValidFilterReturnsOne() {
    Mockito.when(userRepository.getUserByUsername(Mockito.anyString())).thenReturn(user1);
    initializeUserService();
    List<User> result = userService.findUsers(userName1);
    assertEquals(List.of(user1), result);
  }
  
  @Test
  @DisplayName("FindUser with invalid key returns no users")
  public void findUserInvalidFilterReturnsNone() {
    Mockito.when(userRepository.getUserByUsername(Mockito.anyString())).thenReturn(null);
    initializeUserService();
    List<User> result = userService.findUsers(badUsername);
    assertEquals(Collections.emptyList(), result);
  }
  
  @Test
  @DisplayName("Tests that DeleteUser calls the repository's delete method")
  public void deleteUserTest() {
    UserRepository spy = Mockito.spy(UserRepository.class);
    initializeUserService(spy);
    userService.deleteUser(user1);
    Mockito.verify(spy).delete(user1);
  }

  @Test
  @DisplayName("Tests that SaveUser calls the repository's save method")
  public void saveUserTest() {
    UserRepository spy = Mockito.spy(UserRepository.class);
    initializeUserService(spy);
    userService.saveUser(user1);
    Mockito.verify(spy).add(user1);
  }
  
  private void initializeUserService() {
    userService = new UserService(userRepository, Mockito.mock(AuthorityRepository.class),
        Mockito.mock(UnitRepository.class));
  }
  
  private void initializeUserService(UserRepository userRepository) {
    userService = new UserService(userRepository, Mockito.mock(AuthorityRepository.class),
        Mockito.mock(UnitRepository.class));
  }
}
