package edu.wsu.bean_582_2024.ApartmentFinder.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;

import edu.wsu.bean_582_2024.ApartmentFinder.views.list.LoginView;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity // <1>
@Configuration
public class SecurityConfig extends VaadinWebSecurity { // <2>

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
            auth.requestMatchers(
                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/images/*.png")).permitAll());
        http.authorizeHttpRequests(auth ->
            auth.requestMatchers(
                AntPathRequestMatcher.antMatcher("/h2-console/**")).hasRole("ADMIN"));

        super.configure(http);
        setLoginView(http, LoginView.class); // <4>
    }

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
}
