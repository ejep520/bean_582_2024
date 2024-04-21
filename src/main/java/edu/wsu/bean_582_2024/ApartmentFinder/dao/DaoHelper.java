package edu.wsu.bean_582_2024.ApartmentFinder.dao;

import edu.wsu.bean_582_2024.ApartmentFinder.model.AbstractEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;

public abstract class DaoHelper{
  EntityManager entityManager;
  EntityManagerFactory entityManagerFactory;
  Logger logger;
  public DaoHelper(EntityManagerFactory entityManagerFactory, Logger logger) {
    this.entityManagerFactory = entityManagerFactory;
    this.entityManager = this.entityManagerFactory.createEntityManager();
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
}
