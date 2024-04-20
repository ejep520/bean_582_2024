package edu.wsu.bean_582_2024.ApartmentFinder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import edu.wsu.bean_582_2024.ApartmentFinder.dao.UnitDao;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UnitRepositoryImpl;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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

  /**
   * This test class tests the integration between the unit service and the unit DAO via the unit
   * repository. This test verifies that a predictable command will be sent thru the unit
   * repository to the DAO and the result will be faithfully returned to the service.
   */
  @Test
  public void getAllTest() {
    when(unitDao.getAll()).thenReturn(unitList);
    
    List<Unit> result = unitService.getAllUnits(false);
    
    assertEquals(unitList, result);
    verifyNoMoreInteractions(unitDao);
  }

  /**
   * This test verifies that the search key is relayed through from the service layer, through
   * the repository layer to the DAO and that the DAO returns the results.
   */
  @Test
  public void findUnitsTest() {
    when(unitDao.find(TestUnits.ADDRESS_1)).thenReturn(List.of(unit_1));
    
    List<Unit> result = unitService.findUnits(TestUnits.ADDRESS_1);
    
    assertEquals(List.of(unit_1), result);
    verifyNoMoreInteractions(unitDao);
  }

  /**
   * Similar to the test before it, this test simulates a situation where no units are returned as
   * a result of the query.
   */
  @Test
  public void findUnitsTestFail() {
    when(unitDao.find(anyString())).thenReturn(Collections.emptyList());
    
    List<Unit> result = unitService.findUnits(TestUnits.ADDRESS_1);
    
    assertEquals(Collections.emptyList(), result);
    verifyNoMoreInteractions(unitDao);
  }

  /**
   * This test asserts the unit DAO will receive a call from the service layer for the count of
   * units in the database and return the result to service via the repository.
   */
  @Test
  public void getCountTest() {
    when(unitDao.count()).thenReturn(2L);
    
    long result = unitService.getUnitCount();
    
    assertEquals(2L, result);
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

  /**
   * This test analyzes the business logic in the function as well as its ability to pass needed
   * commands to the unit DAO and get back required information.
   * @param updateFlag represents whether the unit being saved already exists in the database.
   */
  @ValueSource(booleans = {false, true})
  @ParameterizedTest
  public void saveUnitTest(boolean updateFlag) {
    if (updateFlag) unit_1.setId(1L);
    
    unitService.saveUnit(unit_1);
    
    if (updateFlag) verify(unitDao).update(unit_1);
    else verify(unitDao).save(unit_1);
    verifyNoMoreInteractions(unitDao);
  }

  /**
   * Tests the business logic and integration of the layers to assure predictable commands are
   * passed from the unit service to the unit DAO when the view layer calls for units filtered
   * by user.
   * @param user The user being passed in.
   */
  @MethodSource("userStream")
  @ParameterizedTest
  public void getUserUnits(User user) {
    if (user != null && Role.ADMIN.equals(user.getRole())) {
      when(unitDao.getAll()).thenReturn(unitList);
    } else if (user != null) {
      when(unitDao.findByUser(any(User.class))).thenReturn(List.of(unit_1));
    }
    
    List<Unit> result = unitService.getUsersUnits(user);
  
    if (user == null) {
      assertEquals(Collections.emptyList(), result);
      verifyNoInteractions(unitDao);
    } else if (Role.ADMIN.equals(user.getRole())) {
      assertEquals(unitList, result);
      verify(unitDao).getAll();
      verifyNoMoreInteractions(unitDao);
    } else {
      assertEquals(List.of(unit_1), result);
      verify(unitDao).findByUser(any(User.class));
      verifyNoMoreInteractions(unitDao);
    }
  }
  
  private static Stream<Arguments> userStream() {
    User user_1 = new User(TestUsers.USERNAME_1, TestUsers.USER_PASSWORD_1, TestUsers.USER_ROLE_1);
    User user_2 = new User(TestUsers.USERNAME_2, TestUsers.USER_PASSWORD_2, TestUsers.USER_ROLE_2);
    return Stream.of(arguments((User) null), arguments(user_1), arguments(user_2));
  }

  /**
   * This test checks the business logic as well as the integration between layers when units 
   * are searched by both user and a search key.
   * @param user The user being searched by
   * @param searchKey The search term being tested
   */
  @MethodSource("userSearchKeyStream")
  @ParameterizedTest
  public void userSearchUnitsTest(User user, String searchKey) {
    if (user != null) {
      if (searchKey == null || searchKey.isBlank()) {
        if (Role.ADMIN.equals(user.getRole())) {
          when(unitDao.getAll()).thenReturn(unitList);
        } else {
          when(unitDao.findByUser(any(User.class))).thenReturn(List.of(unit_2));
        }
      } else {
        if (Role.ADMIN.equals(user.getRole())) {
          when(unitDao.find(anyString())).thenReturn(List.of(unit_1));
        } else {
          when(unitDao.findOwnedUnitsByFilter(any(User.class), anyString()))
              .thenReturn(List.of(unit_2));
        }
      }
    }
    
    List<Unit> result = unitService.getUserUnitsByFilter(user, searchKey);
    
    if (user == null) {
      assertEquals(Collections.emptyList(), result);
      verifyNoInteractions(unitDao);
      return;
    }
    if (searchKey == null || searchKey.isBlank()) {
      if (Role.ADMIN.equals(user.getRole())) {
        assertEquals(unitList, result);
        verify(unitDao).getAll();
      } else {
        assertEquals(List.of(unit_2), result);
        verify(unitDao).findByUser(any(User.class));
      }
    } else {
      if (Role.ADMIN.equals(user.getRole())) {
        assertEquals(List.of(unit_1), result);
        verify(unitDao).find(anyString());
      } else {
        assertEquals(List.of(unit_2), result);
        verify(unitDao).findOwnedUnitsByFilter(any(User.class), anyString());
      }
    }
    verifyNoMoreInteractions(unitDao);
  }
  
  private static Stream<Arguments> userSearchKeyStream() {
    User user_1 = new User(TestUsers.USERNAME_1, TestUsers.USER_PASSWORD_1, TestUsers.USER_ROLE_1);
    User user_2 = new User(TestUsers.USERNAME_2, TestUsers.USER_PASSWORD_2, TestUsers.USER_ROLE_2);
    return Stream.of(arguments(null, TestUnits.ADDRESS_1), arguments(user_1, null),
        arguments(user_1, " "), arguments(user_1, TestUnits.ADDRESS_1), arguments(user_2, null),
        arguments(user_2, " "), arguments(user_2, TestUnits.ADDRESS_1));
  }
}
