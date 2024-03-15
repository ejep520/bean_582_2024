package edu.wsu.bean_582_2024.ApartmentFinder.views;

import com.vaadin.flow.component.grid.Grid;
import edu.wsu.bean_582_2024.ApartmentFinder.data.AuthorityRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UnitRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UserRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.service.AuthService;
import edu.wsu.bean_582_2024.ApartmentFinder.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AdminViewTests {

    static {
        // Prevent Vaadin Development mode to launch browser window
        System.setProperty("vaadin.launch-browser", "false");
    }

    private List<User> users;

//    @Autowired
    private AdminView adminView;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @BeforeEach
    public void setup() throws AuthService.AuthException {
        userService = new UserService(userRepository, authorityRepository, unitRepository);
        authService = new AuthService(userRepository, authorityRepository);

        User user1 = new User("user1", "password1", Role.ADMIN);
        User user2 = new User("user2", "password2", Role.OWNER);
        User user3 = new User("user3", "password3", Role.USER);

        users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        userRepository.add(user1);
        userRepository.add(user2);
        userRepository.add(user3);

        when(userService.getAllUsers()).thenReturn(users);
        when(userService.findUserByUsername("user1")).thenReturn(Optional.of(user1));
        when(userService.findUserByUsername("user2")).thenReturn(Optional.of(user2));
        when(userService.findUserByUsername("user3")).thenReturn(Optional.of(user3));
        when(userService.findUserById(0L)).thenReturn(Optional.of(user1));
        when(userService.findUserById(1L)).thenReturn(Optional.of(user2));
        when(userService.findUserById(2L)).thenReturn(Optional.of(user3));

        //when(authService.authenticate("user1", "password1")).thenReturn(true);
        //when(authService.authenticate("user2", "password2")).thenReturn(true);
        //when(authService.authenticate("user3", "password3")).thenReturn(true);

        /*when(userRepository.getAll()).thenReturn(users);
        when(userRepository.getUserById(0L)).thenReturn(Optional.ofNullable(users.get(0)));
        when(userRepository.getUserById(1L)).thenReturn(Optional.ofNullable(users.get(1)));
        when(userRepository.getUserById(2L)).thenReturn(Optional.ofNullable(users.get(2)));
        when(userRepository.getUserByUsername("user1")).thenReturn(users.get(0));
        when(userRepository.getUserByUsername("user2")).thenReturn(users.get(1));
        when(userRepository.getUserByUsername("user3")).thenReturn(users.get(2));
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
        // TODO Auto-generated method stub
        return grid.getListDataView().getItems().iterator().next();

    }
}
