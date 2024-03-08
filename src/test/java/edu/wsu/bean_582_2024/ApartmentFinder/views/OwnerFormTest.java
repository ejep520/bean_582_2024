package edu.wsu.bean_582_2024.ApartmentFinder.views;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//Unit testing

public class OwnerFormTest {
	private List<Unit> units = new ArrayList<>();
	private Unit unit1 = new Unit();
	private Unit unit2 = new Unit();
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
  
  
	
    @BeforeEach  
    public void setupData() {
      units.clear();
      
    	unit1.setAddress(ADDRESS_1);
    	unit1.setBathrooms(BATHROOMS_1);
    	unit1.setBedrooms(BEDROOMS_1);
    	unit1.setFeatured(FEATURED_1);
    	unit1.setKitchen(KITCHEN_1);
    	unit1.setLivingRoom(LIVING_ROOM_1);
    	units.add(unit1);
    	
    	unit2.setAddress(ADDRESS_2);
    	unit2.setBathrooms(BATHROOMS_2);
    	unit2.setBedrooms(BEDROOMS_2);
    	unit2.setFeatured(FEATURED_2);
    	unit2.setKitchen(KITCHEN_2);
    	unit2.setLivingRoom(LIVING_ROOM_2);
    	units.add(unit2);    	
    }
    
    @Test
    @DisplayName("OwnerForm setting the Unit populates the fields")
    public void formFieldsPopulated() {
      OwnerForm form1, form2;
      
      form1 = createFormWithUnitOne();
      
      assertEquals(ADDRESS_1, form1.getAddress().getValue());
      assertEquals(BATHROOMS_1, form1.getBathrooms().getValue());
      assertEquals(BEDROOMS_1, form1.getBedrooms().getValue());
      assertTrue(form1.getFeatured().getValue());
      assertEquals(KITCHEN_1, form1.getKitchen().getValue());
      assertEquals(LIVING_ROOM_1, form1.getLivingRoom().getValue());
        
      form2 = new OwnerForm();
      form2.setUnit(unit2); 
      assertEquals(ADDRESS_2, form2.getAddress().getValue());
      assertEquals(BATHROOMS_2, form2.getBathrooms().getValue());
      assertEquals(BEDROOMS_2, form2.getBedrooms().getValue());
      assertFalse(form2.getFeatured().getValue());
      assertEquals(KITCHEN_2, form2.getKitchen().getValue());
      assertEquals(LIVING_ROOM_2, form2.getLivingRoom().getValue());
    }
    
    @Test
    @DisplayName("OwnerForm adds a listener for and fires off save event")
    public void formFiresSaveEventTest() {
      OwnerForm form = createFormWithUnitOne();
      AtomicBoolean saveFired = new AtomicBoolean(false);
      form.addSaveListener(e -> saveFired.set(true));
      form.save.click();
      assertTrue(saveFired.get());
    }
    
    @Test
    @DisplayName("OwnerForm adds a listener for and fires off delete event")
    public void formFiresDeleteEventTest() {
      OwnerForm form = createFormWithUnitOne();
      AtomicBoolean deleteFired = new AtomicBoolean(false);
      form.addDeleteListener(e -> deleteFired.set(true));
      form.delete.click();
      assertTrue(deleteFired.get());
    }
    
    @Test
    @DisplayName("OwnerForm adds listener to and fires off cancel/close event")
    public void formFiresCancelEventTest() {
      OwnerForm form = createFormWithUnitOne();
      AtomicBoolean cancelFired = new AtomicBoolean(false);
      form.addCloseListener(e -> cancelFired.set(true));
      form.cancel.click();
      assertTrue(cancelFired.get());
    }
    
    @Test
    @DisplayName("OwnerForm Save Event fired off the updated Unit")
    public void formSaveEventFiresUpdatedUnit() {
      OwnerForm form = createFormWithUnitOne();
      
      form.getAddress().setValue(ADDRESS_2);
      form.getBedrooms().setValue(BEDROOMS_2);
      form.getBathrooms().setValue(BATHROOMS_2);
      form.getFeatured().setValue(FEATURED_2);
      form.getKitchen().setValue(KITCHEN_2);
      form.getLivingRoom().setValue(LIVING_ROOM_2);

      AtomicReference<Unit> savedUnitRef = new AtomicReference<>(); 
      form.addSaveListener(e -> savedUnitRef.set(e.getUnit()));
        
      form.save.click();
        
      assertEquals(unit2, savedUnitRef.get());
    }
    
    @Test
    @DisplayName("OwnerForm will not fire if form contains invalid data")
    public void saveFormWithInvalidDataDoesntFireSave() {
      OwnerForm form = createFormWithUnitOne();
      AtomicBoolean saveFired = new AtomicBoolean(false);
      form.addSaveListener(e -> saveFired.set(true));
      
      form.getBathrooms().setValue(3.333d);
      form.save.click();
      
      assertFalse(saveFired.get());
    }
    
  private OwnerForm createFormWithUnitOne() {
      OwnerForm returnValue = new OwnerForm();
      returnValue.setUnit(unit1);
      return returnValue;
  }  
}
