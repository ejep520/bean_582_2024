package edu.wsu.bean_582_2024.ApartmentFinder.views;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.service.AuthService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NewUserViewTests {
  @Mock
  private AuthService authService;
  @InjectMocks
  private NewUserView newUserView;
  private final List<Component> components = new ArrayList<>();
  private final List<PasswordField> passwordFields = new ArrayList<>();
  private H2 banner;
  private Button registerButton;
  private TextField usernameField;
  private VerticalLayout verticalLayout;
  private static final String USERNAME = "Foo";
  private static final String PASSWORD = "Bar";
  
  static {
      // Prevent Vaadin Development mode to launch browser window
      System.setProperty("vaadin.launch-browser", "false");
  }
  
  @BeforeEach
  public void setUpForm() {
    verticalLayout = (VerticalLayout) newUserView.getContent();
    if (!components.addAll(verticalLayout.getChildren().toList())) {
      fail("Unable to add components to the component list!");
      return;
    }
    for (Component thisComponent : components
        .stream()
        .filter(e -> PasswordField.class.isAssignableFrom(e.getClass()))
        .toList()) {
      try {
        passwordFields.add((PasswordField) thisComponent);
      } catch (ClassCastException err) {
        fail("Unable to cast Component to PasswordField", err);
        return;
      }
    }
    registerButton = (Button) components.stream()
        .filter(e -> Button.class.isAssignableFrom(e.getClass()))
        .findFirst()
        .orElse(null);
    usernameField = (TextField) components.stream()
        .filter(e -> TextField.class.isAssignableFrom(e.getClass()))
        .findFirst()
        .orElse(null);
    banner = (H2) components.stream()
        .filter(e -> H2.class.isAssignableFrom(e.getClass()))
        .findFirst()
        .orElse(null);
  }
  
  @Test
  public void formSettingsTest() {
    assertNotNull(verticalLayout);
    assertEquals(Alignment.CENTER, verticalLayout.getAlignItems());
    assertEquals(2, passwordFields.size());
    assertNotNull(registerButton);
    assertNotNull(usernameField);
    assertNotNull(banner);
    for (PasswordField thisField : passwordFields) {
      assertTrue(thisField.getLabel().equals("Password") 
          ^ thisField.getLabel().equals("Confirm password"));
    }
    assertEquals("Username", usernameField.getLabel());
    assertEquals("Register", banner.getText());
    
  }
  
  @Test
  public void noUsernamePreventsRegistration() {
    usernameField.setValue("");
    passwordFields.forEach(e -> e.setValue(PASSWORD));
    assertThrows(IllegalStateException.class, registerButton::click); // Caused by the Notification not being able to be sent.
    verify(authService, times(0)).usernameTaken(anyString());
    verify(authService, times(0)).register(anyString(), anyString(), any(Role.class));
  }
  
  @Test
  public void takenUsernamePreventsRegistration() {
    when(authService.usernameTaken(USERNAME)).thenReturn(true);
    
    usernameField.setValue(USERNAME);
    passwordFields.forEach(e -> e.setValue(PASSWORD));
    
    assertThrows(IllegalStateException.class, registerButton::click);
    verify(authService).usernameTaken(USERNAME);
    verify(authService, times(0)).register(anyString(), anyString(), any(Role.class));
  }
  
  @Test
  public void firstEmptyPasswordPreventsRegistration() {
    when(authService.usernameTaken(USERNAME)).thenReturn(false);
    
    usernameField.setValue(USERNAME);
    passwordFields.get(1).setValue(PASSWORD);
    
    assertThrows(IllegalStateException.class, registerButton::click);
    verify(authService).usernameTaken(USERNAME);
    verify(authService, times(0)).register(anyString(), anyString(), any(Role.class));
  }

  @Test
  public void secondEmptyPasswordPreventsRegistration() {
    when(authService.usernameTaken(USERNAME)).thenReturn(false);

    usernameField.setValue(USERNAME);
    passwordFields.get(0).setValue(PASSWORD);

    assertThrows(IllegalStateException.class, registerButton::click);
    verify(authService).usernameTaken(USERNAME);
    verify(authService, times(0)).register(anyString(), anyString(), any(Role.class));
  }

  @Test
  public void mismatchedPasswordsPreventRegistration() {
    when(authService.usernameTaken(USERNAME)).thenReturn(false);

    usernameField.setValue(USERNAME);
    passwordFields.get(0).setValue(PASSWORD);
    passwordFields.get(1).setValue("FOOBAR");

    assertThrows(IllegalStateException.class, registerButton::click);
    verify(authService).usernameTaken(USERNAME);
    verify(authService, times(0)).register(anyString(), anyString(), any(Role.class));
  }

  @Test
  public void successfulRegistrationTest() {
    when(authService.usernameTaken(USERNAME)).thenReturn(false);
    ArgumentCaptor<String> usernameCaptor, passwordCaptor;
    ArgumentCaptor<Role> roleCaptor = ArgumentCaptor.forClass(Role.class);
    usernameCaptor = ArgumentCaptor.forClass(String.class);
    passwordCaptor = ArgumentCaptor.forClass(String.class);

    usernameField.setValue(USERNAME);
    passwordFields.forEach(e -> e.setValue(PASSWORD));

    assertThrows(IllegalStateException.class, registerButton::click);
    verify(authService).usernameTaken(USERNAME);
    verify(authService).register(usernameCaptor.capture(), passwordCaptor.capture(), roleCaptor
        .capture());
    assertEquals(USERNAME, usernameCaptor.getValue());
    assertEquals(PASSWORD, passwordCaptor.getValue());
    assertEquals(Role.USER, roleCaptor.getValue());
  }

}
