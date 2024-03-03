package edu.wsu.bean_582_2024.ApartmentFinder.it;

import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vaadin.flow.component.grid.Grid;

import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.views.OwnerForm;
import edu.wsu.bean_582_2024.ApartmentFinder.views.OwnerView;

//Integration Testing

@ExtendWith(SpringExtension.class)
@Disabled // Currently not behaving properly. Remove this whole line when it's working.
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
		System.out.println(firstUnit.getAddress());
		Assertions.assertFalse(form.isVisible());
		grid.asSingleSelect().setValue(firstUnit);

		Assertions.assertTrue(form.isVisible());
		Assertions.assertEquals(firstUnit.getAddress(), form.getAddress().getValue());
	}

	private Unit getFirstItem(Grid<Unit> grid) throws NullPointerException {
		return Objects.requireNonNull(grid).getListDataView().getItems().iterator().next();
	}
}
