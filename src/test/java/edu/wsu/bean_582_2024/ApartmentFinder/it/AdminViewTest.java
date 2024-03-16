package edu.wsu.bean_582_2024.ApartmentFinder.it;

import com.vaadin.flow.component.grid.Grid;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.views.AdminForm;
import edu.wsu.bean_582_2024.ApartmentFinder.views.AdminView;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Disabled
public class AdminViewTest {

    static {
        // Prevent Vaadin Development mode to launch browser window
        System.setProperty("vaadin.launch-browser", "true");
    }

    @Autowired
    private AdminView adminView;

    @Test
    public void formShownWhenUserIsSelectedTest() {
        Grid<User> userGrid = adminView.getGrid();
        User firstUser = getFirstItem(userGrid);
        AdminForm adminForm = adminView.getAdminForm();

        assertFalse(adminForm.isVisible());

        userGrid.asSingleSelect().setValue(firstUser);

        assertTrue(adminForm.isVisible());
    }

    private User getFirstItem(Grid<User> grid) {
        // TODO Auto-generated method stub
        return grid.getListDataView().getItems().iterator().next();

    }
}
