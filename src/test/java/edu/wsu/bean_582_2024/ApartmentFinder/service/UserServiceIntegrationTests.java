package edu.wsu.bean_582_2024.ApartmentFinder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import edu.wsu.bean_582_2024.ApartmentFinder.dao.AuthorityDao;
import edu.wsu.bean_582_2024.ApartmentFinder.dao.UnitDao;
import edu.wsu.bean_582_2024.ApartmentFinder.dao.UserDao;
import edu.wsu.bean_582_2024.ApartmentFinder.data.AuthorityRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.AuthorityRepositoryImpl;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UnitRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UnitRepositoryImpl;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UserRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UserRepositoryImpl;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Authority;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
public class UserServiceIntegrationTests {
  @Mock
  private UserDao userDao;
  @Mock
  private AuthorityDao authorityDao;
  @Mock
  private UnitDao unitDao;
  private UserService userService;
  private User user_1;
  private User user_2;
  private User user_3;
  private List<User> userList;
  
  @BeforeEach
  public void restartService() {
    UserRepository userRepository = new UserRepositoryImpl(userDao);
    AuthorityRepository authorityRepository = new AuthorityRepositoryImpl(authorityDao);
    UnitRepository unitRepository = new UnitRepositoryImpl(unitDao);
    userService = new UserService(userRepository, authorityRepository, unitRepository);
    user_1 = new User(TestUsers.USERNAME_1, TestUsers.USER_PASSWORD_1, TestUsers.USER_ROLE_1);
    user_2 = new User(TestUsers.USERNAME_2, TestUsers.USER_PASSWORD_2, TestUsers.USER_ROLE_2);
    user_3 = new User(TestUsers.USERNAME_3, TestUsers.USER_PASSWORD_3, TestUsers.USER_ROLE_3);
    userList = List.of(user_1, user_2, user_3);
  }

  /**
   * This test verifies that when the command to add a user is received by the service that it
   * passes along the proper commands thru the repository to the DAO.
   */
  @Test
  public void addUserTest() {
    userService.saveUser(user_1);
    
    verify(userDao).save(user_1);
    verifyNoMoreInteractions(userDao);
    verifyNoInteractions(authorityDao, unitDao);
  }

  /**
   * This test verifies that attempts to get all users from the DAO call a predictable command and
   * that the result is faithfully returned to the caller.
   */
  @Test
  public void getAllUsers() {
    when(userDao.getAll()).thenReturn(userList);
    
    List<User> result = userService.getAllUsers();
    
    assertEquals(userList, result);
    verify(userDao).getAll();
    verifyNoMoreInteractions(userDao);
    verifyNoInteractions(unitDao, authorityDao);
  }

  /**
   * This tests the business logic within the method as well as the integration of the service and
   * repository layers to communicate the needs of the service to the DAO layer and receive back
   * from the service the correct information from the DAO.
   * @param testKey Represents the test data passed in from the view layer.
   */
  @MethodSource("userStream")
  @ParameterizedTest
  public void getUserByUsernameTest(String testKey) {
    if (testKey == null || testKey.isBlank()) {
      when(userDao.getAll()).thenReturn(userList);
    } else if (testKey.equals(TestUsers.USERNAME_1)) {
      when(userDao.findUser(TestUsers.USERNAME_1)).thenReturn(user_1);
    } else if (testKey.equals(TestUsers.BAD_USERNAME)) {
      when(userDao.findUser(TestUsers.BAD_USERNAME)).thenReturn(null);
    } else {
      fail("The test is not prepared for this input.");
      return;
    }
    
    List<User> result = userService.findUsers(testKey);
    
    if (testKey == null || testKey.isBlank()) {
      assertEquals(userList, result);
      verify(userDao).getAll();
    } else if (testKey.equals(TestUsers.USERNAME_1)) {
      assertEquals(List.of(user_1), result);
      verify(userDao).findUser(TestUsers.USERNAME_1);
    } else if (testKey.equals(TestUsers.BAD_USERNAME)) {
      assertEquals(Collections.emptyList(), result);
      verify(userDao).findUser(TestUsers.BAD_USERNAME);
    } else {
      fail("Somehow we slipped past the first check and failed on the second. This test is not set" 
          + " up for this condition.");
    }
    verifyNoMoreInteractions(userDao);
    verifyNoInteractions(unitDao);
    verifyNoInteractions(authorityDao);
  }
  
  private static Stream<Arguments> userStream() {
    return Stream.of(arguments((String) null), arguments(" "), arguments(TestUsers.USERNAME_1),
        arguments(TestUsers.BAD_USERNAME));
  }

  /**
   * This test verifies that when a user is deleted, all necessary commands are issued to destroy
   * the user and all units and authorities associated with that user.
   */
  @Test
  public void deleteUserTest() {
    Authority authority = new Authority(user_1, "ADMIN_ROLE");
    Unit unit = new Unit("TestAddress", 3, 2.5D, "TestLivingRoom", "TestKitchen", true, user_1);
    user_1.getAuthorities().add(authority);
    user_1.getUnits().add(unit);
    
    userService.deleteUser(user_1);
    
    verify(userDao).delete(any(User.class));
    verify(unitDao).delete(any(Unit.class));
    verify(authorityDao).delete(any(Authority.class));
    verifyNoMoreInteractions(userDao, unitDao, authorityDao);
  }

  /**
   * This test verifies that a predictable command is sent from the service layer to the DAO when
   * an existing user in the database requires updating.
   */
  @Test
  public void saveUserTest() {
    user_1.setId(1L);
    
    userService.saveUser(user_1);
    
    verify(userDao).update(user_1);
    verifyNoMoreInteractions(userDao);
    verifyNoInteractions(authorityDao);
    verifyNoInteractions(unitDao);
  }

  /**
   * This tests both the business logic of the service layer and the method's ability to pass on
   * predictable commands to the layer below it and get back the required information.
   * @param returnUser Determines whether the test data will "find" a user or not.
   */
  @ValueSource(booleans = {true, false})
  @ParameterizedTest
  public void getUserByIdTest(boolean returnUser) {
    if (returnUser) {
      user_1.setId(1L);
      when(userDao.get(anyLong())).thenReturn(Optional.of(user_1));
    } else {
      when(userDao.get(anyLong())).thenReturn(Optional.empty());
    }
    
    Optional<User> result = userService.findUserById(1L);
    
    assertEquals(returnUser, result.isPresent());
  }
}
