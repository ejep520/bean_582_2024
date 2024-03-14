package edu.wsu.bean_582_2024.ApartmentFinder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.wsu.bean_582_2024.ApartmentFinder.data.AuthorityRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UnitRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UserRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Authority;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * UserService unit tests
 * @author Erik Jepsen &lt;erik.jepsen@wsu.edu&gt;
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
  @Mock
  private UserRepository userRepository;
  @Mock
  private UnitRepository unitRepository;
  @Mock
  private AuthorityRepository authorityRepository;
  private UserService userService;
  private final static String USERNAME1 = "TestAdmin";
  private final static String userPassword1 = "foo";
  private final static Role userRole1 = Role.ADMIN;
  private final User user1 = new User(USERNAME1, userPassword1, userRole1);
  private final Authority authority1_1 = new Authority(user1, "ADMIN");
  private final Authority authority1_2 = new Authority(user1, "OWNER");
  private final Authority authority1_3 = new Authority(user1, "USER");
  private final List<Authority> authorities_1 = List.of(authority1_1, authority1_2, authority1_3);
  private final static String userName2 = "TestOwner";
  private final static String userPassword2 = "bar";
  private final static Role userRole2 = Role.OWNER;
  private final User user2 = new User(userName2, userPassword2, userRole2);
  private final Authority authority2_1 = new Authority(user2, "OWNER");
  private final Authority authority2_2 = new Authority(user2, "USER");
  private final List<Authority> authorities_2 = List.of(authority2_1, authority2_2);
  private final static String username3 = "TestUser";
  private final static String userPassword3 = "foobar";
  private final static Role userRole3 = Role.USER;
  private final User user3 = new User(username3, userPassword3, userRole3);
  private final Authority authority3 = new Authority(user3, "USER");
  private final List<User> allUsers = List.of(user1, user2, user3);
  private final static String BAD_USERNAME = "BadUsername";
  private final static String ADDRESS = "Address String";
  private final static String KITCHEN = "Kitchen String";
  private final static String LIVING_ROOM = "Living Room String";
  private final static Double BATHROOMS = 2.5d;
  private final static Integer BEDROOMS = 2;
  private final static Boolean FEATURED = true;
  private final Unit unit = new Unit(ADDRESS, BEDROOMS, BATHROOMS, LIVING_ROOM, KITCHEN, FEATURED, user1);
  
  public UserServiceTests() {
    user1.getAuthorities().addAll(authorities_1);
    user1.getUnits().add(unit);
    user1.setId(1L);
    user2.getAuthorities().addAll(authorities_2);
    user2.setId(2L);
    user3.getAuthorities().add(authority3);
    user3.setId(3L);
  }
  
  @AfterEach
  public void resetField() {
    if (user1.getAuthorities().size() != 3) {
      user1.getAuthorities().clear();
      user1.getAuthorities().addAll(authorities_1);
    }
    if (user2.getAuthorities().size() != 2) {
      user2.getAuthorities().clear();
      user2.getAuthorities().addAll(authorities_2);
    }
    if (user3.getAuthorities().size() != 1) {
      user3.getAuthorities().clear();
      user3.getAuthorities().add(authority3);
    }
    if (user1.getUnits().size() != 1) {
      user1.getUnits().clear();
      user1.getUnits().add(unit);
    }
  }
  
  @Test
  @DisplayName("Initialization of UserService returns not null")
  public void initializationResultsInNotNull() {
    initializeUserService();
    assertNotNull(userService);
  }
  
  @Test
  @DisplayName("FindAll method returns all users")
  public void findAllReturnsAll() {
    when(userRepository.getAll()).thenReturn(allUsers);
    initializeUserService();
    List<User> result = userService.getAllUsers();
    assertEquals(allUsers, result);
  }
  
  @Test
  @DisplayName("FindUsers with null returns all users")
  public void findUsersNullReturnsAllUsers() {
    when(userRepository.getAll()).thenReturn(allUsers);
    initializeUserService();
    List<User> result = userService.findUsers(null);
    assertEquals(allUsers, result);
  }

  @Test
  @DisplayName("FindUsers with empty string returns all users")
  public void findUsersEmptyReturnsAllUsers() {
    when(userRepository.getAll()).thenReturn(allUsers);
    initializeUserService();
    List<User> result = userService.findUsers("");
    assertEquals(allUsers, result);
  }
  
  @Test
  @DisplayName("FindUsers with blank string returns all users")
  public void findUsersBlankReturnsAllUsers() {
    when(userRepository.getAll()).thenReturn(allUsers);
    initializeUserService();
    List<User> result = userService.findUsers(" ");
    assertEquals(allUsers, result);
  }
  
  @Test
  @DisplayName("FindUsers with a valid user returns one User")
  public void findUsersValidFilterReturnsOne() {
    when(userRepository.getUserByUsername(anyString())).thenReturn(user1);
    initializeUserService();
    List<User> result = userService.findUsers(USERNAME1);
    assertEquals(List.of(user1), result);
  }
  
  @Test
  @DisplayName("FindUser with invalid key returns no users")
  public void findUserInvalidFilterReturnsNone() {
    when(userRepository.getUserByUsername(anyString())).thenReturn(null);
    initializeUserService();
    List<User> result = userService.findUsers(BAD_USERNAME);
    assertEquals(Collections.emptyList(), result);
  }
  
  @Test
  @DisplayName("Tests that DeleteUser calls the repository's delete method")
  public void deleteUserTest() {
    initializeUserService();
    userService.deleteUser(user1);
    verify(userRepository).delete(user1);
    verify(authorityRepository, times(3)).remove(any(Authority.class));
    verify(unitRepository).delete(any(Unit.class));
  }

  @Test
  @DisplayName("Tests that SaveUser calls the repository's save method")
  public void saveUserTest() {
    User user = new User(USERNAME1, userPassword1, userRole1);
    initializeUserService();
    userService.saveUser(user);
    verify(userRepository).add(user);
  }
  
  @Test
  @DisplayName("Tests that SaveUser calls the repository's update method")
  public void updateUserTest() {
    initializeUserService();
    User user = new User(USERNAME1, userPassword1, userRole1);
    user.setId(1L);
    userService.saveUser(user);
    verify(userRepository).update(user);
  }
  
  @ParameterizedTest(name="Able to find user {0}")
  @ValueSource(longs = {1L, 2L, 3L})
  public void findingUsersReturnsUsersFaithfully(Long userId) {
    when(userRepository.getUserById(userId))
        .thenReturn(allUsers.stream().filter(e -> e.getId().equals(userId)).findFirst());
    initializeUserService();
    Optional<User> result = userService.findUserById(userId);
    assertTrue(result.isPresent());
  }

  @Test
  @DisplayName("Unable to find user nonexistent user by id")
  public void unableToFindNonexistentUserById() {
    long userId = 4L;
    when(userRepository.getUserById(userId))
        .thenReturn(allUsers.stream().filter(e -> e.getId().equals(userId)).findFirst());
    initializeUserService();
    Optional<User> result = userService.findUserById(userId);
    assertTrue(result.isEmpty());
  }

  private void initializeUserService() {
    userService = new UserService(userRepository, authorityRepository, unitRepository);
  }
}
