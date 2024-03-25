package edu.wsu.bean_582_2024.ApartmentFinder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.wsu.bean_582_2024.ApartmentFinder.data.UnitRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
public class UnitServiceTests {
  @Mock
  private UnitRepository unitRepository;
  @InjectMocks
  private UnitService unitService;
  private final static String ADDRESS_1 = "Address 1";
  private final static String ADDRESS_2 = "Address 2";
  private final static String ADDRESS_3 = "Address 3";
  private final static Integer BEDROOMS_1 = 1;
  private final static Integer BEDROOMS_2 = 2;
  private final static Integer BEDROOMS_3 = 3;
  private final static Double BATHROOMS_1 = 2.5D;
  private final static Double BATHROOMS_2 = 3.0D;
  private final static Double BATHROOMS_3 = 3.5D;
  private final static Boolean FEATURED_1 = true;
  private final static Boolean FEATURED_2 = false;
  private final static Boolean FEATURED_3 = true;
  private final static String KITCHEN_1 = "Kitchen 1";
  private final static String KITCHEN_2 = "Kitchen 2";
  private final static String KITCHEN_3 = "Kitchen 3";
  private final static String LIVING_ROOM_1 = "Living Room 1";
  private final static String LIVING_ROOM_2 = "Living Room 2";
  private final static String LIVING_ROOM_3 = "Living Room 3";
  private final static String USERNAME = "testOwner";
  private final static String PASSWORD = "testPass";
  private final static Role ROLE = Role.OWNER;
  private final static User user = new User(USERNAME, PASSWORD, ROLE);
  private final static Unit unit_1 = new Unit(ADDRESS_1, BEDROOMS_1, BATHROOMS_1, LIVING_ROOM_1,
      KITCHEN_1, FEATURED_1, user);
  private final static Unit unit_2 = new Unit(ADDRESS_2, BEDROOMS_2, BATHROOMS_2, LIVING_ROOM_2,
      KITCHEN_2, FEATURED_2, user);
  private final static Unit unit_3 = new Unit(ADDRESS_3, BEDROOMS_3, BATHROOMS_3, LIVING_ROOM_3,
      KITCHEN_3, FEATURED_3, null);
  private final static List<Unit> units = List.of(unit_1, unit_2, unit_3);
  private final static User dummyAdmin = new User("DummyAdmin", "dummyPass",
      Role.ADMIN);


  @Test
  public void getAllUnitsTestSorted() {
    when(unitRepository.getAll()).thenReturn(units);
    
    List<Unit> result = unitService.getAllUnits(true);
    
    assertEquals(List.of(unit_1, unit_3, unit_2), result);
    verify(unitRepository).getAll();
  }
  
  @Test
  public void getAllUnitsTestUnsorted() {
    when(unitRepository.getAll()).thenReturn(units);

    List<Unit> result = unitService.getAllUnits(false);

    assertEquals(units, result);
    verify(unitRepository).getAll();
  }
  
  @Test
  public void findUnitsTest() {
    when(unitRepository.search(anyString())).thenReturn(Collections.emptyList());
    
    List<Unit> results = unitService.findUnits("Test Data");
    
    verify(unitRepository).search(anyString());
    assertEquals(Collections.emptyList(), results);
  }
  
  @Test
  public void deleteUnitTest() {
    unitService.deleteUnit(unit_1);
    
    verify(unitRepository).update(unit_1);
    verify(unitRepository).delete(unit_1);
  }

  @Test
  public void saveUnitTest() {
    unit_2.setId(2L);
    
    unitService.saveUnit(unit_1);
    unitService.saveUnit(unit_2);
    
    verify(unitRepository).add(unit_1);
    verify(unitRepository).update(unit_2);
  }
  
