package edu.wsu.bean_582_2024.ApartmentFinder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import edu.wsu.bean_582_2024.ApartmentFinder.dao.UnitDao;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UnitRepositoryImpl;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
public class UnitServiceIntegrationTests {
  @Mock
  private UnitDao unitDao;
  private UnitService unitService;
  private Unit unit_1;
  private Unit unit_2;
  private List<Unit> unitList;
  
  @BeforeEach
  public void resetService() {
    unitService = new UnitService(new UnitRepositoryImpl(unitDao));
    unit_1 = new Unit(TestUnits.ADDRESS_1, TestUnits.BEDROOMS_1, TestUnits.BATHROOMS_1,
        TestUnits.LIVING_ROOM_1, TestUnits.KITCHEN_1, true, null);
    unit_2 = new Unit(TestUnits.ADDRESS_1, TestUnits.BEDROOMS_2, TestUnits.BATHROOMS_2,
        TestUnits.LIVING_ROOM_2, TestUnits.KITCHEN_2, false, null);
    unitList = List.of(unit_1, unit_2);
  }
  
  @Test
  public void getAllTest() {
    when(unitDao.getAll()).thenReturn(unitList);
    
    List<Unit> result = unitService.getAllUnits(false);
    
    assertEquals(unitList, result);
    verify(unitDao).getAll();
    verifyNoMoreInteractions(unitDao);
  }
  
  @Test
  public void findUnitsTest() {
    when(unitDao.find(TestUnits.ADDRESS_1)).thenReturn(List.of(unit_1));
    
    List<Unit> result = unitService.findUnits(TestUnits.ADDRESS_1);
    
    assertEquals(List.of(unit_1), result);
    verify(unitDao).find(TestUnits.ADDRESS_1);
    verifyNoMoreInteractions(unitDao);
  }
  
  @Test
  public void findUnitsTestFail() {
    when(unitDao.find(anyString())).thenReturn(Collections.emptyList());
    
    List<Unit> result = unitService.findUnits(TestUnits.ADDRESS_1);
    
    assertEquals(Collections.emptyList(), result);
    verify(unitDao).find(anyString());
    verifyNoMoreInteractions(unitDao);
  }
  
  @Test
  public void getCountTest() {
    when(unitDao.count()).thenReturn(2L);
    
    long result = unitService.getUnitCount();
    
    assertEquals(2L, result);
    verify(unitDao).count();
    verifyNoMoreInteractions(unitDao);
  }
  
  @Test
  public void deleteUnitTest() {
    User user = new User(TestUsers.USERNAME_1, TestUsers.USER_PASSWORD_1, TestUsers.USER_ROLE_1);
    unit_1.setUser(user);
    
    unitService.deleteUnit(unit_1);
    
    assertNull(unit_1.getUser());
    verify(unitDao).update(any(Unit.class));
    verify(unitDao).delete(any(Unit.class));
    verifyNoMoreInteractions(unitDao);
  }
  
  @ValueSource(booleans = {false, true})
  @ParameterizedTest
  public void saveUnitTest(boolean updateFlag) {
    if (updateFlag) unit_1.setId(1L);
    
    unitService.saveUnit(unit_1);
    
    if (updateFlag) verify(unitDao).update(unit_1);
    else verify(unitDao).save(unit_1);
    verifyNoMoreInteractions(unitDao);
  }
  
  
}
