package edu.wsu.bean_582_2024.ApartmentFinder.service;

import java.util.ArrayList;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String name = authentication.getName();
    String pass = authentication.getCredentials().toString();
    return new UsernamePasswordAuthenticationToken(name, pass, new ArrayList<>());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
