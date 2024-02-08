package edu.wsu.bean_582_2024.ApartmentFinder;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ApartmentFinderSecurity {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    http.authorizeHttpRequests((requests) ->
        requests
            .requestMatchers("/amin/**").hasRole("ADMIN")
            .requestMatchers("/owner/**").hasRole("OWNER")
            .requestMatchers("/user/new/**").anonymous()
            .requestMatchers("/user").hasRole("USER")
            .anyRequest().permitAll());
    return http.build();
  }
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Autowired
  public void configGlobal(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {
    auth.jdbcAuthentication().dataSource(dataSource);
  }
}
