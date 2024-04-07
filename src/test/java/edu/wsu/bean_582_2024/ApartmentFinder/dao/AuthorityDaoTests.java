package edu.wsu.bean_582_2024.ApartmentFinder.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wsu.bean_582_2024.ApartmentFinder.TestCase;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Authority;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase
@Tag("slow")
public class AuthorityDaoTests {
  // S18
  private final EntityManager entityManager;
  private final AuthorityDao authorityDao;
  private static final String USERNAME = "TestAdmin";
  private static final String PASSWORD = "foo";
  private static final Role ROLE = Role.ADMIN;
  private final User user = new User(USERNAME, PASSWORD, ROLE);
  private final static String AUTHORITY_STRING_1 = "USER";
  private final static String AUTHORITY_STRING_2 = "OWNER";
  private final static String AUTHORITY_STRING_3 = "ADMIN";
  private final Authority authority_1, authority_2, authority_3;
  
  @Autowired
  public AuthorityDaoTests(EntityManagerFactory entityManagerFactory) {
    this.entityManager = entityManagerFactory.createEntityManager();
    authority_1 = new Authority(user, AUTHORITY_STRING_1);
    authority_2 = new Authority(user, AUTHORITY_STRING_2);
    authority_3 = new Authority(user, AUTHORITY_STRING_3);
    authorityDao = new AuthorityDao(entityManagerFactory);
  }
 
  @BeforeEach
  public void clearAuthorities() {
    EntityTransaction transaction;
    user.getAuthorities().clear();
    transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.createQuery("delete from Unit").executeUpdate();
    entityManager.createQuery("delete from Authority").executeUpdate();
    entityManager.createQuery("delete from User").executeUpdate();
    transaction.commit();
  }

  // C181
  @TestCase("C181")
  @Test
  public void findAllAuthoritiesTest() {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    user.getAuthorities().addAll(List.of(authority_1, authority_2, authority_3));
    try {
      entityManager.persist(user);
    } catch (Exception err) {
      transaction.rollback();
      fail(err);
      return;
    }
    transaction.commit();
    List<Authority> result = authorityDao.getAll();
    
    assertEquals(List.of(authority_1, authority_2, authority_3), result);
  }

  // C182
  @TestCase("C182")
  @Test
  public void testGetById() {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    user.getAuthorities().addAll(List.of(authority_1, authority_2, authority_3));
    try {
      entityManager.persist(user);
      entityManager.persist(authority_1);
      entityManager.persist(authority_2);
      entityManager.persist(authority_3);
    } catch (Exception err) {
      transaction.rollback();
      fail(err);
      return;
    }
    transaction.commit();
    Optional<Authority> result = authorityDao.get(authority_1.getId());
    if (result.isPresent()) {
      assertEquals(authority_1, result.get());
    } else {
      fail("Authority was not retrieved.");
    }
  }

  // C183
  @TestCase("C183")
  @Test
  @DisplayName("Test save function")
  public void testSaveFunction() {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    
    try {
      entityManager.persist(user);
    } catch (Exception err) {
      fail(err);
      transaction.rollback();
      return;
    }
    transaction.commit();
    
    if (!user.getAuthorities().add(authority_1)) fail("Unable to add authority to User.");
    authorityDao.save(authority_1); 
    
    assertEquals(authority_1, authorityDao.get(authority_1.getId()).orElse(null));
  }

  /**
   * In practical terms, this should never happen, but for purposes of "painting it green"...
   */
  // C184
  @TestCase("C184")
  @Test
  @DisplayName("Updating authorities is NOT supported.")
  public void updateFunctionTest() {
    assertThrows(UnsupportedOperationException.class, () -> authorityDao.update(authority_1));
  }

  // C185
  @TestCase("C185")
  @Test
  @DisplayName("Test Delete function")
  public void testDeleteFunction() {
    user.getAuthorities().add(authority_1);
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(user);
    transaction.commit();
    transaction.begin();
    entityManager.persist(authority_1);
    transaction.commit();
    user.getAuthorities().clear();
    authority_1.setUser(null);
    transaction.begin();
    entityManager.merge(user);
    transaction.commit();
    authorityDao.delete(authority_1);
    transaction.begin();
    List<Authority> result = authorityDao.getAll();
    transaction.commit();
    assertTrue(result.isEmpty());
  }
}
