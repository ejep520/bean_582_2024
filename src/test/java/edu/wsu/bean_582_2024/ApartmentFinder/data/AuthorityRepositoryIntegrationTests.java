package edu.wsu.bean_582_2024.ApartmentFinder.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wsu.bean_582_2024.ApartmentFinder.dao.AuthorityDao;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Authority;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Tag("slow")
public class AuthorityRepositoryIntegrationTests {
  private final EntityManagerFactory entityManagerFactory;
  private final AuthorityRepository authorityRepository;
  private final AuthorityDao authorityDao;
  private final EntityManager entityManager;
  private static final String USERNAME = "TestAdmin";
  private static final String PASSWORD = "foo";
  private static final Role ROLE = Role.ADMIN;
  private final User user = new User(USERNAME, PASSWORD, ROLE);
  private final static String AUTHORITY_STRING_1 = "USER";
  private final static String AUTHORITY_STRING_2 = "OWNER";
  private final static String AUTHORITY_STRING_3 = "ADMIN";
  private final Authority authority_1, authority_2, authority_3;
  private final List<Authority> authorities;
  
  @Autowired
  public AuthorityRepositoryIntegrationTests(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
    entityManager = this.entityManagerFactory.createEntityManager();
    authorityDao = new AuthorityDao(entityManagerFactory);
    authorityRepository = new AuthorityRepositoryImpl(authorityDao);
    authority_1 = new Authority(user, AUTHORITY_STRING_1);
    authority_2 = new Authority(user, AUTHORITY_STRING_2);
    authority_3 = new Authority(user, AUTHORITY_STRING_3);
    authorities = List.of(authority_1, authority_2, authority_3);
  }
  
  @BeforeEach
  public void ResetDatabase() {
    EntityTransaction transaction;
    user.getAuthorities().clear();
    transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.createQuery("delete from Unit").executeUpdate();
    entityManager.createQuery("delete from Authority").executeUpdate();
    entityManager.createQuery("delete from User").executeUpdate();
    transaction.commit();
    if (authority_1.getId() != null) authority_1.setId(null);
    if (authority_2.getId() != null) authority_2.setId(null);
    if (authority_3.getId() != null) authority_3.setId(null);
    user.getAuthorities().clear();
  }

  /**
   * Disabled. Checks that the database has no entries after the @BeforeEach decorated method.
   * This was used in the creation of the test class to ensure the things under the hood were
   * working as required. They are (as of 2024-04-05). This may be re-enabled as needed, otherwise
   * it doesn't test anything applicable to this class.
   */
  @Test
  @Disabled("Scaffolding test. Checks that elements of the class under the hood are behaving.")
  public void emptyDatabaseTest() {
    
    List<Authority> authorities =  authorityDao.getAll();
    assertEquals(0, authorities.size());
  }
  
  @Test
  public void addAuthoritiesTest() {
    EntityTransaction transaction;

    assertNull(authority_1.getId());

    transaction = entityManager.getTransaction();
    transaction.begin();
    try {
      entityManager.persist(user);
    } catch (Exception err) {
      transaction.rollback();
      fail(err);
      return;
    }
    transaction.commit();

    if (!user.getAuthorities().add(authority_1)) fail("Unable to add authority to user");
    authorityRepository.add(authority_1);

    List<Authority> authorityList = new ArrayList<>(user.getAuthorities().size());
    for (Authority thisAuth : user.getAuthorities()) {
      authorityList.add(Objects.requireNonNull(authorityRepository.get(thisAuth.getId())));
    }
    
    assertEquals(1, authorityList.size());
  }

  @Test
  public void readAuthoritiesTest() {
    if (!user.getAuthorities().addAll(authorities)) fail("Unable to add authorities to the user.");
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    try {
      entityManager.persist(user);
    } catch (Exception err) {
      transaction.rollback();
      fail(err);
      return;
    }
    transaction.commit();
    
    for (Authority authority : authorities) {
      assertEquals(authority, authorityRepository.get(authority.getId()));
    }
  }
  
  @Test
  public void updateAuthorityTest() {
    EntityTransaction transaction = entityManager.getTransaction();
    user.getAuthorities().add(authority_1);
    
    transaction.begin();
    try {
      entityManager.persist(user);
    } catch (Exception err) {
      transaction.rollback();
      fail(err);
      return;
    }
    
    authority_1.setUser(new User());
    
    assertThrows(UnsupportedOperationException.class, () -> authorityRepository.update(authority_1));
  }

  @Test
  public void deleteAuthorityTest() {
    if (!user.getAuthorities().addAll(authorities)) fail("Unable to add authorities to user.");
    EntityTransaction transaction = entityManager.getTransaction();
    
    transaction.begin();
    entityManager.persist(user);
    transaction.commit();
    
    user.getAuthorities().remove(authority_1);
    authority_1.setUser(null);
    transaction.begin();
    entityManager.merge(user);
    transaction.commit();
    
    authorityRepository.delete(authority_1);
    
    assertNull(authorityRepository.get(authority_1.getId()));
  }
}
