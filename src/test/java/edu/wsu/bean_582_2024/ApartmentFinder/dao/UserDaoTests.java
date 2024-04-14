package edu.wsu.bean_582_2024.ApartmentFinder.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wsu.bean_582_2024.ApartmentFinder.TestCase;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
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
public class UserDaoTests {
  //S20
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
  
  @BeforeEach
  public void clearTable() {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.createQuery("delete from Unit").executeUpdate();
    entityManager.createQuery("delete from Authority").executeUpdate();
    entityManager.createQuery("delete from User").executeUpdate();
    transaction.commit();
    if (!user_1.getUsername().equals(USERNAME_1))
      user_1.setUsername(USERNAME_1);
  }

  @TestCase("C201")
  @Test
  public void getUserByIdTest() {
    try {
      addAllUsers();
    } catch (Exception err) {
      fail(err);
      return;
    }

    Optional<User> result = userDao.get(user_1.getId());

    if (result.isPresent())
      assertEquals(user_1, result.get());
    else
      fail("User could not be found.");
  }

  @TestCase("C202")
  @Test
  public void findUserByUsernameSuccessReturnsUser() {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(user_1);
    transaction.commit();
    entityManager.refresh(user_1);
    
    User result = userDao.findUser(USERNAME_1);
    
    assertEquals(user_1, result);
  }

  @TestCase("C203")
  @Test
  public void findUserByUsernameFailureReturnsNull() {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(user_1);
    transaction.commit();
    entityManager.refresh(user_1);

    User result = userDao.findUser(USERNAME_3);
    
    assertNull(result);
  }

  @TestCase("C204")
  @Test
  public void testGetAllFunction() {
    try {
      addAllUsers();
    } catch (Exception err) {
      fail(err);
      return;
    }
    
    List<User> result = userDao.getAll();
    
    assertEquals(userList, result);
  }

  @TestCase("C205")
  @Test
  public void testSaveUserFunction() {
    userDao.save(user_1);
    List<User> result = userDao.getAll();

    assertEquals(List.of(user_1), result);
  }

  @TestCase("C206")
  @Test
  public void testUpdateFunction() {
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(user_1);
    transaction.commit();
    
    user_1.setUsername(USERNAME_2);
    userDao.update(user_1);
    
    User result = entityManager.find(User.class, user_1.getId());
    if (result == null)
      fail("User was not found.");
    else 
      assertEquals(user_1.getUsername(), result.getUsername());
    
    entityManager.refresh(user_1);
  }

  @TestCase("C207")
  @Test
  public void deleteUserFunctionTest() {
    try {
      addAllUsers();
    } catch (Exception err) {
      fail(err);
      return;
    }
    
    userDao.delete(user_2);
    
    List<User> result = userDao.castList(User.class, entityManager.createQuery("select e from User e").getResultList());
    assertThrows(EntityNotFoundException.class, () -> entityManager.refresh(user_2));
    assertEquals(List.of(user_1, user_3), result);
  }

  @TestCase("C208")
  @Test
  public void countFunctionTest() {
    assertEquals(0L, userDao.count());
    
    try {
      addAllUsers();
    } catch (Exception err) {
      fail(err);
    }
    
    assertEquals(3L, userDao.count());
  }
  
  private void addAllUsers() throws Exception{
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    try {
      entityManager.persist(user_1);
      entityManager.persist(user_2);
      entityManager.persist(user_3);
    } catch (Exception err) {
      transaction.rollback();
      throw err;
    }
    transaction.commit();
  }
}
