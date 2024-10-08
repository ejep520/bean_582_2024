package edu.wsu.bean_582_2024.ApartmentFinder.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import edu.wsu.bean_582_2024.ApartmentFinder.service.AuthService;
import edu.wsu.bean_582_2024.ApartmentFinder.service.AuthService.AuthException;


@Route("login")
@RouteAlias("/")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout
    implements BeforeEnterObserver, ComponentEventListener<AbstractLogin.LoginEvent> {
  private static final Class<? extends Component> LOGIN_SUCCESS_URL = HomeView.class;
  private final LoginForm login = new LoginForm();
  private final AuthService authService;
  private final UI ui = UI.getCurrent();

  public LoginView(AuthService authService) {
    this.authService = authService;
    addClassName("login-view");
    setSizeFull();
    setAlignItems(Alignment.CENTER);
    setJustifyContentMode(JustifyContentMode.CENTER);
    login.addLoginListener(this);
    login.setForgotPasswordButtonVisible(false);
    add(new H1("Apartment Finder"), login, new Anchor("newuser", "Add a user"));
  }

  @Override
  public void onComponentEvent(LoginEvent loginEvent) {
    boolean authenticated;
    try {
      authenticated = authService.authenticate(loginEvent.getUsername(), loginEvent.getPassword());
    } catch (AuthException e) {
      login.setError(true);
      return;
    }
    if (authenticated) {
      ui.navigate(LOGIN_SUCCESS_URL);
    } else
      login.setError(true);
  }

  @Override
  public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
    if (authService.getUserCount() == 0L)
      beforeEnterEvent.rerouteTo(NewUserView.class);
    else if (beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error"))
      login.setError(true);
  }
}
