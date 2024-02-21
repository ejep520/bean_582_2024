package edu.wsu.bean_582_2024.ApartmentFinder.it;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;

import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.views.OwnerForm;
import edu.wsu.bean_582_2024.ApartmentFinder.views.OwnerView;

//Integration Testing

@RunWith(SpringRunner.class)
@SpringBootTest
public class OwnerViewTest {
	
	 static {
	        // Prevent Vaadin Development mode to launch browser window
	        System.setProperty("vaadin.launch-browser", "false");
	    }
	
	@Autowired
	private OwnerView ownerView;
	
	 @Test
	    public void formShownWhenUnitSelected() {
		Grid<Unit> grid = ownerView.getGrid();
		 Unit firstUnit = getFirstItem(grid);
		 
		 OwnerForm form = ownerView.getOwnerForm();
		 System.out.println(firstUnit.getAddress().toString());
		 Assert.assertFalse(form.isVisible());
		 grid.asSingleSelect().setValue(firstUnit);
		 
		 Assert.assertTrue(form.isVisible());
		 Assert.assertEquals(firstUnit.getAddress(), form.getAddress());
	    }

	private Unit getFirstItem(Grid<Unit> grid) {
		// TODO Auto-generated method stub
		return ((ListDataProvider<Unit>)grid.getDataProvider()).getItems().iterator().next();
		
	}

}
