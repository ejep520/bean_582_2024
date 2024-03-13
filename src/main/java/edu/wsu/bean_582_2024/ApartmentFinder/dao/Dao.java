package edu.wsu.bean_582_2024.ApartmentFinder.dao;

import edu.wsu.bean_582_2024.ApartmentFinder.model.AbstractEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface Dao<T extends AbstractEntity> {
  Optional<T> get(long id);
  List<T> getAll();
  @Transactional(propagation = Propagation.NEVER)
  void save(T t);
  @Transactional(propagation = Propagation.NEVER)
  void update(T t);
  @Transactional(propagation = Propagation.NEVER)
  void delete(T t);
}
