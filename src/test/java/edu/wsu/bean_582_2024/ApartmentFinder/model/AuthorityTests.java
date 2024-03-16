package edu.wsu.bean_582_2024.ApartmentFinder.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Authority class
 * @author Erik Jepsen &lt;erik.jepsen@wsu.edu&gt;
 */
@Tag("fast")
public final class AuthorityTests extends AuthenticationTestsTemplate {
  private Authority authority;
  private User user;
  private static final String GRANTED_AUTHORITY = "USER";

  @AfterEach
  public void resetFields() {
    user = null;
    authority = null;
  }

  @Test
  @DisplayName("Default Authority initialization returns not null")
  public void defaultAuthorityInitReturnsNotNullTest() {
    createDefaultAuthority();
    assertNotNull(authority);
  }

  @Test
  @DisplayName("Parameterized Authority initialization returns not null")
  public void parameterizedAuthorityInitializationNotNullTest() {
    createParameterizedUser();
    createParameterizedAuthority();
    assertNotNull(authority);
  }

  @Test
  @DisplayName("Parameterized Authority initialization with default user returns not null")
  public void parameterizedAuthorityWithoutValidUserReturnsNotNull() {
    createDefaultUser();
    createParameterizedAuthority();
    assertNotNull(authority);
  }

  @Test
  @DisplayName("Default Authority User is null")
  public void defaultAuthorityUserIsNull() {
    createDefaultAuthority();
    assertNull(authority.getUser());
  }

  @Test
  @DisplayName("Default Authority's Authority field is null")
  public void defaultAuthorityAuthorityIsNull() {
    createDefaultAuthority();
    assertNull(authority.getAuthority());
  }

  @Test
  @DisplayName("Parameterized Authority keeps its User")
  public void parameterizedAuthorityKeepsItsUser() {
    createParameterizedUser();
    createParameterizedAuthority();
    assertEquals(user, authority.getUser());
  }

  @Test
  @DisplayName("Parameterized Authority keeps its authority")
  public void parameterizedAuthorityKeepsItsAuthority() {
    createParameterizedUser();
    createParameterizedAuthority();
    assertEquals(GRANTED_AUTHORITY, authority.getAuthority());
  }

  @Test
  @DisplayName("Default Authority keeps its set User")
  public void defaultAuthorityKeepsItsSetUser() {
    createDefaultAuthority();
    assertNull(authority.getUser()); // Asserts the default is created properly without a user.
    createParameterizedUser(); // create the user to be inserted.
    authority.setUser(user); // do the deed.
    assertEquals(user, authority.getUser()); // Assert the change is made and correct.
  }

  private void createDefaultUser() {
    user = new User();
  }

  private void createParameterizedUser() {
    user = new User(USERNAME, PASSWORD, Role.USER);
  }

  private void createDefaultAuthority() {
    authority = new Authority();
  }

  private void createParameterizedAuthority() {
    authority = new Authority(user, GRANTED_AUTHORITY);
  }
}
