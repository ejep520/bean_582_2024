package edu.wsu.bean_582_2024.ApartmentFinder.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wsu.bean_582_2024.ApartmentFinder.TestCase;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase
@Tag("slow")
public class UnitDaoTests {
  // S19
  private final EntityManager entityManager;
  private final UnitDao unitDao;
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
  private final User user = new User(USERNAME, PASSWORD, ROLE);
  private final Unit unit_1 = new Unit(ADDRESS_1, BEDROOMS_1, BATHROOMS_1, LIVING_ROOM_1,
      KITCHEN_1, FEATURED_1, user);
  private final Unit unit_2 = new Unit(ADDRESS_2, BEDROOMS_2, BATHROOMS_2, LIVING_ROOM_2,
      KITCHEN_2, FEATURED_2, user);
  private final Unit unit_3 = new Unit(ADDRESS_3, BEDROOMS_3, BATHROOMS_3, LIVING_ROOM_3,
      KITCHEN_3, FEATURED_3, null);
  private final List<Unit> units = List.of(unit_1, unit_2, unit_3);
  
  @Autowired
  public UnitDaoTests(EntityManagerFactory entityManagerFactory) {
    entityManager = entityManagerFactory.createEntityManager();
    unitDao = new UnitDao(entityManagerFactory);
  }
  
  @BeforeEach
  public void clearTables() {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.createQuery("delete from Unit").executeUpdate();
    entityManager.createQuery("delete from Authority").executeUpdate();
    entityManager.createQuery("delete from User").executeUpdate();
    transaction.commit();
    if (!ADDRESS_1.equals(unit_1.getAddress()))
      unit_1.setAddress(ADDRESS_1);
  }

  // C191
  @TestCase("C191")
  @Test
  public void countFunctionTest() {
    assertEquals(0L, unitDao.count());
    
    try {
      addAllUnits();
    } catch (Exception err) {
      fail(err);
      return;
    }
    
    assertEquals(3L, unitDao.count());
  }

  // C192
  @TestCase("C192")
  @Test
  public void getByIdTest() {
    try {
      addAllUnits();
    } catch (Exception err) {
      fail(err);
      return;
    }

    Optional<Unit> result = unitDao.get(unit_1.getId());
    
    if (result.isEmpty())
      fail("Unit was not found.");
    else 
      assertEquals(unit_1, result.get());
  }

  // C193
  @TestCase("C193")
  @Test
  public void getAllFunctionTest() {
    assertEquals(Collections.emptyList(), unitDao.getAll());
    
    try {
      addAllUnits();
    } catch (Exception err) {
      fail(err);
      return;
    }
    
    assertEquals(units, unitDao.getAll());
  }

  // C194
  @TestCase("C194")
  @Test
  public void saveFunctionTest() {
    EntityTransaction transaction;
    Optional<Unit> result1, result2;
    result1 = unitDao.get(1001L);
    assertTrue(result1.isEmpty());
    
    transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(user);
    transaction.commit();
    
    unitDao.save(unit_1);
    result2 = unitDao.get(unit_1.getId());
    
    if (result2.isEmpty())
      fail("Unit was not found.");
    else 
      assertEquals(unit_1, result2.get());
  }

  // C196
  @TestCase("C196")
  @Test
  public void updateFunctionTest() {
    try {
      addAllUnits();
    } catch (Exception err) {
      fail(err);
      return;
    }
    
    assertEquals(ADDRESS_1, unit_1.getAddress());
    
    unit_1.setAddress(ADDRESS_2);
    unitDao.update(unit_1);
    entityManager.refresh(unit_1);
    
    assertNotEquals(ADDRESS_1, unit_1.getAddress());
  }

  // C196
  @TestCase("C196")
  @Test
  public void deleteFunctionTest() {
    try {
      addAllUnits();
    } catch (Exception err) {
      fail(err);
      return;
    }
    
    assertEquals(3L, unitDao.count());
    
    unitDao.delete(unit_2);
    
    assertEquals(List.of(unit_1, unit_3), unitDao.getAll());
  }

  // C197
  @TestCase("C197")
  @Test
  public void findFunctionTest() {
    try {
      addAllUnits();
    } catch (Exception err) {
      fail(err);
      return;
    }
    
    List<Unit> result = unitDao.find(ADDRESS_3);
    
    assertEquals(List.of(unit_3), result);
  }

  // C198
  @TestCase("C198")
  @Test
  public void findFunctionWithoutKeyTest() {
    try {
      addAllUnits();
    } catch (Exception err) {
      fail(err);
      return;
    }

    List<Unit> result = unitDao.find("");
    
    assertEquals(units, result);
  }

  // C199
  @TestCase("C199")
  @Test
  public void findFunctionWithNullKeyTest() {
    try {
      addAllUnits();
    } catch (Exception err) {
      fail(err);
      return;
    }

    List<Unit> result = unitDao.find(null);
    
    assertEquals(units, result);
  }

  // C1910
  @TestCase("C1910")
  @Test
  public void findByUserFunction() {
    try {
      addAllUnits();
    } catch (Exception err) {
      fail(err);
      return;
    }
    
    List<Unit> result = unitDao.findByUser(user);
    
    assertEquals(List.of(unit_1, unit_2), result);
  }

  // C1911
  @TestCase("C1911")
  @Test
  public void findByUserFunctionWithNullUserFindsNoUnits() {
    try {
      addAllUnits();
    } catch (Exception err) {
      fail(err);
      return;
    }
    
    List<Unit> result = unitDao.findByUser(null);
    
    assertEquals(Collections.emptyList(), result);
  }

  // C1912
  @TestCase("C1912")
  @Test
  public void findOwnedUnitsWithFilterTestValidInputs() {
    try {
      addAllUnits();
    } catch (Exception err) {
      fail(err);
      return;
    }

    List<Unit> result = unitDao.findOwnedUnitsByFilter(user, ADDRESS_1);
    
    assertEquals(List.of(unit_1), result);
  }

  // C1913
  @TestCase("C1913")
  @Test
  public void findOwnedUnitsWithValidBadDataReturnsNothing() {
    try {
      addAllUnits();
    } catch (Exception err) {
      fail(err);
      return;
    }

    List<Unit> result = unitDao.findOwnedUnitsByFilter(user, ADDRESS_3);
    
    assertEquals(Collections.emptyList(), result);
  }
  
  private void addAllUnits() throws Exception {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    try {
      entityManager.persist(user);
      entityManager.persist(unit_1);
      entityManager.persist(unit_2);
      entityManager.persist(unit_3);
    } catch (Exception err) {
      transaction.rollback();
      throw err;
    }
    transaction.commit();
    for (Unit unit : List.of(unit_1, unit_2, unit_3))
      entityManager.refresh(unit);
  }
}
