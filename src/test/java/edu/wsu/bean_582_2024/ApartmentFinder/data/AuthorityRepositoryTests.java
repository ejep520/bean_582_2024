package edu.wsu.bean_582_2024.ApartmentFinder.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import edu.wsu.bean_582_2024.ApartmentFinder.TestCase;
import edu.wsu.bean_582_2024.ApartmentFinder.dao.AuthorityDao;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Authority;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthorityRepositoryTests {
  // S15
  @Mock
  private AuthorityDao authorityDao;
  private AuthorityRepository authorityRepository;
  private final static String AUTHORITY_ADMIN = "ADMIN";
  private final static String AUTHORITY_OWNER = "OWNER";
  private final static String AUTHORITY_USER = "USER";
  private final static String USERNAME_1 = "testAdmin";
  private final static String USERNAME_2 = "testOwner";
  private final static String USERNAME_3 = "testUser";
  private final static String PASSWORD_1 = "foo";
  private final static String PASSWORD_2 = "bar";
  private final static String PASSWORD_3 = "foobar";
  private final static Role ROLE_1 = Role.ADMIN;
  private final static Role ROLE_2 = Role.OWNER;
  private final static Role ROLE_3 = Role.USER;
  private final static User USER_1 = new User(USERNAME_1, PASSWORD_1, ROLE_1);
  private final static User USER_2 = new User(USERNAME_2, PASSWORD_2, ROLE_2);
  private final static User USER_3 = new User(USERNAME_3, PASSWORD_3, ROLE_3);
  private final static Authority AUTHORITY_1_1 = new Authority(USER_1, AUTHORITY_ADMIN);
  private final static Authority AUTHORITY_1_2 = new Authority(USER_1, AUTHORITY_OWNER);
  private final static Authority AUTHORITY_1_3 = new Authority(USER_1, AUTHORITY_USER);
  private final static Authority AUTHORITY_2_1 = new Authority(USER_2, AUTHORITY_OWNER);
  private final static Authority AUTHORITY_2_2 = new Authority(USER_2, AUTHORITY_USER);
  private final static Authority AUTHORITY_3 = new Authority(USER_3, AUTHORITY_USER);
  private final static List<Authority> authorityList = List.of(AUTHORITY_1_1, AUTHORITY_1_2,
      AUTHORITY_1_3, AUTHORITY_2_1, AUTHORITY_2_2, AUTHORITY_3);

  static {
    USER_1.setId(1L);
    USER_2.setId(2L);
    USER_3.setId(3L);
    AUTHORITY_1_1.setId(11L);
    AUTHORITY_1_2.setId(12L);
    AUTHORITY_1_3.setId(13L);
    AUTHORITY_2_1.setId(21L);
    AUTHORITY_2_2.setId(22L);
    AUTHORITY_3.setId(30L);
    USER_1.getAuthorities().add(AUTHORITY_1_1);
    USER_1.getAuthorities().add(AUTHORITY_1_2);
    USER_1.getAuthorities().add(AUTHORITY_1_3);
    USER_2.getAuthorities().add(AUTHORITY_2_1);
    USER_2.getAuthorities().add(AUTHORITY_2_2);
    USER_3.getAuthorities().add(AUTHORITY_3);
  }
  
  @BeforeEach
  public void resetRepository() {
    authorityRepository = new AuthorityRepositoryImpl(authorityDao);
  }

  @TestCase("C151")
  @ParameterizedTest
  @ValueSource(longs = {11L, 12L, 13L, 21L, 22L, 30L, 42L})
  public void getAllFunctionTest(long searchKey) {
    when(authorityDao.get(searchKey))
        .thenReturn(authorityList.stream().filter(
            e -> e.getId().equals(searchKey)).findFirst());
    
    Authority result = authorityRepository.get(searchKey);
    
    if (result == null)
      assertEquals(42L, searchKey);
    else
      assertEquals(searchKey, result.getId());
  }

  @TestCase("C152")
  @Test
  public void addFunctionTest() {
    ArgumentCaptor<Authority> captor = ArgumentCaptor.forClass(Authority.class);
    authorityRepository.add(AUTHORITY_3);
    
    verify(authorityDao).save(captor.capture());
    assertEquals(AUTHORITY_3, captor.getValue());
  }

  @TestCase("C153")
  @Test
  public void updateFunctionTest() {
    ArgumentCaptor<Authority> captor = ArgumentCaptor.forClass(Authority.class);
    doThrow(UnsupportedOperationException.class).when(authorityDao).update(any(Authority.class));
    
    assertThrows(UnsupportedOperationException.class, () -> authorityRepository.update(AUTHORITY_3));
    verify(authorityDao).update(captor.capture());
    assertEquals(AUTHORITY_3, captor.getValue());
  }

  @TestCase("C154")
  @Test
  public void deleteFunctionTest() {
    ArgumentCaptor<Authority> captor = ArgumentCaptor.forClass(Authority.class);
    
    authorityRepository.delete(AUTHORITY_3);
    
    verify(authorityDao).delete(captor.capture());
    assertEquals(AUTHORITY_3, captor.getValue());
  }
}
