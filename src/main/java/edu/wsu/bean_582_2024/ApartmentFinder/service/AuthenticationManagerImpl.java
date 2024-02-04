package edu.wsu.bean_582_2024.ApartmentFinder.service;

import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthenticationManagerImpl implements AuthenticationManager {

  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private PasswordEncoder passwordEncoder;
  
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if (authentication == null) throw new NullPointerException();
    String userName = authentication.getName();
    String pass = authentication.getCredentials().toString();
    User user = userRepository.findById(userName).orElse(null);
    if (user == null) throw new AuthenticationCredentialsNotFoundException("Credentials not found.");
    authentication.setAuthenticated(passwordEncoder.matches(pass, user.getPassHash()));
    return authentication;
  }
}
