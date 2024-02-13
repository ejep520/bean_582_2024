package edu.wsu.bean_582_2024.ApartmentFinder.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;

import edu.wsu.bean_582_2024.ApartmentFinder.views.LoginView;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
/*
        http.authorizeHttpRequests(auth ->
            auth.requestMatchers(
                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/images/*.png")).permitAll());
 */
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers(antMatchers("/admin")).hasRole("ADMIN")
            .requestMatchers(antMatchers("/owner")).hasAnyRole("ADMIN", "OWNER")
            .requestMatchers(antMatchers("/home")).hasRole("USER")
            .requestMatchers(antMatchers("/")).permitAll());
        super.configure(http);
        
        setLoginView(http, LoginView.class);
    }
    @Bean
    public UserDetailsService users(DataSource dataSource) {
      return new JdbcUserDetailsManager(dataSource);
    }
    /*
    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user582")
                // password = password with this hash, don't tell anybody :-)
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin582")
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin); // <5>
    }
     */
}
