package edu.wsu.bean_582_2024.ApartmentFinder.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;

import edu.wsu.bean_582_2024.ApartmentFinder.TestCase;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

/**
 * Tests for the User class
 * @author Erik Jepsen &lt;erik.jepsen@wsu.edu&gt;
 */
@Tag("fast")
@Execution(ExecutionMode.CONCURRENT)
public class UserTests extends AuthenticationTestsTemplate{
  // S14
  private User user;
  @AfterEach
  public void RecreateUser() {
    user = null;
  }

  // C141
  @TestCase("C141")
  @Test
  @DisplayName("Creating a default user does NOT result in null")
  public void defaultUserCreationNotNull() {
    user = new User();
    assertNotNull(user);
  }

  // C142
  @TestCase("C142")
  @Test
  @DisplayName("Default User is NOT enabled")
  public void defaultUserNotEnabled() {
    user = new User();
    assertFalse(user.getEnabled());
    assertFalse(user.isEnabled()); // Duplicate code to satisfy the UserDetails interface.
  }

  // C143
  @TestCase("C143")
  @Test
  @DisplayName("Default username is not null")
  public void defaultUsernameNotNull() {
    user = new User();
    assertNotNull(user.getUsername());
  }

  // C144
  @TestCase("C144")
  @Test
  @DisplayName("Default username is empty")
  public void defaultUsernameEmpty() {
    user = new User();
    assertTrue(user.getUsername().isEmpty());
  }

  // C145
  @TestCase("C145")
  @Test
  @DisplayName("Default password is not null")
  public void defaultUserPasswordNotNull() {
    user = new User();
    assertNotNull(user.getPassword());
  }

  // C146
  @TestCase("C146")
  @Test
  @DisplayName("Default password is empty")
  public void defaultUserPasswordEmpty() {
    user = new User();
    assertTrue(user.getPassword().isEmpty());
  }

  // C147
  @TestCase("C147")
  @Test
  @DisplayName("Parameterized User keeps its username")
  public void parameterizedUsername() {
    user = new User(USERNAME, PASSWORD, Role.USER);
    assertEquals(USERNAME, user.getUsername());
  }

  // C148
  @TestCase("C148")
  @Test
  @DisplayName("Parameterized User hashes its password")
  public void parameterizedPasswordHashed() {
    user = new User(USERNAME, PASSWORD, Role.USER);
    assertNotEquals(PASSWORD, user.getPassword());
  }

  // C149
  @TestCase("C149")
  @Test
  @DisplayName("Parameterized User can check its own password (1/2)")
  public void parameterizedPasswordPasses() {
    user = new User(USERNAME, PASSWORD, Role.USER);
    assertTrue(user.checkPassword(PASSWORD));
  }

  // C1410
  @TestCase("C1410")
  @Test
  @DisplayName("Parameterized User can check its own password (2/2)")
  public void parameterizedUserWrongPasswordFails() {
    user = new User(USERNAME, PASSWORD, Role.USER);
    assertFalse(user.checkPassword(BAD_PASSWORD));
  }

  // C1411
  @TestCase("C1411")
  @Test
  @DisplayName("Default User is unexpired")
  public void defaultUserUnexpired() {
    user = new User();
    assertTrue(user.isAccountNonExpired());
  }

  // C1412
  @TestCase("C1412")
  @Test
  @DisplayName("Default User is unlocked")
  public void defaultUserUnlocked() {
    user = new User();
    assertTrue(user.isAccountNonLocked());
  }

  // C1413
  @TestCase("C1413")
  @Test
  @DisplayName("Parameterized User is enabled")
  public void parameterizedUserIsEnabled() {
    user = new User(USERNAME, PASSWORD, Role.USER);
    assertTrue(user.getEnabled());
    assertTrue(user.isEnabled()); // Duplicate code to satisfy User Details interface.
  }

  // C1414
  @TestCase("C1414")
  @Test
  @DisplayName("Default User has null role")
  public void defaultUserRoleIsNull() {
    user = new User();
    assertNull(user.getRole());
  }

  // C1415
  @TestCase("C1415")
  @Test
  @DisplayName("Parameterized User keeps its role")
  public void parameterizedUserKeepsItsRole() {
    user = new User(USERNAME, PASSWORD, Role.USER);
    assertEquals(Role.USER, user.getRole());
  }

  // C1416
  @TestCase("C1416")
  @Test
  @DisplayName("Set Username works")
  public void userSetUsernameHolds() {
    user = new User(BAD_PASSWORD, PASSWORD, Role.USER);
    assertEquals(BAD_PASSWORD, user.getUsername());
    user.setUsername(USERNAME);
    assertEquals(USERNAME, user.getUsername());
  }

  // C1417
  @TestCase("C1417")
  @Test
  @DisplayName("Set Password on Default User works")
  public void defaultUserSetPasswordTest() {
    user = new User();
    assertTrue(user.getPassword().isEmpty()); // Default user password is blank to begin
  }

