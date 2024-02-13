package edu.wsu.bean_582_2024.ApartmentFinder.service;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UserRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.views.AdminView;
import edu.wsu.bean_582_2024.ApartmentFinder.views.MainLayout;
import edu.wsu.bean_582_2024.ApartmentFinder.views.OwnerView;
import edu.wsu.bean_582_2024.ApartmentFinder.views.HomeView;
import jakarta.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthenticationProvider {
  private static final List<GrantedAuthority> AUTHORITIES = List.of(
      (GrantedAuthority) () -> "ROLE_USER");

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    User user = userRepository.getUserByUsername(authentication.getName());
    if (user != null && user.checkPassword(authentication.getCredentials().toString())) {
      return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), AUTHORITIES);
    }
    return null;
  }

  
  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }

  public record AuthorizedRoute(String route, String name, Class<? extends Component> view) {}
   public static class AuthException extends Exception {}
  private final UserRepository userRepository;
    
  public AuthService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  public static boolean isAuthenticated() {
    VaadinServletRequest request = VaadinServletRequest.getCurrent();
    return request != null && request.getUserPrincipal() != null;
  }
  public boolean authenticate(String username, String password) throws AuthException {
    User user = userRepository.getUserByUsername(username);
    VaadinServletRequest request = VaadinServletRequest.getCurrent();
    if (request == null) return false; 
    if (user != null && user.checkPassword(password)) {
      VaadinSession.getCurrent().setAttribute(User.class, user);
      createRoutes(user.getRole());
      try {
        request.login(username, password);
      } catch (ServletException e) {
        return false;
      }
      request.getHttpServletRequest().changeSessionId();
      return true;
    } else {
      throw new AuthException();
    }
  }
  
  private void createRoutes(Role role) {
    RouteConfiguration.forSessionScope().removeRoute(HomeView.class);
    RouteConfiguration.forSessionScope().removeRoute(OwnerView.class);
    RouteConfiguration.forSessionScope().removeRoute(AdminView.class);
    getAuthorizedRoutes(role)
        .forEach(route ->
            RouteConfiguration.forSessionScope().setRoute(
                route.route, route.view, MainLayout.class
            ));
  }
  
  public List<AuthorizedRoute> getAuthorizedRoutes(Role role) {
    List<AuthorizedRoute> routes = new ArrayList<>();
    routes.add(new AuthorizedRoute("/home", "Home", HomeView.class));
    if (role.equals(Role.OWNER)) {
      routes.add(new AuthorizedRoute("/owner", "Building Owner", OwnerView.class));
    } else if (role.equals(Role.ADMIN)) {
      routes.add(new AuthorizedRoute("/owner", "Building Owner", OwnerView.class));
      routes.add(new AuthorizedRoute("/admin", "Admin", AdminView.class));
    }
    return routes;
  }
  public boolean usernameTaken(String username) {
    User user = userRepository.getUserByUsername(username);
    return user != null;
  }
  public void register(String username, String password) {
    userRepository.save(new User(username, password, Role.USER));
  }
  public static void logout() {
    UI.getCurrent().getPage().setLocation("/");
    VaadinSession.getCurrent().getSession().invalidate();
  }
}
