package edu.wsu.bean_582_2024.ApartmentFinder.views;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import edu.wsu.bean_582_2024.ApartmentFinder.TestCase;
import edu.wsu.bean_582_2024.ApartmentFinder.service.SecurityService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
public class MainLayoutTests {
  // S22
  @Mock
  private SecurityService securityService;

  @TestCase("C221")
  @DisplayName("Simulate a page view without an authorized user")
  @Test
  public void loginScreenPreviewTest() {
    when(securityService.getAuthenticatedUser()).thenReturn(Optional.empty());
    MainLayout mainLayout = new MainLayout(securityService);

    verify(securityService, times(2)).getAuthenticatedUser();
    assertNull(mainLayout.getClassName());
    
    List<Component> children = mainLayout.getChildren().toList();

    assertEquals(2, children.size());

    HorizontalLayout horizontalLayout = (HorizontalLayout) children.stream()
        .filter(e -> e instanceof HorizontalLayout)
        .findFirst()
        .orElse(null);
    VerticalLayout verticalLayout = (VerticalLayout) children.stream()
        .filter(e -> e instanceof VerticalLayout)
        .findFirst()
        .orElse(null);
    
    assertNotNull(horizontalLayout);
    assertNotNull(verticalLayout);
    assertNull(verticalLayout.getClassName());
    assertEquals("py-0 px-m", horizontalLayout.getClassName());
    assertEquals(Alignment.CENTER, horizontalLayout.getDefaultVerticalComponentAlignment());
    assertEquals("100%", horizontalLayout.getWidth());

    List<Component> horizontalChildren = horizontalLayout.getChildren().toList();
    List<Component> verticalChildren = verticalLayout.getChildren().toList();
    
    
    assertEquals(Collections.emptyList(), verticalChildren);
    assertEquals(2, horizontalChildren.size());
    
    H1 banner = (H1) horizontalChildren.stream()
        .filter(e -> e instanceof H1)
        .findFirst()
        .orElse(null);
    DrawerToggle toggle = (DrawerToggle) horizontalChildren.stream()
        .filter(e -> e instanceof DrawerToggle)
        .findFirst()
        .orElse(null);

    assertNotNull(banner);
    assertNotNull(toggle);
    assertEquals(1d, horizontalLayout.getFlexGrow(banner));
    assertEquals("Apartment Finder", banner.getText());
    assertEquals("text-l m-m", banner.getClassName());
  }

  @TestCase("C222")
  @DisplayName("Simulate an authorized user.")
  @Test
  public void authorizedUserTest() {
    UserDetails userDetails = mock(UserDetails.class);
    when(securityService.getAuthenticatedUser()).thenReturn(Optional.of(userDetails));
    assertThrows(NullPointerException.class, () -> new MainLayout(securityService));
    
    /* ** Sadly, the rest will have to wait for integration testing. ** ELJ 2024/03/28
    List<Component> children = mainLayout.getChildren().toList();
    HorizontalLayout horizontalLayout = (HorizontalLayout) children.stream()
        .filter(e -> e instanceof HorizontalLayout)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Could not find horizontal layout."));
    VerticalLayout verticalLayout = (VerticalLayout) children.stream()
        .filter(e -> e instanceof VerticalLayout)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Could not find vertical layout."));
    List<Component> horizontalChildren = horizontalLayout.getChildren().toList();
    List<Component> verticalChildren = verticalLayout.getChildren().toList();
    Button logoutButton = (Button) horizontalChildren.stream()
        .filter(e -> e instanceof Button)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Could not find a button in the horizontal" 
            + " layout."));
    
    assertEquals(3, horizontalChildren.size());
    assertEquals(1, verticalChildren.size());
    assertEquals("Log out", logoutButton.getText());
    */
  }
}