  @ParameterizedTest
  @MethodSource("userStream")
  @MockitoSettings(strictness = Strictness.LENIENT)
  public void getUsersUnitsTest(User testUser) {
    List<Unit> partialList = List.of(unit_1, unit_2);
    when(unitRepository.getAll()).thenReturn(units);
    when(unitRepository.findByUser(user)).thenReturn(partialList);

    List<Unit> result = unitService.getUsersUnits(testUser);
    
    if (testUser == null) {
      assertEquals(Collections.emptyList(), result);
      verify(unitRepository, times(0)).getAll();
      verify(unitRepository, times(0)).findByUser(any(User.class));
    } else if (testUser == user) {
      assertEquals(partialList, result);
      verify(unitRepository, times(0)).getAll();
      verify(unitRepository).findByUser(any(User.class));
    } else {
      assertEquals(units, result);
      verify(unitRepository).getAll();
      verify(unitRepository, times(0)).findByUser(any(User.class));
    }
  }
  
  private static Stream<Arguments> userStream() {
    return Stream.of(arguments((User) null), arguments(user), arguments(dummyAdmin));
  }

  @ParameterizedTest
  @MethodSource("userSearchKeyArgumentsStream")
  public void getUserUnitsByFilterTest(User testUser, String searchKey) {
    List<Unit> partialList = List.of(unit_1, unit_2);
    List<Unit> singularList = List.of(unit_1);
    if (testUser != null) {
      if (searchKey == null || searchKey.isBlank()) {
        if (Role.ADMIN.equals(testUser.getRole())) {
          when(unitRepository.getAll()).thenReturn(units);
        } else {
          when(unitRepository.findByUser(any(User.class))).thenReturn(partialList);
        }
      } else {
        if (Role.ADMIN.equals(testUser.getRole())) {
          when(unitRepository.search(searchKey)).thenReturn(singularList);
        } else {
          when(unitRepository.findOwnedUnitsByFilter(any(User.class), anyString())).thenReturn(singularList);
        }
      }
    }
    
    List<Unit> result = unitService.getUserUnitsByFilter(testUser, searchKey);
    
    if (testUser == null) {
      assertEquals(Collections.emptyList(), result);
      verify(unitRepository, times(0)).getAll();
      verify(unitRepository, times(0)).findByUser(any(User.class));
      verify(unitRepository, times(0)).search(anyString());
      verify(unitRepository, times(0))
          .findOwnedUnitsByFilter(any(User.class), anyString());
    } else if (testUser == user && (searchKey == null || searchKey.isBlank())) {
      assertEquals(partialList, result);
      verify(unitRepository, times(0)).getAll();
      verify(unitRepository).findByUser(user);
      verify(unitRepository, times(0)).search(anyString());
      verify(unitRepository, times(0))
          .findOwnedUnitsByFilter(any(User.class), anyString());
    } else if (testUser == user) {
      assertEquals(singularList, result);
      verify(unitRepository, times(0)).getAll();
      verify(unitRepository, times(0)).findByUser(any(User.class));
      verify(unitRepository, times(0)).search(anyString());
      verify(unitRepository).findOwnedUnitsByFilter(user, ADDRESS_1);
    } else if (searchKey == null || searchKey.isBlank()) {
      assertEquals(units, result);
      verify(unitRepository).getAll();
      verify(unitRepository, times(0)).findByUser(any(User.class));
      verify(unitRepository, times(0)).search(anyString());
      verify(unitRepository, times(0))
          .findOwnedUnitsByFilter(any(User.class), anyString());
    } else {
      verify(unitRepository, times(0)).getAll();
      verify(unitRepository, times(0)).findByUser(any(User.class));
      verify(unitRepository).search(ADDRESS_1);
      verify(unitRepository, times(0))
          .findOwnedUnitsByFilter(any(User.class), anyString());
    }
  }
  
  private static Stream<Arguments> userSearchKeyArgumentsStream() {
    return Stream.of(arguments(null, null), arguments(null, " "), 
        arguments(null, ADDRESS_1), arguments(user, null), arguments(user, " "),
        arguments(user, ADDRESS_1), arguments(dummyAdmin, null), arguments(dummyAdmin, " "),
        arguments(dummyAdmin, ADDRESS_1));
  }
}
