package edu.wsu.bean_582_2024.ApartmentFinder.dao;

import edu.wsu.bean_582_2024.ApartmentFinder.model.AbstractEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.SessionImplementor;
import org.slf4j.Logger;

public abstract class DaoHelper{
  EntityManager entityManager;
  EntityManagerFactory entityManagerFactory;
  Logger logger;
  public DaoHelper(EntityManagerFactory entityManagerFactory, Logger logger) {
    this(entityManagerFactory, logger, entityManagerFactory.createEntityManager());
  }
  
  public DaoHelper(EntityManagerFactory entityManagerFactory, Logger logger,
      EntityManager entityManager) {
    this.entityManagerFactory = entityManagerFactory;
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
    SessionImplementor sessionImplementor = (SessionImplementor) entityManager.getDelegate();
    Transaction transaction = sessionImplementor.getTransaction();
    try {
      transaction.begin();
      action.accept(entityManager);
      transaction.commit();
    } catch (RuntimeException err) {
      transaction.rollback();
      throw err;
    }
  }
}
