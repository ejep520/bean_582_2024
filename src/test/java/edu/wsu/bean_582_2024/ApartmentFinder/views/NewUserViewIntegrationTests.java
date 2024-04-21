package edu.wsu.bean_582_2024.ApartmentFinder.views;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import edu.wsu.bean_582_2024.ApartmentFinder.data.AuthorityRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UserRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Authority;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.service.AuthService;
import edu.wsu.bean_582_2024.ApartmentFinder.service.TestUsers;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
public class NewUserViewIntegrationTests {
  @Mock
  private UserRepository userRepository;
  @Mock
  private AuthorityRepository authorityRepository;
  @InjectMocks
  private AuthService authService;
  private NewUserView newUserView;
  
  @BeforeEach
  public void resetNewUserView() {
    newUserView = new NewUserView(authService);
  }

  /**
   * This test ensures that the function to check if a username is taken passes its commands to the
   * user repository.
   */
  @Test
  public void usernameTakenTest() {
    User user = mock(User.class);
    when(userRepository.getUserByUsername(anyString())).thenReturn(user);
    
    assertThrows(IllegalStateException.class, () -> newUserView.register(TestUsers.USERNAME_1, TestUsers.USER_PASSWORD_1,
        TestUsers.USER_PASSWORD_1));
    verifyNoMoreInteractions(userRepository);
    verifyNoInteractions(user, authorityRepository);
  }

  /**
   * This test ensures the commands required by the register(String, String) method are passed on
   * to the repository and that no additional commands are passed along side-effectually.
   */
  @Test
  public void registrationTest() {
    User user = mock(User.class);
    List<Authority> authorities = new ArrayList<>();
    when(user.getAuthorities()).thenReturn(authorities);
    when(userRepository.getUserByUsername(anyString())).thenReturn(null, user);
    when(userRepository.count()).thenReturn(0L);
    
    assertThrows(IllegalStateException.class, () -> newUserView.register(TestUsers.USERNAME_1, TestUsers.USER_PASSWORD_1,
        TestUsers.USER_PASSWORD_1));
    
    verify(authorityRepository, times(3)).add(any(Authority.class));
    verify(userRepository).add(any(User.class));
    verify(userRepository).update(any(User.class));
    verify(user, times(3)).getAuthorities();
    verifyNoMoreInteractions(userRepository, authorityRepository, user);
  }
}
