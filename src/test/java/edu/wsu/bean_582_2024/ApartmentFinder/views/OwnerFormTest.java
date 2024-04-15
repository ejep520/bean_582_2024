package edu.wsu.bean_582_2024.ApartmentFinder.views;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import edu.wsu.bean_582_2024.ApartmentFinder.TestCase;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//Unit testing

@ExtendWith(MockitoExtension.class)
public class OwnerFormTest {
  // S4
  private final List<Unit> units = new ArrayList<>();
  private final Unit unit1 = new Unit();
  private final Unit unit2 = new Unit();
  private final static String ADDRESS_1 = "1815 WSU Rd";
  private final static String ADDRESS_2 = "2024 Bean Rd"; 
  private final static double BATHROOMS_1 = 2.5d;
  private final static double BATHROOMS_2 = 4.0d;
  private final static int BEDROOMS_1 = 4;
  private final static int BEDROOMS_2 = 5;
  private final static boolean FEATURED_1 = true;
  private final static boolean FEATURED_2 = false;
  private final static String KITCHEN_1 = "Gas Stove, Dishwasher";
  private final static String KITCHEN_2 = "Refrigerator, Dishwasher, Garbage Disposal";
  private final static String LIVING_ROOM_1 = "Coffee Table and End Tables";
  private final static String LIVING_ROOM_2 = "Dinner Table and Chairs, Coffee Table and End" 
      + " Tables, Desk";
  private final static String USERNAME = "TestUser";
  private final static String PASSWORD = "TestPass";
  private final static Role ROLE = Role.USER;
  private final User USER = new User(USERNAME, PASSWORD, ROLE);
  private final List<User> USER_LIST = List.of(USER);
  @Mock
  private UserService userService;
  
	
  @BeforeEach  
  public void setupData() {
    units.clear();
    
    unit1.setAddress(ADDRESS_1);
    unit1.setBathrooms(BATHROOMS_1);
    unit1.setBedrooms(BEDROOMS_1);
    unit1.setFeatured(FEATURED_1);
    unit1.setKitchen(KITCHEN_1);
    unit1.setLivingRoom(LIVING_ROOM_1);
    unit1.setUser(USER);
    units.add(unit1);
    
    unit2.setAddress(ADDRESS_2);
    unit2.setBathrooms(BATHROOMS_2);
    unit2.setBedrooms(BEDROOMS_2);
    unit2.setFeatured(FEATURED_2);
    unit2.setKitchen(KITCHEN_2);
    unit2.setLivingRoom(LIVING_ROOM_2);
    unit2.setUser(USER);
    units.add(unit2);    	
    
    when(userService.getAllUsers()).thenReturn(USER_LIST);
  }

  @TestCase("C47")
  @Test
  public void propertiesTest() {
    OwnerForm form = createFormWithUnitOne();
    
    assertEquals("owner-form", form.getClassName());
    assertTrue(form.address.isRequired());
    assertFalse(form.kitchen.isRequired());
    assertFalse(form.livingRoom.isRequired());
    assertTrue(form.bedrooms.isRequired());
    assertTrue(form.bathrooms.isRequired());
    assertTrue(form.user.isRequired());
    assertEquals(0, form.bedrooms.getMin());
    assertEquals(20, form.bedrooms.getMax());
    assertEquals(1, form.bedrooms.getStep());
    assertEquals(0d, form.bathrooms.getMin());
    assertEquals(10.5d, form.bathrooms.getMax());
    assertEquals(0.5d, form.bathrooms.getStep());
    assertTrue(form.user.isReadOnly());
    assertEquals("primary", form.save.getThemeName());
    assertEquals("tertiary", form.cancel.getThemeName());
    assertEquals("error", form.delete.getThemeName());
  }


  @TestCase("C41")
  @Test
  @DisplayName("OwnerForm setting the Unit populates the fields")
  public void formFieldsPopulated() {
    OwnerForm form1, form2;
    
    form1 = createFormWithUnitOne();
    
    assertEquals(ADDRESS_1, form1.address.getValue());
    assertEquals(BATHROOMS_1, form1.bathrooms.getValue());
    assertEquals(BEDROOMS_1, form1.bedrooms.getValue());
    assertTrue(form1.featured.getValue());
    assertEquals(KITCHEN_1, form1.kitchen.getValue());
    assertEquals(LIVING_ROOM_1, form1.livingRoom.getValue());
    assertEquals(USER, form1.user.getValue());
      
    form2 = new OwnerForm(USER, userService);
    form2.setUnit(unit2); 
    assertEquals(ADDRESS_2, form2.address.getValue());
    assertEquals(BATHROOMS_2, form2.bathrooms.getValue());
    assertEquals(BEDROOMS_2, form2.bedrooms.getValue());
    assertFalse(form2.featured.getValue());
    assertEquals(KITCHEN_2, form2.kitchen.getValue());
    assertEquals(LIVING_ROOM_2, form2.livingRoom.getValue());
  }

  @TestCase("C42")
  @Test
  @DisplayName("OwnerForm adds a listener for and fires off save event")
  public void formFiresSaveEventTest() {
    OwnerForm form = createFormWithUnitOne();
    AtomicBoolean saveFired = new AtomicBoolean(false);
    form.addSaveListener(e -> saveFired.set(true));
    form.save.click();
    assertTrue(saveFired.get());
  }

  @TestCase("C43")
  @Test
  @DisplayName("OwnerForm adds a listener for and fires off delete event")
  public void formFiresDeleteEventTest() {
    OwnerForm form = createFormWithUnitOne();
    AtomicBoolean deleteFired = new AtomicBoolean(false);
    form.addDeleteListener(e -> deleteFired.set(true));
    form.delete.click();
    assertTrue(deleteFired.get());
  }

  @TestCase("C44")
  @Test
  @DisplayName("OwnerForm adds listener to and fires off cancel/close event")
  public void formFiresCancelEventTest() {
    OwnerForm form = createFormWithUnitOne();
    AtomicBoolean cancelFired = new AtomicBoolean(false);
    form.addCloseListener(e -> cancelFired.set(true));
    form.cancel.click();
    assertTrue(cancelFired.get());
  }

  @TestCase("C45")
  @Test
  @DisplayName("OwnerForm Save Event fired off the updated Unit")
  public void formSaveEventFiresUpdatedUnit() {
    OwnerForm form = createFormWithUnitOne();
    
    form.address.setValue(ADDRESS_2);
    form.bedrooms.setValue(BEDROOMS_2);
    form.bathrooms.setValue(BATHROOMS_2);
    form.featured.setValue(FEATURED_2);
    form.kitchen.setValue(KITCHEN_2);
    form.livingRoom.setValue(LIVING_ROOM_2);

    AtomicReference<Unit> savedUnitRef = new AtomicReference<>(); 
    form.addSaveListener(e -> savedUnitRef.set(e.getUnit()));
      
    form.save.click();
      
    assertEquals(unit2, savedUnitRef.get());
  }

  @TestCase("C46")
  @Test
  @DisplayName("OwnerForm will not fire if form contains invalid data")
  public void saveFormWithInvalidDataDoesntFireSave() {
    OwnerForm form = createFormWithUnitOne();
    AtomicBoolean saveFired = new AtomicBoolean(false);
    form.addSaveListener(e -> saveFired.set(true));
    
    form.bathrooms.setValue(3.333d);
    form.save.click();
    
    assertFalse(saveFired.get());
  }
    
  private OwnerForm createFormWithUnitOne() {
      OwnerForm returnValue = new OwnerForm(USER, userService);
      returnValue.setUnit(unit1);
      return returnValue;
  }  
}
