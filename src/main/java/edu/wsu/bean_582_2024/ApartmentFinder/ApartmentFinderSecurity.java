package edu.wsu.bean_582_2024.ApartmentFinder;

import edu.wsu.bean_582_2024.ApartmentFinder.service.AuthenticationManagerImpl;
import edu.wsu.bean_582_2024.ApartmentFinder.service.CustomAuthenticationProvider;
import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ApartmentFinderSecurity {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http)
    throws Exception {
    Objects.requireNonNull(http)
        .authorizeHttpRequests((authorize) ->
            authorize.anyRequest().authenticated());
        
    return http.build();
  }
  @Bean
  public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
    UserDetails user = User.builder()
        .roles("USER").build();
    UserDetails owner = User.builder()
        .roles("USER", "OWNER").build();
    UserDetails admin = User.builder()
        .roles("USER", "OWNER", "ADMIN").build();
    return new InMemoryUserDetailsManager(user, owner, admin);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Bean
  public AuthenticationManager authManager(HttpSecurity http) throws Exception {
    
    return http.getSharedObject(AuthenticationManagerImpl.class);
  }
}