  // C1418
  @TestCase("C1418")
  @Test
  @DisplayName("Set Password on Parameterized User works")
  public void parameterizedUserSetPasswordTest() {
    user = new User(USERNAME, BAD_PASSWORD, Role.USER);
    assertTrue(user.checkPassword(BAD_PASSWORD)); // Check the wrong password is present.
    user.setPassword(PASSWORD);
    assertTrue(user.checkPassword(PASSWORD)); // Check the correct password is now present.
  }

  // C1419
  @TestCase("C1419")
  @Test
  @DisplayName("SetRole works on User")
  public void userSetRoleHolds() {
    user = new User(USERNAME, PASSWORD, Role.USER);
    assertEquals(Role.USER, user.getRole()); // Check the original role is present.
    user.setRole(Role.OWNER);
    assertEquals(Role.OWNER, user.getRole()); // Check the changed role is present.
  }

  // C1420
  @TestCase("C1420")
  @Test
  @DisplayName("User GetAuthorities works")
  public void userGetAuthorities() {
    user = new User();
    assertNotNull(user.getAuthorities(), "getAuthorities is null! This shouldn't happen!");
    assertTrue(Collection.class.isAssignableFrom(user.getAuthorities().getClass()),
        "getAuthorities returned something other than a Collection!");
  }

  // C1421
  @TestCase("C1421")
  @Test
  @DisplayName("User SetEnabled works")
  public void userSetEnabledTest() {
    user = new User(USERNAME, PASSWORD, Role.USER);
    assertTrue(user.getEnabled());
    user.setEnabled(false);
    assertFalse(user.getEnabled());
    assertFalse(user.isEnabled());
  }

  // C1422
  @TestCase("C1422")
  @Test
  @DisplayName("User EraseCredentials works")
  public void userEraseCredentialsTest() { } // The function is presently empty.

  // C1423
  @TestCase("C1423")
  @Test
  @DisplayName("User Credentials are unexpired")
  public void userCredentialsAreUnexpiredTest() {
    user = new User();
    assertTrue(user.isCredentialsNonExpired());
  }

  // C1424
  @TestCase("C1424")
  @Test
  @DisplayName("Changing the password sets the Changed Password flag.")
  public void changingPasswordSetsChangedPasswordFlag() {
    user = new User(USERNAME, BAD_PASSWORD, Role.USER);
    assertTrue(user.checkPassword(BAD_PASSWORD)); // Assert the bad password is set.
    user.setNewPassword(PASSWORD);
    assertTrue(user.checkPassword(PASSWORD)); // Assert the new password is set.
    assertTrue(user.getPasswordChanged());
  }

  // C1425
  @TestCase("C1425")
  @Test
  @DisplayName("Getting units returns a list of units")
  public void gettingUnitsReturnsListOfUnits() {
    user = new User();
    assertTrue(List.class.isAssignableFrom(user.getUnits().getClass()));
  }

  // C1426
  @TestCase("C1426")
  @Test
  @DisplayName("Username is returned by User.toString")
  public void usernameIsReturnedByUserToString() {
    user = new User(USERNAME, PASSWORD, Role.USER);
    assertEquals(USERNAME, user.toString());
  }

  // C1427
  @TestCase("C1427")
  @Test
  @DisplayName("Setting new Authorities collection replaces the existing collection")
  public void settingNewAuthoritiesCollectionReplacesExistingCollection() {
    int oldHash, newHash;
    Authority auth1, auth2, auth3;
    List<Authority> oldList, newList;
    user = new User(USERNAME, PASSWORD, Role.USER);
    auth1 = new Authority(user, "USER");
    auth2 = new Authority(user, "OWNER");
    auth3 = new Authority(user, "ADMIN");
    oldList = List.of(auth1, auth2);
    newList = List.of(auth1, auth2, auth3);
    user.getAuthorities().addAll(oldList);
    oldHash = user.getAuthorities().hashCode();
    newHash = newList.hashCode();
    assertNotEquals(oldHash, newHash);
    user.setAuthorities(newList);
    assertEquals(newHash, user.getAuthorities().hashCode());
  }

  // C1428
  @TestCase("C1428")
  @Test
  @DisplayName("Changing the password to the existing password doesn't set the change flag")
  public void affirmativelyNotChangingThePasswordWontRaiseChangePasswordFlag() {
    user = new User(USERNAME, PASSWORD, Role.USER);
    assertTrue(user.checkPassword(PASSWORD)); //Assert the password is the password.
    user.setNewPassword(PASSWORD);
    assertTrue(user.checkPassword(PASSWORD)); //The password is still the password.
    assertFalse(user.getPasswordChanged()); //Assert the call to change the password didn't raise the flag.
  }

  // C1429
  @TestCase("C1429")
  @Test
  @DisplayName("Changing the salt invalidates the password hash")
  public void changingSaltInvalidatesPasswordHash() {
    user = new User(USERNAME, PASSWORD, Role.USER);
    assertTrue(user.checkPassword(PASSWORD)); // Assert the password is the password.
    user.setPasswordSalt(RandomStringUtils.random(10, true, true)); // This function exists to allow JDBC to set the salt of loaded users.
    assertFalse(user.checkPassword(PASSWORD)); // Assert the change is Password Salt invalidates the password hash.
  }
}
