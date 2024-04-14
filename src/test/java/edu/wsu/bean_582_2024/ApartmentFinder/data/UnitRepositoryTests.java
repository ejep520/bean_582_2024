package edu.wsu.bean_582_2024.ApartmentFinder.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import edu.wsu.bean_582_2024.ApartmentFinder.TestCase;
import edu.wsu.bean_582_2024.ApartmentFinder.dao.UnitDao;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UnitRepositoryTests {
  // S16
  @Mock
  private UnitDao unitDao;
  private UnitRepository unitRepository;
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
  
  public UnitRepositoryTests() {
    unit_1.setId(1L);
    unit_2.setId(2L);
    unit_3.setId(3L);
  }
  
  @BeforeEach
  public void resetRepository() {
    unitRepository = new UnitRepositoryImpl(unitDao);
  }

  @TestCase("C161")
  @Test
  public void getAllFunctionTest() {
    when(unitDao.getAll()).thenReturn(units);
    
    List<Unit> result = unitRepository.getAll();
    
    verify(unitDao).getAll();
    assertEquals(units, result);
  }

  @TestCase("C162")
  @ParameterizedTest(name="Get Unit by id function test {0}/4")
  @ValueSource(longs = {1L, 2L, 3L, 4L})
  public void getByIdFunctionTest(long id) {
    when(unitDao.get(id)).thenReturn(units.stream().filter(e -> e.getId().equals(id)).findFirst());

    Unit result = unitRepository.get(id);
    
    if (result == null)
      assertEquals(4L, id);
    else 
      assertEquals(id, result.getId());
  }

  @TestCase("C163")
  @Test
  public void saveFunctionTest() {
    ArgumentCaptor<Unit> captor = ArgumentCaptor.forClass(Unit.class);
    
    unitRepository.add(unit_1);
    
    verify(unitDao).save(captor.capture());
    assertEquals(unit_1, captor.getValue());
  }

  @TestCase("C164")
  @Test
  public void updateFunctionTest() {
    ArgumentCaptor<Unit> captor = ArgumentCaptor.forClass(Unit.class);

    unitRepository.update(unit_1);

    verify(unitDao).update(captor.capture());
    assertEquals(unit_1, captor.getValue());
  }

  @TestCase("C165")
  @Test
  public void deleteFunctionTest() {
    ArgumentCaptor<Unit> captor = ArgumentCaptor.forClass(Unit.class);

    unitRepository.delete(unit_1);

    verify(unitDao).delete(captor.capture());
    assertEquals(unit_1, captor.getValue());
  }

  @TestCase("C166")
  @ParameterizedTest
  @ValueSource(strings = {ADDRESS_1, ADDRESS_2, ADDRESS_3, "321 Nowhere Ave"})
  public void searchFunctionTest(String searchKey) {
    when(unitDao.find(searchKey))
        .thenReturn(units.stream()
            .filter(e -> e.getAddress().equals(searchKey))
            .toList());
    
    List<Unit> result = unitRepository.search(searchKey);
    
    verify(unitDao).find(searchKey);
    if (result.isEmpty())
      assertEquals("321 Nowhere Ave", searchKey);
    else
      assertEquals(searchKey, result.get(0).getAddress());
  }

  @TestCase("C167")
  @ParameterizedTest
  @MethodSource("userStream")
  public void findByOwnerFunctionTest(User searchKey) {
    when(unitDao.findByUser(any()))
        .thenReturn(units.stream()
            .filter(e -> {
              if (e.getUser() == null)
                return false;
              else return e.getUser().equals(searchKey);
            })
            .toList());
    
    List<Unit> result = unitRepository.findByUser(searchKey);
    
    if (result.size() == 2)
      assertEquals(user, searchKey);
    else 
      assertNull(searchKey);
  }
  
  private static Stream<Arguments> userStream() {
    return Stream.of(arguments(user), arguments((User) null));
  }

  @TestCase("C168")
  @ParameterizedTest
  @MethodSource("userAddressStream")
  public void findOwnedUnitsByFilter(User thisUser, String searchKey) {
    ArgumentCaptor<User> captor1 = ArgumentCaptor.forClass(User.class);
    ArgumentCaptor<String> captor2 = ArgumentCaptor.forClass(String.class);
    when(unitDao.findOwnedUnitsByFilter(thisUser, searchKey))
        .thenReturn(units.stream()
          .filter(e -> e != null
          && e.getUser() != null
          && e.getUser() == thisUser
          && e.getAddress() != null
          && e.getAddress().equals(searchKey))
          .toList());
    
    List<Unit> result = unitRepository.findOwnedUnitsByFilter(thisUser, searchKey);

    verify(unitDao).findOwnedUnitsByFilter(captor1.capture(), captor2.capture());
    assertEquals(thisUser, captor1.getValue());
    assertEquals(searchKey, captor2.getValue());
    if (result.size() == 1)
      assertEquals(unit_1, result.get(0));
    else
      assertEquals(0, result.size());
  }
  
  private static Stream<Arguments> userAddressStream() {
    return Stream.of(arguments(user, ADDRESS_1),
        arguments(null, ADDRESS_1),
        arguments(user, ADDRESS_3),
        arguments(null, ADDRESS_3));
  }

  @TestCase("C169")
  @Test
  public void countFunctionTest() {
    long thisCount = 4L;
    when(unitDao.count()).thenReturn(thisCount);
    
    long result = unitRepository.count();
    
    verify(unitDao).count();
    assertEquals(thisCount, result);
  }
}
