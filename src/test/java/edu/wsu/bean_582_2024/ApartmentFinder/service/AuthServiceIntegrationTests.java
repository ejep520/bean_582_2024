package edu.wsu.bean_582_2024.ApartmentFinder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import edu.wsu.bean_582_2024.ApartmentFinder.dao.AuthorityDao;
import edu.wsu.bean_582_2024.ApartmentFinder.dao.UserDao;
import edu.wsu.bean_582_2024.ApartmentFinder.data.AuthorityRepositoryImpl;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UserRepositoryImpl;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Authority;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Tag("fast")
public class AuthServiceIntegrationTests {
  @Mock
  private UserDao userDao;
  @Mock
  private AuthorityDao authorityDao;
  @Mock
  private Authentication authentication;
  private AuthService authService;
  
  @BeforeEach
  public void resetService() {
    authService = new AuthService(new UserRepositoryImpl(userDao),
        new AuthorityRepositoryImpl(authorityDao));
  }

  /**
   * Tests both the business logic (that the authenticate method will return null when the user
   * DAO returns null) and the integration between the authentication service and the user DAO.
   */
  @Test
  public void authenticate_null_user_returns_null() {
    when(authentication.getName()).thenReturn(TestUsers.USERNAME_1);
    when(userDao.findUser(anyString())).thenReturn(null);
    
    assertNull(authService.authenticate(authentication));
    verifyNoMoreInteractions(authentication, userDao);
    verifyNoInteractions(authorityDao);
  }

  /**
   * Tests the integration between the user DAO and the authentication service by checking the
   * business logic of the authenticate method against users of each Role and checked for
   * predicted DAO calls.
   * @param role The role being tested.
   */
  @ParameterizedTest
  @EnumSource(Role.class)
  public void authenticate_each_role(Role role) {
    User user = mock(User.class);
    when(authentication.getName()).thenReturn(TestUsers.USERNAME_1);
    when(authentication.getCredentials()).thenReturn(TestUsers.USER_PASSWORD_1);
    when(user.getRole()).thenReturn(role);
    when(userDao.findUser(anyString())).thenReturn(user);
    
    Authentication result = authService.authenticate(authentication);
    
    switch (role) {
      case ADMIN -> assertEquals(AuthService.ADMIN_AUTHORITY, result.getAuthorities());
      case OWNER -> assertEquals(AuthService.OWNER_AUTHORITY, result.getAuthorities());
      case USER -> assertEquals(AuthService.USER_AUTHORITY, result.getAuthorities());
      default -> fail(String.format("Unable to test for role: %s", role));
    }
    verifyNoMoreInteractions(authentication, user, userDao);
    verifyNoInteractions(authorityDao);
  }

  /**
   * By mimicking no users in the database, this tests the integration between the authentication
   * service and the DAO layer.
   */
  @Test
  public void register_with_zero_users_creates_admin() {
    User user = mock(User.class);
    List<Authority> authorities = new ArrayList<>();
    when(userDao.count()).thenReturn(0L);
    when(user.getRole()).thenReturn(Role.ADMIN);
    when(user.getAuthorities()).thenReturn(authorities);
    
    authService.register(user);
    
    verify(user).setRole(Role.ADMIN);
    verify(authorityDao, times(3)).save(any(Authority.class));
    verify(userDao).save(user);
    verifyNoMoreInteractions(user, authorityDao, userDao);
  }

  /**
   * Tests the business logic as well as the predicted DAO layer calls required to register each
   * user role.
   * @param role The role being tested
   */
  @EnumSource(Role.class)
  @ParameterizedTest
  public void register_each_role_with_existing_users_in_database(Role role) {
    User user = mock(User.class);
    List<Authority> authorities = new ArrayList<>();
    when(userDao.count()).thenReturn(1L);
    when(user.getRole()).thenReturn(role);
    when(user.getAuthorities()).thenReturn(authorities);
    
    authService.register(user);
    
    switch(role) {
      case ADMIN -> {
        verify(authorityDao, times(3)).save(any(Authority.class));
        verify(user).getRole();
      }
      case OWNER -> {
        verify(authorityDao, times(2)).save(any(Authority.class));
        verify(user, times(2)).getRole();
      }
      case USER -> {
        verify(authorityDao).save(any(Authority.class));
        verify(user, times(2)).getRole();
      }
      default -> fail(String.format("This test was not expecting a role of %s.", role));
    }
    verify(userDao).save(user);
    verifyNoMoreInteractions(user, userDao, authorityDao);
  }

