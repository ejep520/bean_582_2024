package edu.wsu.bean_582_2024.ApartmentFinder.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wsu.bean_582_2024.ApartmentFinder.TestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("fast")
public class UnitTests {
  // S13

  private Unit unit;
  private static final String ADDRESS = "123 Test Ave";
  private static final String BAD_ADDRESS = "321 Wrong Way";
  private static final Integer BEDROOMS = 2;
  private static final Integer BAD_BEDROOMS = 4;
  private static final Double BATHROOMS = 2.5d;
  private static final Double BAD_BATHROOMS = 0d;
  private static final String KITCHEN = "Newly Refurnished";
  private static final String BAD_KITCHEN = "Moldy and yuch!";
  private static final String LIVING_ROOM = "Fully Furnished";
  private static final String BAD_LIVING_ROOM = "If I had a black light, this place would Look like a Jackson Pollock painting.";
  private static final Boolean FEATURED = true;
  private static final Boolean BAD_FEATURED = false;
  private static final String USERNAME = "TestName";
  private static final String BAD_USERNAME = "BadName";
  private static final String PASSWORD = "TestPass";
  private static final String BAD_PASSWORD = "BadPass";
  private static final Role ROLE = Role.OWNER;
  private static final Role BAD_ROLE = Role.USER;
  private final User user = new User(USERNAME, PASSWORD, ROLE);
  private final User bad_user = new User(BAD_USERNAME, BAD_PASSWORD, BAD_ROLE);
  

  @AfterEach
  public void resetField() {
    unit = null;
  }

  @TestCase("C131")
  @Test
  @DisplayName("Default Unit Initialization is not null")
  public void defaultUnitInitializationIsNotNull() {
    initializeDefaultUnit();
    assertNotNull(unit);
  }

  @TestCase("C132")
  @Test
  @DisplayName("Parameterized Unit Initialization is not null")
  public void parameterizedUnitInitializationIsNotNull() {
    initializeParameterizedUnit();
    assertNotNull(unit);
  }

  @TestCase("C133")
  @Test
  @DisplayName("Default Unit address is not null")
  public void defaultUnitAddressIsNotNull() {
    initializeDefaultUnit();
    assertNotNull(unit.getAddress());
  }

  @TestCase("C134")
  @Test
  @DisplayName("Parameterized Unit address is not null")
  public void parameterizedUnitAddressIsNotNull() {
    initializeParameterizedUnit();
    assertNotNull(unit.getAddress());
  }

  @TestCase("C135")
  @Test
  @DisplayName("Default Unit address is empty")
  public void defaultUnitAddressIsEmpty() {
    initializeDefaultUnit();
    assertTrue(unit.getAddress().isEmpty());
  }

  @TestCase("C136")
  @Test
  @DisplayName("Parameterized Unit address is kept")
  public void parameterizedUnitAddressIsKept() {
    initializeParameterizedUnit();
    assertEquals(ADDRESS, unit.getAddress());
  }

  @TestCase("C137")
  @Test
  @DisplayName("Changed Unit address is kept")
  public void changedAddressIsKept() {
    initializeParameterizedUnit();
    assertEquals(ADDRESS, unit.getAddress()); // Asserts the original address is present.
    unit.setAddress(BAD_ADDRESS);
    assertEquals(BAD_ADDRESS, unit.getAddress()); // Asserts the address has changed.
  }

  @TestCase("C138")
  @Test
  @DisplayName("Default Unit bedrooms is zero")
  public void defaultUnitBedroomsIsZero() {
    initializeDefaultUnit();
    assertEquals(0, unit.getBedrooms());
  }

  @TestCase("C139")
  @Test
  @DisplayName("Parameterized Unit bedrooms are kept")
  public void parameterizedUnitBedroomsAreKept() {
    initializeParameterizedUnit();
    assertEquals(BEDROOMS, unit.getBedrooms());
  }

  @TestCase("C1310")
  @Test
  @DisplayName("Changed Bedrooms are kept")
  public void changedBedroomsAreKept() {
    initializeParameterizedUnit();
    assertEquals(BEDROOMS, unit.getBedrooms()); // Asserts the correct number of bedrooms to start.
    unit.setBedrooms(BAD_BEDROOMS);
    assertEquals(BAD_BEDROOMS, unit.getBedrooms()); // Asserts the number of bedrooms has changed.
  }

  @TestCase("C1311")
  @Test
  @DisplayName("Default Unit bathrooms is zero")
  public void defaultUnitBathroomsIsZero() {
    initializeDefaultUnit();
    assertEquals(0.0d, unit.getBathrooms());
  }

  @TestCase("C1312")
  @Test
  @DisplayName("Parameterized Unit bathrooms are kept")
  public void parameterizedUnitBathroomsAreKept() {
    initializeParameterizedUnit();
    assertEquals(BATHROOMS, unit.getBathrooms());
  }

  @TestCase("C1313")
  @Test
  @DisplayName("Changed Bathrooms are kept")
  public void changedBathroomsAreKept() {
    initializeParameterizedUnit();
    assertEquals(BATHROOMS, unit.getBathrooms());
    unit.setBathrooms(BAD_BATHROOMS);
    assertEquals(BAD_BATHROOMS, unit.getBathrooms());
  }

