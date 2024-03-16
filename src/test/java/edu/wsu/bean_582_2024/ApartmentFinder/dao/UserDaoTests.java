package edu.wsu.bean_582_2024.ApartmentFinder.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase
@Tag("slow")
public class UserDaoTests {
  private final EntityManager entityManager;
  private final static String USERNAME_1 = "testAdmin";
  private final static String USERNAME_2 = "testOwner";
  private final static String USERNAME_3 = "testUser";
  private final static String PASSWORD_1 = "foo";
  private final static String PASSWORD_2 = "bar";
  private final static String PASSWORD_3 = "foobar";
  private final static Role ROLE_1 = Role.ADMIN;
  private final static Role ROLE_2 = Role.OWNER;
  private final static Role ROLE_3 = Role.USER;
  private final User user_1 = new User(USERNAME_1, PASSWORD_1, ROLE_1);
  private final User user_2 = new User(USERNAME_2, PASSWORD_2, ROLE_2);
  private final User user_3 = new User(USERNAME_3, PASSWORD_3, ROLE_3);
  private final List<User> userList = List.of(user_1, user_2, user_3);
  private final UserDao userDao;
  
  @Autowired
  public UserDaoTests(EntityManagerFactory entityManagerFactory) {
    entityManager = entityManagerFactory.createEntityManager();
    userDao = new UserDao(entityManagerFactory);
  }
  
  @AfterEach
  public void clearTable() {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.createQuery("delete from User").executeUpdate();
    transaction.commit();
  }
  
  @Test
  public void testSaveUserFunction() {
    userDao.save(user_1);
    List<User> result = userDao.getAll();
    
    assertEquals(List.of(user_1), result);
  }
  
  @Test
  public void testGetAllFunction() {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    try {
      entityManager.persist(user_1);
      entityManager.persist(user_2);
      entityManager.persist(user_3);
    } catch (Exception err) {
      transaction.rollback();
      fail(err);
      return;
    }
    transaction.commit();
    
    List<User> result = userDao.getAll();
    
    assertEquals(userList, result);
  }
}
