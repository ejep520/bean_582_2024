package edu.wsu.bean_582_2024.ApartmentFinder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.wsu.bean_582_2024.ApartmentFinder.TestCase;
import edu.wsu.bean_582_2024.ApartmentFinder.data.AuthorityRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UnitRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UserRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Authority;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * UserService unit tests
 * @author Erik Jepsen &lt;erik.jepsen@wsu.edu&gt;
 */
@ExtendWith(MockitoExtension.class)
@Tag("fast")
public class UserServiceTests {
  // S10

  @Mock
  private UserRepository userRepository;
  @Mock
  private UnitRepository unitRepository;
  @Mock
  private AuthorityRepository authorityRepository;
  @InjectMocks
  private UserService userService;
  private final User user1 = new User(TestUsers.USERNAME_1, TestUsers.USER_PASSWORD_1,
      TestUsers.USER_ROLE_1);
  private final Authority authority1_1 = new Authority(user1, "ADMIN");
  private final Authority authority1_2 = new Authority(user1, "OWNER");
  private final Authority authority1_3 = new Authority(user1, "USER");
  private final List<Authority> authorities_1 = List.of(authority1_1, authority1_2, authority1_3);
  private final User user2 = new User(TestUsers.USERNAME_2, TestUsers.USER_PASSWORD_2,
      TestUsers.USER_ROLE_2);
  private final Authority authority2_1 = new Authority(user2, "OWNER");
  private final Authority authority2_2 = new Authority(user2, "USER");
  private final List<Authority> authorities_2 = List.of(authority2_1, authority2_2);
  private final User user3 = new User(TestUsers.USERNAME_3, TestUsers.USER_PASSWORD_3,
      TestUsers.USER_ROLE_3);
  private final Authority authority3 = new Authority(user3, "USER");
  private final List<User> allUsers = List.of(user1, user2, user3);
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
  public void resetFields() {
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

  @TestCase("C101")
  @Test
  @DisplayName("Initialization of UserService returns not null")
  public void initializationResultsInNotNull() {
    assertNotNull(userService);
  }

  @TestCase("C102")
  @Test
  @DisplayName("FindAll method returns all users")
  public void findAllReturnsAll() {
    when(userRepository.getAll()).thenReturn(allUsers);
    List<User> result = userService.getAllUsers();
    assertEquals(allUsers, result);
  }

  @TestCase("C103")
  @Test
  @DisplayName("FindUsers with null returns all users")
  public void findUsersNullReturnsAllUsers() {
    when(userRepository.getAll()).thenReturn(allUsers);
    List<User> result = userService.findUsers(null);
    assertEquals(allUsers, result);
  }

  @TestCase("C104")
  @Test
  @DisplayName("FindUsers with empty string returns all users")
  public void findUsersEmptyReturnsAllUsers() {
    when(userRepository.getAll()).thenReturn(allUsers);
    List<User> result = userService.findUsers("");
    assertEquals(allUsers, result);
  }

  @TestCase("C105")
  @Test
  @DisplayName("FindUsers with blank string returns all users")
  public void findUsersBlankReturnsAllUsers() {
    when(userRepository.getAll()).thenReturn(allUsers);
    List<User> result = userService.findUsers(" ");
    assertEquals(allUsers, result);
  }

  @TestCase("C106")
  @Test
  @DisplayName("FindUsers with a valid user returns one User")
  public void findUsersValidFilterReturnsOne() {
    when(userRepository.getUserByUsername(anyString())).thenReturn(user1);
    List<User> result = userService.findUsers(TestUsers.USERNAME_1);
    assertEquals(List.of(user1), result);
  }

  @TestCase("C107")
  @Test
  @DisplayName("FindUser with invalid key returns no users")
  public void findUserInvalidFilterReturnsNone() {
    when(userRepository.getUserByUsername(anyString())).thenReturn(null);
    List<User> result = userService.findUsers(TestUsers.BAD_USERNAME);
    assertEquals(Collections.emptyList(), result);
  }

  @TestCase("C108")
  @Test
  @DisplayName("Tests that DeleteUser calls the repository's delete method")
  public void deleteUserTest() {
    userService.deleteUser(user1);
    verify(userRepository).delete(user1);
    verify(authorityRepository, times(3)).delete(any(Authority.class));
    verify(unitRepository).delete(any(Unit.class));
  }

  @TestCase("C109")
  @Test
  @DisplayName("Tests that SaveUser calls the repository's save method")
  public void saveUserTest() {
    User user = new User(TestUsers.USERNAME_1, TestUsers.USER_PASSWORD_1, TestUsers.USER_ROLE_1);
    userService.saveUser(user);
    verify(userRepository).add(user);
  }

  @TestCase("C1010")
  @Test
  @DisplayName("Tests that SaveUser calls the repository's update method")
  public void updateUserTest() {
    User user = new User(TestUsers.USERNAME_1, TestUsers.USER_PASSWORD_1, TestUsers.USER_ROLE_1);
    user.setId(1L);
    userService.saveUser(user);
    verify(userRepository).update(user);
  }

  @TestCase("C1011")
  @ParameterizedTest(name="Able to find user {0}")
  @ValueSource(longs = {1L, 2L, 3L})
  public void findingUsersReturnsUsersFaithfully(Long userId) {
    when(userRepository.getUserById(userId))
        .thenReturn(allUsers.stream().filter(e -> e.getId().equals(userId)).findFirst());
    Optional<User> result = userService.findUserById(userId);
    assertTrue(result.isPresent());
  }

  @TestCase("C1012")
  @Test
  @DisplayName("Unable to find user nonexistent user by id")
  public void unableToFindNonexistentUserById() {
    long userId = 4L;
    when(userRepository.getUserById(userId))
        .thenReturn(allUsers.stream().filter(e -> e.getId().equals(userId)).findFirst());
    Optional<User> result = userService.findUserById(userId);
    assertTrue(result.isEmpty());
  }

  @TestCase("C1013")
  @ParameterizedTest(name="Search for Users by username {0}")
  @ValueSource(strings = {TestUsers.USERNAME_1, TestUsers.USERNAME_2, TestUsers.USERNAME_3})
  public void SearchForUsersByUsername(String searchKey) {
    when(userRepository.getUserByUsername(searchKey))
        .thenReturn(allUsers.stream().filter(e -> e.getUsername().equals(searchKey))
            .findFirst().orElse(null));
    Optional<User> result = userService.findUserByUsername(searchKey);
    assertTrue(result.isPresent());
  }

  @TestCase("C1014")
  @Test
  @DisplayName("Save null User makes no repository calls")
  public void saveNullUserMakesNoRepoCalls() {
    userService.saveUser(null);
    verify(userRepository, times(0)).add(any());
    verify(userRepository, times(0)).update(any());
  }
}
