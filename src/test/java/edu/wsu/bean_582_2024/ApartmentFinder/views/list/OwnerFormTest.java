package edu.wsu.bean_582_2024.ApartmentFinder.views.list;

import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.views.OwnerForm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Unit testing

public class OwnerFormTest {
	private List<Unit> units = new ArrayList<>();
	private Unit unit1 = new Unit();
	private Unit unit2 = new Unit();
	
    @BeforeEach  
    public void setupData() {
    	unit1.setAddress("1815 WSU Rd");
    	unit1.setBathrooms(2.5);
    	unit1.setBedrooms(4);
    	unit1.setFeatured(true);
    	unit1.setKitchen("Gas Stove, Dishwasher");
    	unit1.setLivingRoom("Coffee Table and End Tables");
    	units.add(unit1);
    	
    	unit2.setAddress("2024 Bean Rd");
    	unit2.setBathrooms(4.0);
    	unit2.setBedrooms(5);
    	unit2.setFeatured(false);
    	unit2.setKitchen("Refrigerator, Dishwasher, Garbage Disposal");
    	unit2.setLivingRoom("Dinner Table and Chairs, Coffee Table and End Tables, Desk");
    	units.add(unit2);    	
    }
    
    @Test
    public void formFieldsPopulated() {
        OwnerForm form1 = new OwnerForm();
        form1.setUnit(unit1); 
        assertEquals("1815 WSU Rd", form1.getAddress().getValue());
        assertEquals(2.5, form1.getBathrooms().getValue());
        assertEquals(4, form1.getBedrooms().getValue());
        assertEquals(true, form1.getFeatured().getValue());
        assertEquals("Gas Stove, Dishwasher", form1.getKitchen().getValue());
        assertEquals("Coffee Table and End Tables", form1.getLivingRoom().getValue());
        
        OwnerForm form2 = new OwnerForm();
        form2.setUnit(unit2); 
        assertEquals("2024 Bean Rd", form2.getAddress().getValue());
        assertEquals(4.0, form2.getBathrooms().getValue());
        assertEquals(5, form2.getBedrooms().getValue());
        assertEquals(false, form2.getFeatured().getValue());
        assertEquals("Refrigerator, Dishwasher, Garbage Disposal", form2.getKitchen().getValue());
        assertEquals("Dinner Table and Chairs, Coffee Table and End Tables, Desk", form2.getLivingRoom().getValue());
    }
    
    @Test
    public void saveEventHasCorrectValues() {
    	OwnerForm form = new OwnerForm();
    	Unit unit1 = new Unit();
    	
    	form.setUnit(unit1); 
    	unit1.setAddress("1999 Party like st");
    	unit1.setBathrooms(2.0);
    	unit1.setBedrooms(3);
    	unit1.setFeatured(true);
    	unit1.setKitchen("Electric Stove, Garbage Disposal");
    	unit1.setLivingRoom("Dinner Table and Chairs, Desk");
    	units.add(unit1);

        AtomicReference<Unit> savedUnitRef = new AtomicReference<>(null); 
        form.addSaveListener(e -> {savedUnitRef.set(e.getUnit()); });
        
        form.getSave().click();
        
        Unit savedUnit = savedUnitRef.get();
         
        assertEquals("1999 Party like st", savedUnit.getAddress());
        assertEquals(2.0, savedUnit.getBathrooms());
        assertEquals(3, savedUnit.getBedrooms());
        assertEquals(true, savedUnit.getFeatured());
        assertEquals("Electric Stove, Garbage Disposal", savedUnit.getKitchen());
        assertEquals("Dinner Table and Chairs, Desk", savedUnit.getLivingRoom());
        
    }
    
    
}