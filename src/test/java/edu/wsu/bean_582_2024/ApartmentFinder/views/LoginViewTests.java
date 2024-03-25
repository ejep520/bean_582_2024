package edu.wsu.bean_582_2024.ApartmentFinder.views;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.testbench.unit.UIUnitTest;
import com.vaadin.testbench.unit.ViewPackages;
import edu.wsu.bean_582_2024.ApartmentFinder.service.AuthService;
import edu.wsu.bean_582_2024.ApartmentFinder.service.AuthService.AuthException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@ViewPackages(packages = "edu.wsu.bean_582_2024.ApartmentFinder.views")
public class LoginViewTests extends UIUnitTest {
  
  static {
    System.setProperty("vaadin.launch-browser", "false");
  }

  private LoginView loginView;
  @Mock
  private AuthService authService;
  private final static String ONE_HUNDRED_PERCENT = "100%";
  @Mock
  private VaadinServletRequest request;
  @Mock
  private VaadinSession session;
  @Mock
  private RouteConfiguration configuration;
  
  @Test
  public void propertiesOfLoginView() {
    when(authService.getUserCount()).thenReturn(1L);
    loginView = new LoginView(authService);
    
    assertTrue(VerticalLayout.class.isAssignableFrom(loginView.getClass()));
    assertEquals(LoginView.CLASS_NAME, loginView.getClassName());
    assertEquals(ONE_HUNDRED_PERCENT, loginView.getHeight());
    assertEquals(ONE_HUNDRED_PERCENT, loginView.getWidth());
    assertEquals(Alignment.CENTER, loginView.getAlignItems());
    assertEquals(JustifyContentMode.CENTER, loginView.getJustifyContentMode());
    assertFalse(loginView.login.isForgotPasswordButtonVisible());
    assertEquals(3, loginView.getComponentCount());
    verify(authService).getUserCount();
  }

  @Test
  public void componentEventTestLoginError() throws AuthException {
    String username = "testUser";
    String password = "testPass";
    LoginEvent loginEvent = mock(LoginEvent.class);
    when(loginEvent.getUsername()).thenReturn(username);
    when(loginEvent.getPassword()).thenReturn(password);
    when(authService.getUserCount()).thenReturn(1L);
    when(authService.authenticate(username, password, request, session, configuration)).thenThrow(AuthException.class);
    loginView = new LoginView(authService);
    
    loginView.onComponentEvent(loginEvent);
    
    assertTrue(loginView.login.isError());
  }
  
  @Test
  public void componentEventTestLoginFalse() throws AuthException {
    String username = "testUser";
    String password = "testPass";
    LoginEvent loginEvent = mock(LoginEvent.class);
    when(loginEvent.getUsername()).thenReturn(username);
    when(loginEvent.getPassword()).thenReturn(password);
    when(authService.getUserCount()).thenReturn(1L);
    when(authService.authenticate(username, password, request, session, configuration)).thenReturn(false);
    loginView = new LoginView(authService);

    loginView.onComponentEvent(loginEvent);

    assertTrue(loginView.login.isError());
  }
  
  @Test
  public void onComponentEventSuccessfulLoginTest() throws AuthException{
    String username = "testUser";
    String password = "testPass";
    LoginEvent loginEvent = mock(LoginEvent.class);
    when(loginEvent.getUsername()).thenReturn(username);
    when(loginEvent.getPassword()).thenReturn(password);
    when(authService.getUserCount()).thenReturn(1L);
    when(authService.authenticate(username, password, request, session, configuration)).thenReturn(true);
    loginView = new LoginView(authService);

    loginView.onComponentEvent(loginEvent);
    
    assertFalse(loginView.login.isError());
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
    when(authService.getUserCount()).thenReturn(1L);
    loginView = new LoginView(authService);
    
    loginView.beforeEnter(event);
    
    assertEquals(errorCondition, loginView.login.isError());
  }
}
