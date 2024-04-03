package edu.wsu.bean_582_2024.ApartmentFinder.views;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.QueryParameters;
import edu.wsu.bean_582_2024.ApartmentFinder.service.AuthService;
import edu.wsu.bean_582_2024.ApartmentFinder.service.AuthService.AuthException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LoginViewTests {

  static {
    System.setProperty("vaadin.launch-browser", "false");
  }

  private LoginView loginView;
  @Mock
  private AuthService authService;
  private final static String ONE_HUNDRED_PERCENT = "100%";
  private H1 banner;
  private LoginForm form;
  private Anchor anchor;
  
  @BeforeEach
  public void setUpMocks() {
    when(authService.getUserCount()).thenReturn(1L);
  }

  @Test
  public void propertiesOfLoginView() {
    loginView = new LoginView(authService);
    getLoginComponents();
    
    assertTrue(VerticalLayout.class.isAssignableFrom(loginView.getClass()));
    assertEquals("login-view", loginView.getClassName());
    assertEquals(ONE_HUNDRED_PERCENT, loginView.getHeight());
    assertEquals(ONE_HUNDRED_PERCENT, loginView.getWidth());
    assertEquals(Alignment.CENTER, loginView.getAlignItems());
    assertEquals(JustifyContentMode.CENTER, loginView.getJustifyContentMode());
    assertFalse(form.isForgotPasswordButtonVisible());
    assertEquals(3, loginView.getComponentCount());
    assertEquals("Apartment Finder", banner.getText());
    assertEquals("Add a user", anchor.getText());
    assertEquals("newuser", anchor.getHref());
    verify(authService).getUserCount();
  }

  @Test
  public void componentEventTestLoginError() throws AuthException {
    String username = "testUser";
    String password = "testPass";
    LoginEvent loginEvent = mock(LoginEvent.class);
    when(loginEvent.getUsername()).thenReturn(username);
    when(loginEvent.getPassword()).thenReturn(password);
    when(authService.authenticate(username, password)).thenThrow(AuthException.class);
    loginView = new LoginView(authService);
    loginView.onComponentEvent(loginEvent);
    getLoginComponents();

    assertTrue(form.isError());
  }

  @Test
  public void componentEventTestLoginFalse() throws AuthException {
    String username = "testUser";
    String password = "testPass";
    LoginEvent loginEvent = mock(LoginEvent.class);
    when(loginEvent.getUsername()).thenReturn(username);
    when(loginEvent.getPassword()).thenReturn(password);
    when(authService.authenticate(username, password)).thenReturn(false);
    loginView = new LoginView(authService);
    getLoginComponents();
    
    loginView.onComponentEvent(loginEvent);

    assertTrue(form.isError());
  }

  @Test
  public void onComponentEventSuccessfulLoginTest() throws AuthException{
    String username = "testUser";
    String password = "testPass";
    LoginEvent loginEvent = mock(LoginEvent.class);
    when(loginEvent.getUsername()).thenReturn(username);
    when(loginEvent.getPassword()).thenReturn(password);
    when(authService.authenticate(username, password)).thenReturn(true);
    loginView = new LoginView(authService);
    getLoginComponents();
    
    assertThrows(NullPointerException.class, () ->loginView.onComponentEvent(loginEvent));

    assertFalse(form.isError());
  }

  @SuppressWarnings("unchecked")
  @ParameterizedTest
  @ValueSource(booleans = {false, true})
  public void beforeEnterTests(boolean errorCondition) {
    BeforeEnterEvent event = mock(BeforeEnterEvent.class);
    Location location = mock(Location.class);
    QueryParameters queryParameters = mock(QueryParameters.class);
    Map<String, List<String>> parameters = (Map<String, List<String>>) mock(Map.class);
    when(event.getLocation()).thenReturn(location);
    when(location.getQueryParameters()).thenReturn(queryParameters);
    when(queryParameters.getParameters()).thenReturn(parameters);
    when(parameters.containsKey("error")).thenReturn(errorCondition);
    loginView = new LoginView(authService);
    getLoginComponents();
    loginView.beforeEnter(event);

    assertEquals(errorCondition, form.isError());
  }
  
  private <T extends Component> T extractChild(Class<T> clazz, List<Component> componentList) {
    return Objects.requireNonNull(clazz.cast(componentList.stream()
        .filter(e -> clazz.isAssignableFrom(e.getClass()))
        .findFirst().orElse(null)));
  }
  
  private void getLoginComponents() {
    List<Component> children = loginView.getChildren().toList();
    banner = extractChild(H1.class, children);
    form = extractChild(LoginForm.class, children);
    anchor = extractChild(Anchor.class, children);
  }
}
