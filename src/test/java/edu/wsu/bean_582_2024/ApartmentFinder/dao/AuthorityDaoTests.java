package edu.wsu.bean_582_2024.ApartmentFinder.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.wsu.bean_582_2024.ApartmentFinder.model.Authority;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace= Replace.NONE)
public class AuthorityDaoTests {
  
  private final TestEntityManager testEntityManager;
  private final EntityManagerFactory entityManagerFactory;
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
    testEntityManager = new TestEntityManager(entityManagerFactory);
    authority_1 = new Authority(user, AUTHORITY_STRING_1);
    authority_2 = new Authority(user, AUTHORITY_STRING_2);
    authority_3 = new Authority(user, AUTHORITY_STRING_3);
    user.getAuthorities().addAll(List.of(authority_1, authority_2, authority_3));
    authorityDao = new AuthorityDao(entityManagerFactory, testEntityManager.getEntityManager());
  }
  
  @Test
  public void findAllAuthoritiesTest() {
    testEntityManager.persist(user);
    testEntityManager.persist(authority_1);
    testEntityManager.persist(authority_2);
    testEntityManager.persist(authority_3);

    List<Authority> result = authorityDao.getAll();
    
    assertEquals(List.of(authority_1, authority_2, authority_3), result);
  }
}
