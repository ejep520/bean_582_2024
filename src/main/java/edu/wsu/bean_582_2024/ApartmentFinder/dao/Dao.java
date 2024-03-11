package edu.wsu.bean_582_2024.ApartmentFinder.dao;

import edu.wsu.bean_582_2024.ApartmentFinder.model.AbstractEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface Dao<T extends AbstractEntity> {
  Optional<T> get(long id);
  List<T> getAll();
  void save(T t);
  void update(T t, Object... params);
  void delete(T t);
}
