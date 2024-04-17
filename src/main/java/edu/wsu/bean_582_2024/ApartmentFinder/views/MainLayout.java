package edu.wsu.bean_582_2024.ApartmentFinder.views;

import java.io.Serial;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import edu.wsu.bean_582_2024.ApartmentFinder.service.SecurityService;

public class MainLayout extends AppLayout {
  @Serial
  private static final long serialVersionUID = 6748871135793571144L;
  private final SecurityService securityService;

  public MainLayout(SecurityService securityService) {
    this.securityService = securityService;
    createHeader();
    createDrawer();
  }

  private void createHeader() {
    HorizontalLayout header;
    H1 logo = new H1("Apartment Finder");
    logo.addClassNames("text-l", "m-m");
    if (securityService.getAuthenticatedUser().isPresent()) {
      Button logOut = new Button("Log out", e -> securityService.logout());
      header = new HorizontalLayout(new DrawerToggle(), logo, logOut);
    } else {
      header = new HorizontalLayout(new DrawerToggle(), logo);
    }
    header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
    header.expand(logo);
    header.setWidthFull();
    header.addClassNames("py-0", "px-m");

    addToNavbar(header);
  }

  private void createDrawer() {
    RouterLink homeView, ownerView, adminView;
    Optional<UserDetails> details = securityService.getAuthenticatedUser();
    if (details.isPresent()) {
      homeView = new RouterLink("Home", HomeView.class);
    } else {
      addToDrawer(new VerticalLayout());
      setDrawerOpened(false);
      return;
    }
    VerticalLayout layout = new VerticalLayout(homeView);
    if (details.get().getAuthorities().stream()
        .anyMatch(auth -> auth.getAuthority().equals("ROLE_OWNER"))) {
      ownerView = new RouterLink("Unit Editing", OwnerView.class);
      layout.add(ownerView);
    }
    if (details.get().getAuthorities().stream()
        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
      adminView = new RouterLink("User Administration", AdminView.class);
      layout.add(adminView);
    }
    homeView.setHighlightCondition(HighlightConditions.sameLocation());
    addToDrawer(layout);
  }

}