  @TestCase("C1314")
  @Test
  @DisplayName("Default Unit kitchen is empty")
  public void defaultUnitKitchenIsEmpty() {
    initializeDefaultUnit();
    assertTrue(unit.getKitchen().isEmpty());
  }

  @TestCase("C1315")
  @Test
  @DisplayName("Parameterized Unit kitchen is kept")
  public void parameterizedUnitKitchenIsKept() {
    initializeParameterizedUnit();
    assertEquals(KITCHEN, unit.getKitchen());
  }

  @TestCase("C1316")
  @Test
  @DisplayName("Changed Unit kitchen is kept")
  public void changedKitchenIsKept() {
    initializeParameterizedUnit();
    assertEquals(KITCHEN, unit.getKitchen());
    unit.setKitchen(BAD_KITCHEN);
    assertEquals(BAD_KITCHEN, unit.getKitchen());
  }

  @TestCase("C1317")
  @Test
  @DisplayName("Default Unit living room is empty")
  public void defaultUnitLivingRoomIsEmpty() {
    initializeDefaultUnit();
    assertTrue(unit.getLivingRoom().isEmpty());
  }

  @TestCase("C1318")
  @Test
  @DisplayName("Parameterized Unit living room is kept")
  public void parameterizedUnitLivingRoomIsKept() {
    initializeParameterizedUnit();
    assertEquals(LIVING_ROOM, unit.getLivingRoom());
  }

  @TestCase("C1319")
  @Test
  @DisplayName("Changed Unit living room is kept")
  public void changedUnitLivingRoomIsKept() {
    initializeParameterizedUnit();
    assertEquals(LIVING_ROOM, unit.getLivingRoom());
    unit.setLivingRoom(BAD_LIVING_ROOM);
    assertEquals(BAD_LIVING_ROOM, unit.getLivingRoom());
  }

  @TestCase("C1320")
  @Test
  @DisplayName("Default Unit featured is false")
  public void defaultUnitFeaturedIsFalse() {
    initializeDefaultUnit();
    assertFalse(unit.getFeatured());
  }

  @TestCase("C1321")
  @Test
  @DisplayName("Parameterized Unit featured is kept")
  public void parameterizedUnitFeaturedIsKept() {
    initializeParameterizedUnit();
    assertEquals(FEATURED, unit.getFeatured());
  }

  @TestCase("C1322")
  @Test
  @DisplayName("Changed Unit featured is kept")
  public void changedUnitFeaturedIsKept() {
    initializeParameterizedUnit();
    assertEquals(FEATURED, unit.getFeatured());
    unit.setFeatured(BAD_FEATURED);
    assertEquals(BAD_FEATURED, unit.getFeatured());
  }

  @TestCase("C1323")
  @Test
  @DisplayName("Default Unit has null User")
  public void defaultUnitHasNullUser() {
    initializeDefaultUnit();
    assertNull(unit.getUser());
  }

  @TestCase("C1324")
  @Test
  @DisplayName("Parameterized Unit keeps its User")
  public void parameterizedUnitKeepsItsUser() {
    initializeParameterizedUnit();
    assertEquals(user, unit.getUser());
  }

  @TestCase("C1325")
  @Test
  @DisplayName("Changed Unit keeps its User")
  public void changedUnitKeepsItsUser() {
    initializeParameterizedUnit();
    assertEquals(user, unit.getUser());
    unit.setUser(bad_user);
    assertEquals(bad_user, unit.getUser());
  }

  @TestCase("C1326")
  @Test
  @DisplayName("A unit and null are not equal")
  public void unitNullUnequal() {
    initializeParameterizedUnit();
    assertNotEquals(unit, null);
  }

  @TestCase("C1327")
  @Test
  @DisplayName("A unit is equal to itself")
  public void aUnitIsEqualToItself() {
    initializeParameterizedUnit();
    assertTrue(unit.equals(unit));
  }

  @TestCase("C1328")
  @Test
  @DisplayName("Different Units are not equal")
  public void differentUnitsNotEqual() {
    initializeParameterizedUnit();
    Unit unit2 = new Unit(BAD_ADDRESS, BAD_BEDROOMS, BAD_BATHROOMS, BAD_KITCHEN, BAD_LIVING_ROOM,
        BAD_FEATURED, bad_user);
    assertNotEquals(unit2, unit);
  }

  @TestCase("C1329")
  @Test
  @DisplayName("A unit and another class instance are not equal.")
  public void unitAndOtherClassInstanceAreNotEqual() {
    initializeParameterizedUnit();
    assertNotEquals(unit, user); // Not redundant since this tests written, owned code. 
  }

  private void initializeDefaultUnit() {
    unit = new Unit();
  }

  private void initializeParameterizedUnit() {
    unit = new Unit(ADDRESS, BEDROOMS, BATHROOMS, LIVING_ROOM, KITCHEN, FEATURED, user);
  }
}
