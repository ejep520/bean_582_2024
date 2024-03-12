package edu.wsu.bean_582_2024.ApartmentFinder.service;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
import edu.wsu.bean_582_2024.ApartmentFinder.data.AuthorityRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.userService;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Authority;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.views.AdminView;
import edu.wsu.bean_582_2024.ApartmentFinder.views.HomeView;
import edu.wsu.bean_582_2024.ApartmentFinder.views.MainLayout;
import edu.wsu.bean_582_2024.ApartmentFinder.views.OwnerView;
import jakarta.servlet.ServletException;

@Service
public class AuthService implements AuthenticationProvider {
  private final userService userService;
  private final AuthorityRepository authRepository;
  private static final List<SimpleGrantedAuthority> USER_AUTHORITY =
      List.of(new SimpleGrantedAuthority("ROLE_USER"));
  private static final List<SimpleGrantedAuthority> OWNER_AUTHORITY =
      List.of(new SimpleGrantedAuthority("ROLE_OWNER"), new SimpleGrantedAuthority("ROLE_USER"));
  private static final List<GrantedAuthority> ADMIN_AUTHORITY =
      List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_OWNER"),
          new SimpleGrantedAuthority("ROLE_USER"));

  public AuthService(userService userService,
      @Qualifier("AuthImpl") AuthorityRepository authRepository) {
    this.userService = userService;
    this.authRepository = authRepository;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    User user = userService.getUserByUsername(authentication.getName());
    if (user != null) {
      switch (user.getRole()) {
        case USER -> {
          return new UsernamePasswordAuthenticationToken(authentication.getName(),
              authentication.getCredentials(), USER_AUTHORITY);
        }
        case OWNER -> {
          return new UsernamePasswordAuthenticationToken(authentication.getName(),
              authentication.getCredentials(), OWNER_AUTHORITY);
        }
        default -> {
          return new UsernamePasswordAuthenticationToken(authentication.getName(),
              authentication.getCredentials(), ADMIN_AUTHORITY);
        }
      }
    }
    return null;
  }


  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }

  public record AuthorizedRoute(String route, String name, Class<? extends Component> view) {
  }
  public static class AuthException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;
  }


  public static boolean isAuthenticated() {
    VaadinServletRequest request = VaadinServletRequest.getCurrent();
    return request != null && request.getUserPrincipal() != null;
  }

  public boolean authenticate(String username, String password) throws AuthException {
    User user = userService.getUserByUsername(username);
    VaadinServletRequest request = VaadinServletRequest.getCurrent();
    if (request == null)
      return false;
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

  @SuppressWarnings("unchecked")
  private void createRoutes(Role role) {
    RouteConfiguration.forSessionScope().removeRoute(HomeView.class);
    RouteConfiguration.forSessionScope().removeRoute(OwnerView.class);
    RouteConfiguration.forSessionScope().removeRoute(AdminView.class);
    getAuthorizedRoutes(role).forEach(route -> RouteConfiguration.forSessionScope()
        .setRoute(route.route, route.view, MainLayout.class));
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
    User user = userService.getUserByUsername(username);
    return user != null;
  }

  public void register(String username, String password, Role role) {
    User user;
    if (getUserCount() == 0 || role == Role.ADMIN) {
      userService.add(new User(username, password, Role.ADMIN));
      user = userService.getUserByUsername(username);
      for (GrantedAuthority auth : ADMIN_AUTHORITY) {
        authRepository.add(new Authority(user, auth.getAuthority()));
      }
    } else if (role == Role.OWNER) {
      userService.add(new User(username, password, Role.OWNER));
      user = userService.getUserByUsername(username);
      for (GrantedAuthority auth : OWNER_AUTHORITY) {
        authRepository.add(new Authority(user, auth.getAuthority()));
      }
    } else {
      userService.add(new User(username, password, Role.USER));
      user = userService.getUserByUsername(username);
      for (GrantedAuthority auth : USER_AUTHORITY) {
        authRepository.add(new Authority(user, auth.getAuthority()));
      }
    }
  }
  public void register(User user) {
    user.getAuthorities().clear();
    if (getUserCount() == 0) user.setRole(Role.ADMIN);
    userService.add(user);
    if (Role.ADMIN.equals(user.getRole())) {
      for (GrantedAuthority auth : ADMIN_AUTHORITY) {
        authRepository.add(new Authority(user, auth.getAuthority()));
      }
    } else if (Role.OWNER.equals(user.getRole())) {
      for (GrantedAuthority auth : OWNER_AUTHORITY) {
        authRepository.add(new Authority(user, auth.getAuthority()));
      }
    } else {
      for (GrantedAuthority auth : USER_AUTHORITY) {
        authRepository.add(new Authority(user, auth.getAuthority()));
      }
    }
  }

  public long getUserCount () {
    return userService.count();
  }
}
  
