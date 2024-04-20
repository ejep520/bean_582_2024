package edu.wsu.bean_582_2024.ApartmentFinder.views;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.vaadin.flow.spring.security.AuthenticationContext;
import edu.wsu.bean_582_2024.ApartmentFinder.service.SecurityService;
import edu.wsu.bean_582_2024.ApartmentFinder.service.TestUsers;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
public class MainLayoutIntegrationTests {
  @Mock
  private AuthenticationContext authenticationContext;
  @Mock
  private UserDetailsService userDetailsService;
  private MainLayout layout;
  
  @BeforeEach
  public void resetLayout() {
    layout = null;
  }

  /**
   * This test simulates what would happen when the login page is displayed and tests that commands
   * are properly forwarded to determine whether a user is logged in.
   */
  @Test
  public void loginScreenPreviewTest() {
    when(authenticationContext.getPrincipalName()).thenReturn(Optional.empty());
    
    layout = new MainLayout(new SecurityService(authenticationContext, userDetailsService));
    
    verifyNoMoreInteractions(authenticationContext);
    verifyNoInteractions(userDetailsService);
  }

  /**
   * This test asserts that a Null Pointer Exception is thrown because the underlying
   * constructs needed by static methods called during the testing do not exist. Unit
   * and integration testing that relies on these constructs would only be possible by
   * using paid software platforms, which is beyond the scope of this exercise.
   */
  @Test
  public void loggedInScreenPreviewTest() {
    when(authenticationContext.getPrincipalName()).thenReturn(Optional.of(TestUsers.USERNAME_1));
    when(userDetailsService.loadUserByUsername(anyString())).thenReturn(mock(UserDetails.class));
    
    assertThrows(NullPointerException.class, () -> new MainLayout(
        new SecurityService(authenticationContext, userDetailsService)));
    
    verifyNoMoreInteractions(userDetailsService, authenticationContext);
  }
}
