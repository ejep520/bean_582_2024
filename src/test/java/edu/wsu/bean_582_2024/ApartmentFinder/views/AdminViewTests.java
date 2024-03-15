package edu.wsu.bean_582_2024.ApartmentFinder.views;

import com.vaadin.flow.component.grid.Grid;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.service.AuthService;
import edu.wsu.bean_582_2024.ApartmentFinder.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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
    @BeforeEach
    public void setup() throws AuthService.AuthException {
      User user1 = new User("user1", "password1", Role.ADMIN);
      user1.setId(1L);
      User user2 = new User("user2", "password2", Role.OWNER);
      user2.setId(2L);
      User user3 = new User("user3", "password3", Role.USER);
      user3.setId(3L);

      List<User> users = List.of(user1, user2, user3);

      when(userService.getAllUsers()).thenReturn(users);
      when(userService.findUsers(anyString())).thenReturn(users);
      /*
      when(userService.findUsers(isNull())).thenReturn(users);
      when(userService.findUserByUsername("user1")).thenReturn(Optional.of(user1));
      when(userService.findUserByUsername("user2")).thenReturn(Optional.of(user2));
      when(userService.findUserByUsername("user3")).thenReturn(Optional.of(user3));
      when(userService.findUserById(0L)).thenReturn(Optional.of(user1));
      when(userService.findUserById(1L)).thenReturn(Optional.of(user2));
      when(userService.findUserById(2L)).thenReturn(Optional.of(user3));
      when(authService.authenticate("user1", "password1")).thenReturn(true);
      when(authService.authenticate("user2", "password2")).thenReturn(true);
      when(authService.authenticate("user3", "password3")).thenReturn(true);
      */
      adminView = new AdminView(userService, authService);
    }

    @Test
    public void formShownWhenUserIsSelectedTest() {
        Grid<User> userGrid = adminView.getGrid();
        User firstUser = getNextItem(userGrid);
        assertEquals("user1", firstUser.getUsername());
        assertEquals(Role.ADMIN, firstUser.getRole());
        AdminForm adminForm = adminView.getAdminForm();

        assertFalse(adminForm.isVisible());

        userGrid.asSingleSelect().setValue(firstUser);

        assertTrue(adminForm.isVisible());
    }

    private User getNextItem(Grid<User> grid) {
        return grid.getListDataView().getItems().iterator().next();
    }
}
