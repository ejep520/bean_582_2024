package edu.wsu.bean_582_2024.ApartmentFinder.views;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.router.BeforeEnterEvent;
import edu.wsu.bean_582_2024.ApartmentFinder.data.AuthorityRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UserRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.service.AuthService;
import edu.wsu.bean_582_2024.ApartmentFinder.service.TestUsers;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
public class LoginViewIntegrationTests {
  @Mock
  private UserRepository userRepository;
  @Mock
  private AuthorityRepository authorityRepository;

  /**
   * This test checks that in the absence of users in the repository that the app is properly
   * redirected.
   */
  @Test
  public void simulateWithZeroUsers() {
    when(userRepository.count()).thenReturn(0L);
    BeforeEnterEvent event = mock(BeforeEnterEvent.class);
    LoginView view = injectMocksIntoLoginView();
    
    view.beforeEnter(event);
    
    verify(event).rerouteTo(NewUserView.class);
    verifyNoMoreInteractions(userRepository, event);
    verifyNoInteractions(authorityRepository);
  }

  /**
   * This test affirms that if no request exists when an otherwise valid login is attempted that
   * the login is not allowed to continue. This is a security feature that assures the system is
   * not being manipulated.
   */
  @Test
  public void simulateAuthenticationWithoutRequest() {
    User user = mock(User.class);
    LoginEvent event = mock(LoginEvent.class);
    when(userRepository.getUserByUsername(anyString())).thenReturn(user);
    when(event.getUsername()).thenReturn(TestUsers.USERNAME_1);
    when(event.getPassword()).thenReturn(TestUsers.USER_PASSWORD_1);
    
    LoginView view = injectMocksIntoLoginView();
    view.onComponentEvent(event);
    LoginForm form = getLoginForm(view);
    
    if (form == null) {
      fail("Unable to get login form.");
      return;
    }
    assertTrue(form.isError());
    verifyNoMoreInteractions(event, userRepository);
    verifyNoInteractions(user, authorityRepository);
  }
  
  private LoginForm getLoginForm(LoginView view) {
    Component returnValue = view.getChildren().filter(LoginForm.class::isInstance)
        .findFirst().orElse(null);
    return (LoginForm) returnValue;
  }
  
  private LoginView injectMocksIntoLoginView() {
    return new LoginView(new AuthService(userRepository, authorityRepository));
  }
}
