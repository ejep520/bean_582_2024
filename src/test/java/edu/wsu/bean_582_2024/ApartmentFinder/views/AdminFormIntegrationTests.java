package edu.wsu.bean_582_2024.ApartmentFinder.views;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.Query;
import edu.wsu.bean_582_2024.ApartmentFinder.data.AuthorityRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UnitRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UserRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Authority;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.service.AuthService;
import edu.wsu.bean_582_2024.ApartmentFinder.service.TestUsers;
import edu.wsu.bean_582_2024.ApartmentFinder.service.UserService;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
public class AdminFormIntegrationTests {
  @Mock
  private UserRepository userRepository;
  @Mock
  private AuthorityRepository authorityRepository;
  @Mock
  private UnitRepository unitRepository;
  @InjectMocks
  private UserService userService;
  @InjectMocks
  private AuthService authService;
  private AdminView adminView;
  private User user_1;
  private User user_2;
  private User user_3;
  
  static {
    System.setProperty("vaadin.launch-browser", "false");
  }

  private record Children(TextField username, PasswordField pass1, PasswordField pass2,
                          ListBox<Role> role, Checkbox enabled, HorizontalLayout buttonsLayout)
  { }

  private record Buttons(Button saveButton, Button deleteButton, Button cancelButton) { }

  @BeforeEach
  public void resetUsers() {
    user_1 = new User(TestUsers.USERNAME_1, TestUsers.USER_PASSWORD_1, TestUsers.USER_ROLE_1);
    user_1.setId(1L);
    user_2 = new User(TestUsers.USERNAME_2, TestUsers.USER_PASSWORD_2, TestUsers.USER_ROLE_2);
    user_2.setId(2L);
    user_3 = new User(TestUsers.USERNAME_3, TestUsers.USER_PASSWORD_3, TestUsers.USER_ROLE_3);
    user_3.setId(3L);
  }

  /**
   * This test verifies that on instantiation that the user repository is the only repository
   * accessed and that its output is predictable.
   */
  @Test
  public void viewInitializationTest() {
    createViewWithAllUsers();

    assertEquals(List.of(user_1, user_2, user_3), adminView.getGrid().getDataProvider().fetch(new Query<>()).toList());
    verifyNoMoreInteractions(userRepository);
    verifyNoInteractions(authorityRepository, unitRepository);
  }

  /**
   * This test attempts to add a user and verifies that the calls passed thru the service to the
   * repository are predictable and correct.
   */
  @Test
  public void addUserTest() {
    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    when(userRepository.count()).thenReturn(0L);
    createViewWithNoUsers();
    Component toolbar = adminView.getChildren().filter(e -> e.getClassName().equals("toolbar"))
        .findFirst().orElseThrow();
    Button addButton = (Button) toolbar.getChildren().filter(Button.class::isInstance).findFirst()
        .orElseThrow();
    Children children = getChildren();
    Buttons buttons = getButtons(children.buttonsLayout);
    
    addButton.click();
    children.username.setValue(TestUsers.USERNAME_1);
    children.pass1.setValue(TestUsers.USER_PASSWORD_1);
    children.pass2.setValue(TestUsers.USER_PASSWORD_1);
    children.role.setValue(TestUsers.USER_ROLE_1);
    children.enabled.setValue(true);
    buttons.saveButton.click();
    
    verify(userRepository).add(userCaptor.capture());
    verify(authorityRepository, times(3)).add(any(Authority.class));
    assertNotNull(userCaptor.getValue());
    assertEquals(TestUsers.USERNAME_1, userCaptor.getValue().getUsername());
    assertTrue(userCaptor.getValue().checkPassword(TestUsers.USER_PASSWORD_1));
    verifyNoMoreInteractions(userRepository, authorityRepository);
    verifyNoInteractions(unitRepository);
  }

  /**
   * This test is similar to the one before it, but tests the validity of the commands passed when
   * deleting a user.
   */
  @Test
  public void deleteUserTest() {
    createViewWithAllUsers();
    Buttons buttons = getButtons(getChildren().buttonsLayout);
    
    adminView.getGrid().asSingleSelect().setValue(user_2);
    buttons.deleteButton.click();
    
    verify(userRepository).delete(any(User.class));
    verifyNoMoreInteractions(userRepository);
    verifyNoInteractions(unitRepository, authorityRepository);
  }
  
  private void createViewWithAllUsers() {
    when(userRepository.getAll()).thenReturn(List.of(user_1, user_2, user_3));
    adminView = new AdminView(userService, authService);
  } 
  
  private void createViewWithNoUsers() {
    when(userRepository.getAll()).thenReturn(Collections.emptyList());
    adminView = new AdminView(userService, authService);
  }

  @SuppressWarnings("unchecked")
  private Children getChildren() {
    TextField username;
    PasswordField pass1, pass2;
    ListBox<Role> role;
    Checkbox enabled;
    HorizontalLayout buttonsLayout;
    List<Component> childrenList = adminView.getAdminForm().getChildren().toList();
    username = getChild(TextField.class, childrenList);
    pass1 = getChild(PasswordField.class, childrenList, "password");
    pass2 = getChild(PasswordField.class, childrenList, "confirm password");
    role = (ListBox<Role>) getChild(ListBox.class, childrenList);
    enabled = getChild(Checkbox.class, childrenList);
    buttonsLayout = getChild(HorizontalLayout.class, childrenList);
    return new Children(username, pass1, pass2, role, enabled, buttonsLayout);
  }

  private <T extends Component> T getChild(Class<T> clazz, List<Component> children) {
    T returnValue;
    try {
      returnValue = clazz.cast(Objects.requireNonNull(children.stream()
          .filter(clazz::isInstance)
          .findFirst()
          .orElseThrow()));
    } catch (ClassCastException err) {
      System.err.print(err.getMessage());
      return null;
    }
    return returnValue;
  }

  private <T extends Component> T getChild(Class<T> clazz, List<Component> children, String className) {
    T returnValue;
    try {
      returnValue = clazz.cast(Objects.requireNonNull(children.stream()
          .filter(e -> clazz.isInstance(e) && className.equals(((PasswordField) e).getLabel()))
          .findFirst()
          .orElseThrow()));
    } catch (ClassCastException err) {
      System.err.print(err.getMessage());
      return null;
    }
    return returnValue;
  }

  private Buttons getButtons(HorizontalLayout buttonLayout) {
    List<Component> children = buttonLayout.getChildren().toList();
    Button saveButton = getButton(children, "save");
    Button deleteButton = getButton(children, "delete");
    Button cancelButton = getButton(children, "cancel");
    return new Buttons(saveButton, deleteButton, cancelButton);
  }

  private Button getButton(List<Component> children, String label) {
    return (Button) Objects.requireNonNull(children.stream()
        .filter(e -> label.equals(((Button) e).getText()))
        .findFirst()
        .orElseThrow());
  }
}
