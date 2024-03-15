package edu.wsu.bean_582_2024.ApartmentFinder.it;

import com.vaadin.flow.component.grid.Grid;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.views.AdminForm;
import edu.wsu.bean_582_2024.ApartmentFinder.views.AdminView;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminViewTest {
    static {
        // Prevent Vaadin Development mode to launch browser window
        System.setProperty("vaadin.launch-browser", "false");
    }

    @Autowired
    private AdminView adminView;

    @Test
    public void formShownWhenUserIsSelectedTest() {
        Grid<User> userGrid = adminView.getGrid();
        User firstUser = getFirstItem(userGrid);
        AdminForm adminForm = adminView.getAdminForm();

        Assert.assertFalse(adminForm.isVisible());

        userGrid.asSingleSelect().setValue(firstUser);

        Assert.assertTrue(adminForm.isVisible());

    }

    private User getFirstItem(Grid<User> grid) {
        // TODO Auto-generated method stub
        return grid.getListDataView().getItems().iterator().next();

    }
}
