package edu.wsu.bean_582_2024.ApartmentFinder.it;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vaadin.flow.component.grid.Grid;

import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.views.OwnerForm;
import edu.wsu.bean_582_2024.ApartmentFinder.views.OwnerView;

//Integration Testing

/*
 * If you are seeing the message "Sharing is only supported for boot loader classes because
 * bootstrap classpath has been appended" and are using Intelli-J, go to 
 * https://stackoverflow.com/questions/54205486/how-to-avoid-sharing-is-only-supported-for-boot-loader-classes-because-bootstra
 * for info about how to get rid of this warning.
 */

@SpringBootTest
public class OwnerViewTest {
	
	 static {
	        // Prevent Vaadin Development mode to launch browser window
	        System.setProperty("vaadin.launch-browser", "false");
	    }
	
	@Autowired
	private OwnerView ownerView;
	
	 @Test
	 @ExtendWith(SpringExtension.class)
   public void formShownWhenUnitSelected() {
		 Grid<Unit> grid = ownerView.getGrid();
		 Unit firstUnit = getFirstItem(grid);
		 
		 OwnerForm form = ownerView.getOwnerForm();
		 System.out.println(firstUnit.getAddress());
		 Assertions.assertFalse(form.isVisible());
		 grid.asSingleSelect().setValue(firstUnit);
		 
		 Assertions.assertTrue(form.isVisible());
		 Assertions.assertEquals(firstUnit.getAddress(), form.getAddress().getValue());
	    }

	private Unit getFirstItem(Grid<Unit> grid) {
		return grid.getListDataView().getItems().iterator().next();
	}
}
