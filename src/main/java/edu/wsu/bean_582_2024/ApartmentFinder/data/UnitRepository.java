package edu.wsu.bean_582_2024.ApartmentFinder.data;

import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface UnitRepository {
  List<Unit> getAll();
  Unit get(Long id);
  void add(Unit unit);
  void update(Unit unit);
  void delete(Unit unit);
  List<Unit> search(String searchTerm);
  List<Unit> findByUser(User searchTerm);
  List<Unit> findOwnedUnitsByFilter(User user, String searchKey);
  Long count();
}
