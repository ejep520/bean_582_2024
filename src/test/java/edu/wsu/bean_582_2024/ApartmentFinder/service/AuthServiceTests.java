package edu.wsu.bean_582_2024.ApartmentFinder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import edu.wsu.bean_582_2024.ApartmentFinder.TestCase;
import edu.wsu.bean_582_2024.ApartmentFinder.data.AuthorityRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UserRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Authority;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.service.AuthService.AuthorizedRoute;
import edu.wsu.bean_582_2024.ApartmentFinder.views.AdminView;
import edu.wsu.bean_582_2024.ApartmentFinder.views.HomeView;
import edu.wsu.bean_582_2024.ApartmentFinder.views.OwnerView;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {
  // S7

  @Mock
  private UserRepository userRepository;
  @Mock
  AuthorityRepository authorityRepository;
  @Mock
  Authentication authentication;
  @InjectMocks
  private AuthService authService;
  private final Random random = new Random(Calendar.getInstance().getTimeInMillis());

  @TestCase("C71")
  @ParameterizedTest
  @EnumSource(Role.class)
  public void authenticateEachEnumTest(Role role) {
    User user = mock(User.class);
    when(authentication.getName()).thenReturn("Foo");
    when(authentication.getCredentials()).thenReturn("Bar");
    when(user.getRole()).thenReturn(role);
    when(userRepository.getUserByUsername("Foo")).thenReturn(user);

    Authentication result = authService.authenticate(authentication);

    assertNotNull(result);
    verify(authentication, times(2)).getName();
    verify(authentication).getCredentials();
    verify(user).getRole();
    verify(userRepository).getUserByUsername(anyString());
    switch(role) {
      case ADMIN -> assertEquals(AuthService.ADMIN_AUTHORITY, result.getAuthorities());
      case OWNER -> assertEquals(AuthService.OWNER_AUTHORITY, result.getAuthorities());
      case USER -> assertEquals(AuthService.USER_AUTHORITY, result.getAuthorities());
      default -> fail("Found a role not covered by the test.");
    }
  }

  @TestCase("C72")
  @Test
  public void authenticateNullReturnsNull() {
    when(authentication.getName()).thenReturn("Foo");
    when(userRepository.getUserByUsername("Foo")).thenReturn(null);

    Authentication result = authService.authenticate(authentication);

    verify(authentication).getName();
    verify(userRepository).getUserByUsername(anyString());
    assertNull(result);
  }

  @TestCase("C73")
  @ParameterizedTest
  @ValueSource(classes = {UsernamePasswordAuthenticationToken.class, RememberMeAuthenticationToken.class})
  public void supportsTests(Class<?> clazz) {
    boolean result = authService.supports(clazz);

    assertEquals((clazz == UsernamePasswordAuthenticationToken.class), result);
  }

  @TestCase("C74")
  @Test
  public void getUserCountTest() {
    long testValue = random.nextLong(0, Long.MAX_VALUE);
    when(userRepository.count()).thenReturn(testValue);

    long result = authService.getUserCount();

    assertEquals(testValue, result);
    verify(userRepository).count();
  }

  @TestCase("C75")
  @Test
  public void deleteAuthorityTest() {
    Authority authority = mock(Authority.class);

    authService.delete(authority);

    verify(authorityRepository).delete(authority);
  }

  @TestCase("C76")
  @ParameterizedTest
  @EnumSource(Role.class)
  public void getAuthorizedRoutesTest(Role role) {
    AuthorizedRoute userRoute, ownerRoute, adminRoute;
    userRoute = new AuthorizedRoute("/home", "Home", HomeView.class);
    ownerRoute = new AuthorizedRoute("/owner", "Building Owner", OwnerView.class);
    adminRoute = new AuthorizedRoute("/admin", "Admin", AdminView.class);

    List<AuthorizedRoute> result = authService.getAuthorizedRoutes(role);

    switch(role) {
      case ADMIN -> assertEquals(List.of(userRoute, ownerRoute, adminRoute), result);
      case OWNER -> assertEquals(List.of(userRoute, ownerRoute), result);
      case USER -> assertEquals(List.of(userRoute), result);
      default -> fail("Found a role that isn't being tested for.");
    }
  }

  @TestCase("C77")
  @ParameterizedTest
  @MethodSource("usersAndCountsStream")
  public void registerTest(User user, long count, Role originalRole) {
    when(userRepository.count()).thenReturn(count);

    authService.register(user);

    verify(userRepository).add(user);
    verify(userRepository).count();
    switch(user.getRole()) {
      case ADMIN -> verify(authorityRepository, times(3)).add(any(Authority.class));
      case OWNER -> verify(authorityRepository, times(2)).add(any(Authority.class));
      case USER -> verify(authorityRepository).add(any(Authority.class));
      default -> fail("Found a user role the test didn't account for.");
    }
    if (count == 0L)
      assertEquals(Role.ADMIN, user.getRole());
    else
      assertEquals(originalRole, user.getRole());
  }

  private static Stream<Arguments> usersAndCountsStream() {
    User testUser, testOwner, testAdmin;
    testUser = new User("TestUser", "TestPass0", Role.USER);
    testOwner = new User("TestOwner", "TestPass1", Role.OWNER);
    testAdmin = new User("TestAdmin", "TestPass2", Role.ADMIN);
    return Stream.of(arguments(testUser, 1L, Role.USER), arguments(testUser, 0L, Role.USER),
        arguments(testOwner, 1L, Role.OWNER), arguments(testOwner, 0L, Role.OWNER),
        arguments(testAdmin, 1L, Role.ADMIN), arguments(testAdmin, 0L, Role.ADMIN));
  }
}
