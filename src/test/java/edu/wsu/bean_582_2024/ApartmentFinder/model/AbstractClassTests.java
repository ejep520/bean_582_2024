package edu.wsu.bean_582_2024.ApartmentFinder.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.InvocationTargetException;

import edu.wsu.bean_582_2024.ApartmentFinder.TestCase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AbstractClassTests {
  // S11

  @TestCase("C111")
  @ValueSource(classes={User.class, Authority.class, Unit.class})
  @ParameterizedTest()
  public <T extends AbstractEntity> void newInstancesHaveNullId(Class<T> clazz) {
    T instance = createInstance(clazz);
    if (instance == null) return;
    assertNull(instance.getId());
  }

  @TestCase("C112")
  @ValueSource(classes={User.class, Authority.class, Unit.class})
  @ParameterizedTest
  public <T extends AbstractEntity> void setIdsStick(Class<T> clazz) {
    T instance = createInstance(clazz);
    if (instance == null) return;
    instance.setId(1L);
    Long result = instance.getId();
    assertNotNull(result);
    assertEquals(1L, result);
  }

  @TestCase("C113")
  @ValueSource(classes={User.class, Authority.class, Unit.class})
  @ParameterizedTest
  public <T extends AbstractEntity> void versionIsAnInt(Class<T> clazz) {
    T instance = createInstance(clazz);
    if (instance == null) return;
    assertTrue(Integer.class.isAssignableFrom(instance.getVersion().getClass()));
  }
  
  private <T extends AbstractEntity> T createInstance(Class<T> clazz) {
    T instance;
    Generic<T> generic = new Generic<>(clazz);
    try {
      instance = generic.buildOneDefault();
    } catch (Exception err) {
      fail(err);
      return null;
    }
    return instance;
  }
  
  private static class Generic<T extends AbstractEntity> {
    private final Class<T> clazz;
    public Generic(Class<T> clazz) {
      this.clazz = clazz;
    }
    public T buildOneDefault() throws NoSuchMethodException, InstantiationException,
        InvocationTargetException, IllegalAccessException {
      return clazz.getDeclaredConstructor().newInstance();
    }
  }
}