  /**
   * This test checks the integration of the layers and business logic of the overloaded
   * register method. The method under scrutiny in this test is called from the AdminForm
   * class. By capturing the user sent to the DAO, we can check for predictable changes made
   * by the business logic.
   */
  @Test
  public void register_overload_no_existing_users_creates_admin() {
    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    User user = mock(User.class);
    List<Authority> authorities = new ArrayList<>();
    when(user.getAuthorities()).thenReturn(authorities);
    when(userDao.count()).thenReturn(0L);
    when(userDao.findUser(TestUsers.USERNAME_3)).thenReturn(user);
    
    authService.register(TestUsers.USERNAME_3, TestUsers.USER_PASSWORD_3, TestUsers.USER_ROLE_3);
    
    verify(userDao).save(userCaptor.capture());
    assertNotNull(userCaptor.getValue());
    assertEquals(TestUsers.USERNAME_3, userCaptor.getValue().getUsername());
    assertTrue(userCaptor.getValue().checkPassword(TestUsers.USER_PASSWORD_3));
    assertNotEquals(TestUsers.USER_ROLE_3, userCaptor.getValue().getRole());
    verify(authorityDao, times(3)).save(any(Authority.class));
    verify(user, times(3)).getAuthorities();
    verify(userDao).update(any(User.class));
    verifyNoMoreInteractions(user, userDao, authorityDao);
  }

  /**
   * Continuing to test the integration of the overloaded register method with evidence of
   * existing users in the database. Each role is tested and its DAO calls predicted.
   * @param role The user role being tested
   */
  @EnumSource(Role.class)
  @ParameterizedTest
  public void register_overload_for_role_with_existing_users_in_database(Role role) {
    int callsExpected;
    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    User user = mock(User.class);
    List<Authority> authorities = new ArrayList<>();
    when(user.getAuthorities()).thenReturn(authorities);
    when(userDao.count()).thenReturn(1L);
    when(userDao.findUser(TestUsers.USERNAME_1)).thenReturn(user);
    
    authService.register(TestUsers.USERNAME_1, TestUsers.USER_PASSWORD_1, role);
    
    verify(userDao).save(userCaptor.capture());
    assertNotNull(userCaptor.getValue());
    assertEquals(TestUsers.USERNAME_1, userCaptor.getValue().getUsername());
    assertTrue(userCaptor.getValue().checkPassword(TestUsers.USER_PASSWORD_1));
    assertEquals(role, userCaptor.getValue().getRole());
    switch(role) {
      case ADMIN -> callsExpected = 3;
      case OWNER -> callsExpected = 2;
      case USER -> callsExpected = 1;
      default -> {
        fail(String.format("This role was not anticipated in this test: %s", role));
        return;
      }
    }
    verify(authorityDao, times(callsExpected)).save(any(Authority.class));
    verify(userDao).update(any(User.class));
    verifyNoMoreInteractions(user, userDao, authorityDao);
  }

  /**
   * This very simple integration test checks that a predictable call is made from the
   * authentication service to the user DAO and the output returned.
   */
  @Test
  public void count_user_test() {
    when(userDao.count()).thenReturn(1L);
    
    long result = authService.getUserCount();
    
    assertEquals(1L, result);
    verifyNoMoreInteractions(userDao);
    verifyNoInteractions(authorityDao);
  }

  /**
   * Tests that the authority service makes predictable calls to the authority DAO when an
   * Authority is deleted.
   */
  @Test
  public void delete_authority_test() {
   Authority authority = mock(Authority.class);
   
   authService.delete(authority);
   
   verify(authorityDao).delete(authority);
   verifyNoMoreInteractions(authorityDao);
   verifyNoInteractions(userDao);
  }

  /**
   * Tests the business logic and integration between the authentication service and the user
   * DAO when checking to see if a username is taken.
   * @param usernameIsTaken Whether the user DAO will report the username as taken or not.
   */
  @ValueSource(booleans = {false, true})
  @ParameterizedTest
  public void username_taken_test(boolean usernameIsTaken) {
    when(userDao.findUser(anyString())).thenReturn(usernameIsTaken ? mock(User.class) : null);
    
    boolean result = authService.usernameTaken(TestUsers.USERNAME_3);
    
    assertEquals(usernameIsTaken, result);
    verifyNoMoreInteractions(userDao);
    verifyNoInteractions(authorityDao);
  }
}
