package edu.wsu.bean_582_2024.ApartmentFinder.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vaadin.flow.spring.security.AuthenticationContext;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@ExtendWith(MockitoExtension.class)
public class SecurityServiceTests {
  @Mock
  private AuthenticationContext authenticationContext;
  @Mock
  private UserDetailsService userDetailsService;
  @InjectMocks
  private SecurityService securityService;
  
  @ParameterizedTest
  @MethodSource("userStream")
  public void getAuthenticatedUser(String userPrincipal) {
    Optional<String> passedPrincipal = Optional.ofNullable(userPrincipal);
    UserDetails details = mock(UserDetails.class); 
    when(authenticationContext.getPrincipalName()).thenReturn(passedPrincipal);
    if (passedPrincipal.isPresent()) when(userDetailsService.loadUserByUsername(userPrincipal))
        .thenReturn(details);
    
    Optional<UserDetails> result = securityService.getAuthenticatedUser();
    
    verify(authenticationContext).getPrincipalName();
    if (userPrincipal == null) {
      verify(userDetailsService, times(0)).loadUserByUsername(anyString());
      assertTrue(result.isEmpty());
    } else {
      verify(userDetailsService).loadUserByUsername(userPrincipal);
      assertTrue(result.isPresent());
    }
  }
  
  private static Stream<Arguments> userStream() {
    return Stream.of(arguments((String)null), arguments("Foo"));
  }
  
  @Test
  public void logoutTest() {
    securityService.logout();
    
    verify(authenticationContext).logout();
  }
}
