package edu.wsu.bean_582_2024.ApartmentFinder.views;

import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;

import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//Unit testing

@Disabled
public class OwnerFormTest {
  /*
  private final Random random = new Random();
	private List<Unit> units = new ArrayList<>();
	private Unit unit1 = new Unit();
	private Unit unit2 = new Unit();
  private final Long USER_ID_1 = random.nextLong(0, Long.MAX_VALUE);
  private final Long USER_ID_2 = random.nextLong(0, Long.MAX_VALUE);
  private final Boolean IS_ADMIN_1 = random.nextBoolean();
  private final Boolean IS_ADMIN_2 = random.nextBoolean();
	
    @BeforeEach  
    public void setupData() {
    	unit1.setAddress("1815 WSU Rd");
    	unit1.setBathrooms(2.5);
    	unit1.setBedrooms(4);
    	unit1.setFeatured(true);
    	unit1.setKitchen("Gas Stove, Dishwasher");
    	unit1.setLivingRoom("Coffee Table and End Tables");
      unit1.setUser(new User());
    	units.add(unit1);
    	
    	unit2.setAddress("2024 Bean Rd");
    	unit2.setBathrooms(4.0);
    	unit2.setBedrooms(5);
    	unit2.setFeatured(false);
    	unit2.setKitchen("Refrigerator, Dishwasher, Garbage Disposal");
    	unit2.setLivingRoom("Dinner Table and Chairs, Coffee Table and End Tables, Desk");
      unit2.setUser(new User());
    	units.add(unit2);    	
    }
    
    @Test
    public void formFieldsPopulated() {
        OwnerForm form1 = new OwnerForm(new User());
        form1.setUnit(unit1); 
        assertEquals("1815 WSU Rd", form1.address.getValue());
        assertEquals(2.5, form1.bathrooms.getValue());
        assertEquals(4, form1.bedrooms.getValue());
        assertEquals(true, form1.featured.getValue());
        assertEquals("Gas Stove, Dishwasher", form1.kitchen.getValue());
        assertEquals("Coffee Table and End Tables", form1.livingRoom.getValue());
        assertEquals(new User(), form1.owner.getValue());
        
        OwnerForm form2 = new OwnerForm(new User());
        form2.setUnit(unit2); 
        assertEquals("2024 Bean Rd", form2.address.getValue());
        assertEquals(4.0, form2.bathrooms.getValue());
        assertEquals(5, form2.bedrooms.getValue());
        assertEquals(false, form2.featured.getValue());
        assertEquals("Refrigerator, Dishwasher, Garbage Disposal", form2.kitchen.getValue());
        assertEquals("Dinner Table and Chairs, Coffee Table and End Tables, Desk", form2.livingRoom.getValue());
        assertEquals(new User(), form2.owner.getValue());
    }
    
    @Test
    public void saveEventHasCorrectValues() {
    	OwnerForm form = new OwnerForm(new User());
    	Unit unit1 = new Unit();
    	
    	form.setUnit(unit1); 
    	form.address.setValue("1999 Party like st");
    	form.bathrooms.setValue(2.0d);
    	form.bedrooms.setValue(3);
    	form.featured.setValue(true);
    	form.kitchen.setValue("Electric Stove, Garbage Disposal");
    	form.livingRoom.setValue("Dinner Table and Chairs, Desk");
      form.owner.setValue((new User()).getUsername());
      
    	units.add(unit1);

        AtomicReference<Unit> savedUnitRef = new AtomicReference<>(null); 
        form.addSaveListener(e -> {savedUnitRef.set(e.getUnit()); });
        
        form.save.click();
        
        Unit savedUnit = savedUnitRef.get();
         
        assertEquals("1999 Party like st", savedUnit.getAddress());
        assertEquals(2.0, savedUnit.getBathrooms());
        assertEquals(3, savedUnit.getBedrooms());
        assertEquals(true, savedUnit.getFeatured());
        assertEquals("Electric Stove, Garbage Disposal", savedUnit.getKitchen());
        assertEquals("Dinner Table and Chairs, Desk", savedUnit.getLivingRoom());
        
    }
    
*/    
}
