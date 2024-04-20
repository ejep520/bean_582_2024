package edu.wsu.bean_582_2024.ApartmentFinder.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.wsu.bean_582_2024.ApartmentFinder.dao.UnitDao;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Tag("Slow")
public class UnitRepositoryIntegrationTests {
  private final EntityManager entityManager;
  private final UnitRepository unitRepository;
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

  @Autowired
  public UnitRepositoryIntegrationTests(EntityManagerFactory entityManagerFactory) {
    entityManager = entityManagerFactory.createEntityManager();
    unitRepository = new UnitRepositoryImpl(new UnitDao(entityManagerFactory));
  }
  
  @BeforeEach
  public void resetDatabase() {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.createQuery("DELETE FROM Authority").executeUpdate();
    entityManager.createQuery("DELETE FROM Unit").executeUpdate();
    entityManager.createQuery("DELETE FROM User").executeUpdate();
    transaction.commit();
    if (user.getId() != null) user.setId(null);
    if (unit_1.getId() != null) unit_1.setId(null);
    if (unit_2.getId() != null) unit_2.setId(null);
    if (unit_3.getId() != null) unit_3.setId(null);
  }

  /**
   * Checks the resetting of the database by the BeforeEach decorated method is complete.
   */
  @Test
  public void resetTest() {
    assertEquals(Collections.emptyList(), unitRepository.getAll());
  }

  /**
   * Checks the integration of the unit repository with the database via the DAO when adding a new
   * unit.
   */
  @Test
  public void addUnitTest() {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(user);
    transaction.commit();

    user.getUnits().add(unit_1);
    unitRepository.add(unit_1);

    assertEquals(List.of(unit_1), unitRepository.getAll());
  }

  /**
   * Tests that units requested thru the DAO are returned by the database.
   */
  @Test
  public void getUnitTest() {
    unitRepository.add(unit_3);
    
    assertEquals(unit_3, unitRepository.get(unit_3.getId()));
  }

  /**
   * Tests that units updated by that repository are passed on to the database.
   */
  @Test
  public void updateUnitTest() {
    unitRepository.add(unit_3);
    
    unit_3.setAddress(ADDRESS_1);
    unitRepository.update(unit_3);
    
    assertEquals(ADDRESS_1, unitRepository.get(unit_3.getId()).getAddress());
  }

  /**
   * Tests for integration between the repository layer and the database via the DAO layer when a
   * delete call is made.
   */
  @Test
  public void deleteUnitTest() {
    unitRepository.add(unit_3);
    assertNotNull(unitRepository.get(unit_3.getId()));
    
    unitRepository.delete(unit_3);
    assertEquals(Collections.emptyList(), unitRepository.getAll());
  } 
}
