package edu.wsu.bean_582_2024.ApartmentFinder.data;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.wsu.bean_582_2024.ApartmentFinder.dao.UserDao;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
public class UserRepositoryTests {
  @Mock
  private UserDao userDao;
  private UserRepository userRepository;
  private final static String USERNAME_1 = "testAdmin";
  private final static String USERNAME_2 = "testOwner";
  private final static String USERNAME_3 = "testUser";
  private final static String PASSWORD_1 = "foo";
  private final static String PASSWORD_2 = "bar";
  private final static String PASSWORD_3 = "foobar";
  private final static Role ROLE_1 = Role.ADMIN;
  private final static Role ROLE_2 = Role.OWNER;
  private final static Role ROLE_3 = Role.USER;
  private final static User user_1 = new User(USERNAME_1, PASSWORD_1, ROLE_1);
  private final static User user_2 = new User(USERNAME_2, PASSWORD_2, ROLE_2);
  private final static User user_3 = new User(USERNAME_3, PASSWORD_3, ROLE_3);
  private final static List<User> userList = List.of(user_1, user_2, user_3);
  
  public UserRepositoryTests(){
    user_1.setId(1L);
    user_2.setId(2L);
    user_3.setId(3L);
  }
  
  @BeforeEach
  public void createRepository() {
    userRepository = new UserRepositoryImpl(userDao);
  }
  
  @Test
  @DisplayName("GetAll function passes call to DAO and returns result")
  public void testGetAllFunctionPassesCall() {
    when(userDao.getAll()).thenReturn(userList);
    List<User> result = userRepository.getAll();
    assertEquals(userList, result);
    verify(userDao).getAll();
  }
  
  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  @ParameterizedTest
  @MethodSource("idUserStream")
  public void testGetById(long id, Optional<User> optionalUser) {
    when(userDao.get(id)).thenReturn(optionalUser);
    Optional<User> result = userRepository.getUserById(id);
    if (result.isPresent())
      assertEquals(user_1, result.get());
    else
      assertEquals(4L, id);
  }
  
  private static Stream<Arguments> idUserStream() {
    return Stream.of(Arguments.of(1L, Optional.of(user_1)),
        Arguments.of(4L, Optional.empty()));
  }
  
  
  @Test
  public void addFunctionTest() {
    userRepository.add(user_1);
    verify(userDao).save(user_1);
  }

  @Test
  public void updateFunctionTest() {
    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    userRepository.update(user_1);
    verify(userDao).update(captor.capture());
    assertEquals(user_1, captor.getValue());
  }
  
  @Test
  public void deleteFunctionTest() {
    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    userRepository.delete(user_1);
    verify(userDao).delete(captor.capture());
    assertEquals(user_1, captor.getValue());
  }
  
  @ParameterizedTest
  @ValueSource(strings = {USERNAME_1, USERNAME_2, USERNAME_3})
  public void getUserByUsernameTest(String username) {
    when(userDao.findUser(username))
        .thenReturn(Stream
            .of(user_1, user_2, user_3)
            .filter(e -> username.equals(e.getUsername()))
            .findFirst().orElse(null));
    User result = userRepository.getUserByUsername(username);
    assertEquals(username, result.getUsername());
  }
  
  @Test
  public void countFunctionTest() {
    when(userDao.count()).thenReturn(3L);
    Long result = userRepository.count();
    verify(userDao).count();
    assertEquals(3L, result);
  }
}
