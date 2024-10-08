package edu.wsu.bean_582_2024.ApartmentFinder.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.service.AuthService;

@Route(value = "/newuser", layout = MainLayout.class)
@PageTitle("Register User")
@AnonymousAllowed
public class NewUserView extends Composite<Component> implements AfterNavigationObserver {

  private final AuthService authService;

  public NewUserView(AuthService authService) {
    this.authService = authService;
    initContent();
  }

  @Override
  protected Component initContent() {
    TextField username = new TextField("Username");
    username.getElement().setAttribute("data-testid", "username");
    PasswordField password1 = new PasswordField("Password");
    password1.getElement().setAttribute("data-testid", "password1");
    PasswordField password2 = new PasswordField("Confirm password");
    password2.getElement().setAttribute("data-testid", "password2");
    Button sendButton = new Button("Send", event -> register(username.getValue(),
        password1.getValue(), password2.getValue()));
    sendButton.getElement().setAttribute("data-testid", "send");
    sendButton.addClickShortcut(Key.ENTER);
    VerticalLayout layout = new VerticalLayout(new H2("Register"), username, password1,
        password2, sendButton);
    var layoutElement = layout.getElement();
    layoutElement.setAttribute("data-testid", "newUser");
    layout.setAlignItems(Alignment.CENTER);
    return layout;
  }

  void register(String username, String password1, String password2) {
    if (username.trim().isEmpty())
      Notification.show("Enter a username");
    else if (authService.usernameTaken(username.trim()))
      Notification.show("That username has been taken. Choose another.");
    else if (password1.trim().isEmpty())
      Notification.show("Enter a password");
    else if (!password1.trim().equals(password2.trim()))
      Notification.show("Passwords don't match");
    else {
      authService.register(username, password1.trim(), Role.USER);
      Notification.show("User has been registered.");
      UI.getCurrent().navigate(LoginView.class);
    }
  }

  @Override
  public void afterNavigation(AfterNavigationEvent event) {
    if (authService.getUserCount() == 0L)
      Notification.show("The first user created will be an administrator.");
  }
}
