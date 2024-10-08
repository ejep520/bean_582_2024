package edu.wsu.bean_582_2024.ApartmentFinder.service;

import edu.wsu.bean_582_2024.ApartmentFinder.data.UnitRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UnitService {

  private final UnitRepository unitRepository;

  public UnitService(UnitRepository unitRepository) {
    this.unitRepository = unitRepository;
  }

  public List<Unit> getAllUnits(Boolean sortByFeatured) {
    List<Unit> fetchedUnits = unitRepository.getAll();
    List<Unit> processedUnits = new ArrayList<>(fetchedUnits.size());
    processedUnits.addAll(fetchedUnits);
    if (sortByFeatured != null && sortByFeatured)
      processedUnits.sort((e, f) -> f.getFeatured().compareTo(e.getFeatured()));
    return processedUnits;
  }
 
  public List<Unit> findUnits(String filter) {
    return unitRepository.search(filter);
  }
 
  public long getUnitCount() {
    return unitRepository.count();
  }
  public void deleteUnit(Unit unit) {
    unit.setUser(null);
    unitRepository.update(unit);
    unitRepository.delete(unit);
  }
  public void saveUnit(Unit unit) {
    Long oldUnit = unit.getId();
    if (oldUnit == null) {
      unitRepository.add(unit);
    } else {
      unitRepository.update(unit);
    }
  }
  public List<Unit> getUsersUnits(User user) {
    if (user == null)
      return Collections.emptyList();
    if (Role.ADMIN.equals(user.getRole()))
      return unitRepository.getAll();
    return unitRepository.findByUser(user);
  }

  public List<Unit> getUserUnitsByFilter(User user, String searchKey) {
    if (user == null)
      return Collections.emptyList();
    if (searchKey == null || searchKey.isEmpty() || searchKey.isBlank())
      return getUsersUnits(user);
    if (Role.ADMIN.equals(user.getRole()))
      return unitRepository.search(searchKey);
    return unitRepository.findOwnedUnitsByFilter(user, searchKey);
  }
}
