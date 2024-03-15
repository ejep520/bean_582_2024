package edu.wsu.bean_582_2024.ApartmentFinder.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wsu.bean_582_2024.ApartmentFinder.model.Authority;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
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
  
  private final EntityManagerFactory entityManagerFactory;
  private EntityManager entityManager;
  private AuthorityDao authorityDao;
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
    this.entityManagerFactory = entityManagerFactory;
    this.entityManager = this.entityManagerFactory.createEntityManager();
    authority_1 = new Authority(user, AUTHORITY_STRING_1);
    authority_2 = new Authority(user, AUTHORITY_STRING_2);
    authority_3 = new Authority(user, AUTHORITY_STRING_3);
    authorityDao = new AuthorityDao(entityManagerFactory);
  }
 
  @AfterEach
  // @Modifying
  // @Transactional
  public void clearAuthorities() throws Exception{
    EntityTransaction transaction;
    user.getAuthorities().clear();
    transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.createQuery("delete from Authority").executeUpdate();
    entityManager.createQuery("delete from User").executeUpdate();
    transaction.commit();
  }

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
  @Test
  @DisplayName("Test update function")
  public void updateFunctionTest() {
    EntityTransaction transaction1, transaction2;
    transaction1 = entityManager.getTransaction();
    Authority novelAuthority = new Authority(user, "USER");
    User differentUser = new User("DifferentUser", "FOOBAR", Role.USER);
    user.getAuthorities().add(novelAuthority);
    transaction1.begin();
    try {
      entityManager.persist(user);
      entityManager.persist(differentUser);
      entityManager.persist(novelAuthority);
    } catch (Exception err) {
      transaction1.rollback();
      fail(err);
      return;
    }
    transaction1.commit();
    transaction2 = entityManager.getTransaction();
    transaction2.begin();
    novelAuthority.setUser(differentUser);
    transaction2.commit();
    transaction2.begin();
    authorityDao.update((entityManager.contains(novelAuthority))? novelAuthority : entityManager.merge(novelAuthority));
    transaction2.commit();
    transaction2.begin();
    entityManager.merge(user);
    transaction2.commit();
    transaction2.begin();
    entityManager.merge(differentUser);
    transaction2.commit();
    Authority result = entityManager.find(Authority.class, novelAuthority.getId()); 
    
    assertEquals(differentUser, result.getUser());
  }
}
