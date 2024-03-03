package edu.wsu.bean_582_2024.ApartmentFinder.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("fast")
public class UnitTests {

  private Unit unit;
  private static final String ADDRESS = "123 Test Ave";
  private static final Integer BEDROOMS = 2;
  private static final Double BATHROOMS = 2.5;
  private static final String KITCHEN = "Newly Refurnished";
  private static final String LIVING_ROOM = "Fully Furnished";
  private static final Boolean FEATURED = true;

  @AfterEach
  public void resetField() {
    unit = null;
  }

  @Test
  @DisplayName("Default Unit Initialization is not null")
  public void defaultUnitInitializationIsNotNull() {
    initializeDefaultUnit();
    assertNotNull(unit);
  }

  @Test
  @DisplayName("Parameterized Unit Initialization is not null")
  public void parameterizedUnitInitializationIsNotNull() {
    initializeParameterizedUnit();
    assertNotNull(unit);
  }

  @Test
  @DisplayName("Default Unit address is not null")
  public void defaultUnitAddressIsNotNull() {
    initializeDefaultUnit();
    assertNotNull(unit.getAddress());
  }

  @Test
  @DisplayName("Parameterized Unit address is not null")
  public void parameterizedUnitAddressIsNotNull() {
    initializeParameterizedUnit();
    assertNotNull(unit.getAddress());
  }

  @Test
  @DisplayName("Default Unit address is empty")
  public void defaultUnitAddressIsEmpty() {
    initializeDefaultUnit();
    assertTrue(unit.getAddress().isEmpty());
  }

  @Test
  @DisplayName("Parameterized Unit address is kept")
  public void parameterizedUnitAddressIsKept() {
    initializeParameterizedUnit();
    assertEquals(ADDRESS, unit.getAddress());
  }

  @Test
  @DisplayName("Default Unit bedrooms is zero")
  public void defaultUnitBedroomsIsZero() {
    initializeDefaultUnit();
    assertEquals(0, unit.getBedrooms());
  }

  @Test
  @DisplayName("Parameterized Unit bedrooms are kept")
  public void parameterizedUnitBedroomsAreKept() {
    initializeParameterizedUnit();
    assertEquals(BEDROOMS, unit.getBedrooms());
  }

  @Test
  @DisplayName("Default Unit bathrooms is zero")
  public void defaultUnitBathroomsIsZero() {
    initializeDefaultUnit();
    assertEquals(0.0d, unit.getBathrooms());
  }

  @Test
  @DisplayName("Parameterized Unit bathrooms are kept")
  public void parameterizedUnitBathroomsAreKept() {
    initializeParameterizedUnit();
    assertEquals(BATHROOMS, unit.getBathrooms());
  }

  @Test
  @DisplayName("Default Unit kitchen is empty")
  public void defaultUnitKitchenIsEmpty() {
    initializeDefaultUnit();
    assertTrue(unit.getKitchen().isEmpty());
  }

  @Test
  @DisplayName("Parameterized Unit kitchen is kept")
  public void parameterizedUnitKitchenIsKept() {
    initializeParameterizedUnit();
    assertEquals(KITCHEN, unit.getKitchen());
  }

  @Test
  @DisplayName("Default Unit living room is empty")
  public void defaultUnitLivingRoomIsEmpty() {
    initializeDefaultUnit();
    assertTrue(unit.getLivingRoom().isEmpty());
  }

  @Test
  @DisplayName("Parameterized Unit living room is kept")
  public void parameterizedUnitLivingRoomIsKept() {
    initializeParameterizedUnit();
    assertEquals(LIVING_ROOM, unit.getLivingRoom());
  }

  @Test
  @DisplayName("Default Unit featured is false")
  public void defaultUnitFeaturedIsFalse() {
    initializeDefaultUnit();
    assertFalse(unit.getFeatured());
  }

  @Test
  @DisplayName("Parameterized Unit featured is kept")
  public void parameterizedUnitFeaturedIsKept() {
    initializeParameterizedUnit();
    assertEquals(FEATURED, unit.getFeatured());
  }

  private void initializeDefaultUnit() {
    unit = new Unit();
  }

  private void initializeParameterizedUnit() {
    unit = new Unit(ADDRESS, BEDROOMS, BATHROOMS, LIVING_ROOM, KITCHEN, FEATURED);
  }
}
