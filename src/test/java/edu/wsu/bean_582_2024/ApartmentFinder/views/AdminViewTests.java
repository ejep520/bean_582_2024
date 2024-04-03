package edu.wsu.bean_582_2024.ApartmentFinder.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.service.AuthService;
import edu.wsu.bean_582_2024.ApartmentFinder.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
public class AdminViewTests {

    static {
        // Prevent Vaadin Development mode to launch browser window
        System.setProperty("vaadin.launch-browser", "false");
    }

  private AdminView adminView;

    @Mock
    private UserService userService;
    @Mock
    private AuthService authService;
    private final static String ONE_HUNDRED_PERCENT = "100%";
    private record Children(HorizontalLayout content, HorizontalLayout toolbar) { }
    private record ToolbarChildren(TextField filterText, Button addUserButton) { }
    private Children children;

    @BeforeEach
    public void setup() {
      User user1 = new User("user1", "password1", Role.ADMIN);
      user1.setId(1L);
      User user2 = new User("user2", "password2", Role.OWNER);
      user2.setId(2L);
      User user3 = new User("user3", "password3", Role.USER);
      user3.setId(3L);

      List<User> users = List.of(user1, user2, user3);

      when(userService.getAllUsers()).thenReturn(users);
      when(userService.findUsers(anyString())).thenReturn(users);

      adminView = new AdminView(userService, authService);
      children = getChildren();
    }

    @Test
    public void viewPropertiesTest() {
      assertEquals("admin-view", adminView.getClassName());
      assertEquals(ONE_HUNDRED_PERCENT, adminView.getWidth());
      assertEquals(ONE_HUNDRED_PERCENT, adminView.getHeight());
      assertEquals(2, adminView.getComponentCount());
      assertEquals(2d, children.content.getFlexGrow(adminView.getGrid()));
      assertEquals(1d, children.content.getFlexGrow(adminView.getAdminForm()));
      assertEquals("content", children.content.getClassName());
      assertEquals(ONE_HUNDRED_PERCENT, children.content.getHeight());
      assertEquals(ONE_HUNDRED_PERCENT, children.content.getWidth());
      assertEquals(2, children.content.getComponentCount());
      assertEquals("toolbar", children.toolbar.getClassName());
      assertEquals(2, children.toolbar.getComponentCount());
      ToolbarChildren toolbarChildren = getToolbarChildren();
      assertEquals("Filter by username", toolbarChildren.filterText.getPlaceholder());
      assertTrue(toolbarChildren.filterText.isClearButtonVisible());
      assertEquals(ValueChangeMode.LAZY, toolbarChildren.filterText.getValueChangeMode());
      assertEquals("Add User", toolbarChildren.addUserButton.getText());
    }
    
    @Test
    public void gridPropertiesTest() {
      Grid<User> grid = adminView.getGrid();
      List<Column<User>> columns = grid.getColumns();
      
      assertEquals(ONE_HUNDRED_PERCENT, grid.getHeight());
      assertEquals(ONE_HUNDRED_PERCENT, grid.getWidth());
      assertEquals(3, columns.size());
      for (Column<User> column : columns) {
        assertTrue(column.isAutoWidth());
        assertTrue("username".equals(column.getKey())
          ^ "role".equals(column.getKey())
          ^ "enabled".equals(column.getKey()));
      }
    }

    @Test
    public void formShownAndClosedWhenUserIsSavedTest() {
        Grid<User> userGrid = adminView.getGrid();
        User firstUser = getNextItem(userGrid);
        
        assertEquals("user1", firstUser.getUsername());
        assertEquals(Role.ADMIN, firstUser.getRole());
        
        AdminForm adminForm = adminView.getAdminForm();

        assertFalse(adminForm.isVisible());
        assertEquals("25em", adminForm.getWidth());

        userGrid.asSingleSelect().setValue(firstUser);

        assertTrue(adminForm.isVisible());
        adminForm.save.click();
        assertFalse(adminForm.isVisible());
        verify(userService).saveUser(any(User.class));
        assertNull(adminForm.getUser());
    }

    @Test
    public void formShownAndClosedWhenUserIsDeletedTest() {
        Grid<User> userGrid = adminView.getGrid();
        User firstUser = getNextItem(userGrid);
        
        assertEquals("user1", firstUser.getUsername());
        assertEquals(Role.ADMIN, firstUser.getRole());
        
        AdminForm adminForm = adminView.getAdminForm();

        assertFalse(adminForm.isVisible());

        userGrid.asSingleSelect().setValue(firstUser);

        assertTrue(adminForm.isVisible());
        adminForm.delete.click();
        assertFalse(adminForm.isVisible());
        verify(userService).deleteUser(any(User.class));
        assertNull(adminForm.getUser());
    }

    @Test
    public void formShownAndClosedWhenUserIsCancelledTest() {
        Grid<User> userGrid = adminView.getGrid();
        User firstUser = getNextItem(userGrid);
        assertEquals("user1", firstUser.getUsername());
        assertEquals(Role.ADMIN, firstUser.getRole());
        AdminForm adminForm = adminView.getAdminForm();

        assertFalse(adminForm.isVisible());

        userGrid.asSingleSelect().setValue(firstUser);

        assertTrue(adminForm.isVisible());
        adminForm.cancel.click();
        assertFalse(adminForm.isVisible());
        verifyNoInteractions(authService);
    }

    private User getNextItem(Grid<User> grid) {
        return grid.getListDataView().getItems().iterator().next();
    }
    
    private Children getChildren() {
      List<Component> childrenList = adminView.getChildren().toList();
      HorizontalLayout content = (HorizontalLayout) childrenList.stream()
          .filter(e -> "content".equals(e.getClassName()))
          .findFirst()
          .orElseThrow(() -> new RuntimeException("Unable to find content."));
      HorizontalLayout toolbar = (HorizontalLayout) childrenList.stream()
          .filter(e -> "toolbar".equals(e.getClassName()))
          .findFirst()
          .orElseThrow(() -> new RuntimeException("Unable to find toolbar"));
      return new Children(content, toolbar);
    }
    
    private ToolbarChildren getToolbarChildren() {
      List<Component> childrenList = children.toolbar.getChildren().toList();
      TextField filterText = (TextField) childrenList.stream()
          .filter(e -> e instanceof TextField)
          .findFirst()
          .orElseThrow();
      Button addUserButton = (Button) childrenList.stream()
          .filter(e -> e instanceof Button)
          .findFirst()
          .orElseThrow();
      return new ToolbarChildren(filterText, addUserButton);
    }
}
