package edu.wsu.bean_582_2024.ApartmentFinder.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import edu.wsu.bean_582_2024.ApartmentFinder.TestCase;
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
  // S12
  private Authority authority;
  private User user;
  private static final String GRANTED_AUTHORITY = "USER";

  @AfterEach
  public void resetFields() {
    user = null;
    authority = null;
  }

  @TestCase("C121")
  @Test
  @DisplayName("Default Authority initialization returns not null")
  public void defaultAuthorityInitReturnsNotNullTest() {
    createDefaultAuthority();
    assertNotNull(authority);
  }

  @TestCase("C122")
  @Test
  @DisplayName("Parameterized Authority initialization returns not null")
  public void parameterizedAuthorityInitializationNotNullTest() {
    createParameterizedUser();
    createParameterizedAuthority();
    assertNotNull(authority);
  }

  @TestCase("C123")
  @Test
  @DisplayName("Parameterized Authority initialization with default user returns not null")
  public void parameterizedAuthorityWithoutValidUserReturnsNotNull() {
    createDefaultUser();
    createParameterizedAuthority();
    assertNotNull(authority);
  }

  @TestCase("C124")
  @Test
  @DisplayName("Default Authority User is null")
  public void defaultAuthorityUserIsNull() {
    createDefaultAuthority();
    assertNull(authority.getUser());
  }

  @TestCase("C125")
  @Test
  @DisplayName("Default Authority's Authority field is null")
  public void defaultAuthorityAuthorityIsNull() {
    createDefaultAuthority();
    assertNull(authority.getAuthority());
  }

  @TestCase("C126")
  @Test
  @DisplayName("Parameterized Authority keeps its User")
  public void parameterizedAuthorityKeepsItsUser() {
    createParameterizedUser();
    createParameterizedAuthority();
    assertEquals(user, authority.getUser());
  }

  @TestCase("C127")
  @Test
  @DisplayName("Parameterized Authority keeps its authority")
  public void parameterizedAuthorityKeepsItsAuthority() {
    createParameterizedUser();
    createParameterizedAuthority();
    assertEquals(GRANTED_AUTHORITY, authority.getAuthority());
  }

  @TestCase("C128")
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
