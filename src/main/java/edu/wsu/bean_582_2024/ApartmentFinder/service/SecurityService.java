package edu.wsu.bean_582_2024.ApartmentFinder.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.vaadin.flow.spring.security.AuthenticationContext;

@Service
public class SecurityService {

  private final AuthenticationContext authenticationContext;

  private final UserDetailsService userDetailsService;

  public SecurityService(AuthenticationContext authenticationContext, UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
    this.authenticationContext = authenticationContext;
  }

  public Optional<UserDetails> getAuthenticatedUser() {
    String principalName = authenticationContext.getPrincipalName().orElse(null);
    return principalName == null ? Optional.empty()
        : Optional.of(userDetailsService.loadUserByUsername(principalName));
  }

  public void logout() {
    authenticationContext.logout();
  }
}
