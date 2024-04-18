package edu.wsu.bean_582_2024.ApartmentFinder.views;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.Query;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UnitRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.service.UnitService;
import edu.wsu.bean_582_2024.ApartmentFinder.service.TestUnits;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HomeViewIntegrationTests {
  @Mock
  private UnitRepository unitRepository;
  @InjectMocks
  private UnitService unitService;
  private HomeView homeView;
  private Unit unit_1;
  @Mock
  private User user_1;
  private List<Unit> units;
  
  @BeforeEach
  public void resetHomeView() {
    homeView = null;
    unit_1 = new Unit(TestUnits.ADDRESS_1, 2, 2.5D, TestUnits.LIVING_ROOM_1, TestUnits.KITCHEN_1,
        true, user_1);
    Unit unit_2 = new Unit(TestUnits.ADDRESS_1, 3, 3.0D, TestUnits.LIVING_ROOM_2,
        TestUnits.KITCHEN_2,
        false, null);
    units = List.of(unit_1, unit_2);
  }
  
  @Test
  public void noUnitsWhenViewInitializedTest() {
    when(unitRepository.getAll()).thenReturn(Collections.emptyList());
    
    homeView = new HomeView(unitService);
    
    verifyNoMoreInteractions(unitRepository);
    verifyNoInteractions(user_1);
  }
  
  @Test
  public void unitsExistWhenViewInitialized() {
    when(unitRepository.getAll()).thenReturn(units);
    
    homeView = new HomeView(unitService);
    
    assertEquals(units, homeView.getGrid().getDataProvider().fetch(new Query<>()).toList());
    verifyNoMoreInteractions(unitRepository);
    verifyNoInteractions(user_1);
  }
  
  @Test
  public void updatingFilterValue() {
    when(unitRepository.getAll()).thenReturn(units);
    when(unitRepository.search(anyString())).thenReturn(List.of(unit_1));
    homeView = new HomeView(unitService);
    
    TextField filterText = getFilterTextField();
    filterText.setValue(TestUnits.ADDRESS_1);
    
    verifyNoMoreInteractions(unitRepository);
    verifyNoInteractions(user_1);
    assertEquals(List.of(unit_1), homeView.getGrid().getDataProvider().fetch(new Query<>()).toList());
  }
  
  private TextField getFilterTextField() {
    Component toolbar = Objects.requireNonNull(homeView.getChildren().filter(e -> e.getClassName().equals("toolbar"))
        .findFirst().orElse(null));
    Component filter = Objects.requireNonNull(toolbar.getChildren().findFirst().orElse(null));
    return (TextField) filter;
  }
}
