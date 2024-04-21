package edu.wsu.bean_582_2024.ApartmentFinder.views;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.vaadin.flow.spring.security.AuthenticationContext;
import edu.wsu.bean_582_2024.ApartmentFinder.data.AuthorityRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UnitRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UserRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.service.SecurityService;
import edu.wsu.bean_582_2024.ApartmentFinder.service.TestUnits;
import edu.wsu.bean_582_2024.ApartmentFinder.service.TestUsers;
import edu.wsu.bean_582_2024.ApartmentFinder.service.UnitService;
import edu.wsu.bean_582_2024.ApartmentFinder.service.UserService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
public class OwnerFormIntegrationTests {
  @Mock
  private UnitRepository unitRepository;
  @Mock
  private AuthenticationContext authenticationContext;
  @Mock
  private UserDetailsService userDetailsService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private AuthorityRepository authorityRepository;
  @Mock
  private UserDetails userDetails;
  @Mock
  private User user;
  @InjectMocks
  private UserService userService;
  @InjectMocks
  private SecurityService securityService;
  @InjectMocks
  private UnitService unitService;
  private Unit unit_1;
  private Unit unit_2;
  private List<Unit> partialList, fullList;
  
  @BeforeEach
  public void setupOwnerView() {
    unit_1 = new Unit(TestUnits.ADDRESS_1, TestUnits.BEDROOMS_1, TestUnits.BATHROOMS_1,
        TestUnits.LIVING_ROOM_1, TestUnits.KITCHEN_1, true, user);
    unit_2 = new Unit(TestUnits.ADDRESS_1, TestUnits.BEDROOMS_2, TestUnits.BATHROOMS_2,
        TestUnits.LIVING_ROOM_2, TestUnits.KITCHEN_2, false, null);
    fullList = List.of(unit_1, unit_2);
    when(user.getRole()).thenReturn(Role.OWNER);
    when(userDetails.getUsername()).thenReturn(TestUsers.USERNAME_1);
    when(authenticationContext.getPrincipalName()).thenReturn(Optional.of(TestUsers.USERNAME_1));
    when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
    when(userRepository.getUserByUsername(anyString())).thenReturn(user);
    when(userRepository.getAll()).thenReturn(List.of(user));
    
    
  }

  /**
   * This test ensures that the instantiation of OwnerView results in predictable calls to the
   * appropriate repositories.
   */
  @Test
  public void initializationTest() {
    partialList = List.of(unit_1);
    when(unitRepository.findByUser(any(User.class))).thenReturn(partialList);
    
    new OwnerView(unitService, securityService, userService);
    
    verifyNoMoreInteractions(user, authenticationContext, userDetailsService, userRepository,
        userDetails);
    verifyNoInteractions(authorityRepository);
  }
}
