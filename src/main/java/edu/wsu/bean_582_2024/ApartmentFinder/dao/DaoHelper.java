package edu.wsu.bean_582_2024.ApartmentFinder.dao;

import edu.wsu.bean_582_2024.ApartmentFinder.model.AbstractEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import org.slf4j.Logger;

public abstract class DaoHelper{
  EntityManager entityManager;
  Logger logger;
  public DaoHelper(EntityManager entityManager, Logger logger) {
    this.entityManager = entityManager;
    this.logger = logger;
  }
  <T extends AbstractEntity> List<T> castList(Class<? extends T> clazz, Collection<?> rawCollection) {
    List<T> returnValue = new ArrayList<>(rawCollection.size());
    for (Object o : rawCollection) {
      try{
        returnValue.add(clazz.cast(o));
      } catch (ClassCastException err) {
        logger.atWarn().log(String.format("Casting to a %s failed. %s", clazz, err));
      }
    }
    return returnValue;
  }

  void executeInsideTransaction(Consumer<EntityManager> action) {
    EntityTransaction tx = entityManager.getTransaction();
    try {
      tx.begin();
      action.accept(entityManager);
      tx.commit();
    } catch (RuntimeException err) {
      tx.rollback();
      throw err;
    }
  }
}
