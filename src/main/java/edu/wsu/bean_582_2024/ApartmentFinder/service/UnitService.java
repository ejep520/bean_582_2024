package edu.wsu.bean_582_2024.ApartmentFinder.service;

import edu.wsu.bean_582_2024.ApartmentFinder.data.UnitRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UnitService {
  private final UnitRepository unitRepository;
  private final UserService userService;
  public UnitService(UnitRepository unitRepository, UserService userService) {
    this.unitRepository = unitRepository;
    this.userService = userService;
  }
  public List<Unit> getAllUnits(Boolean sortByFeatured) {
    List<Unit> units = unitRepository.getAll();
    if (sortByFeatured != null && sortByFeatured)
      units.sort((e, f) -> f.getFeatured().compareTo(e.getFeatured()));
    return units;
  }
  public List<Unit> findUnits(String filter) {
    return unitRepository.search(filter);
  }
  public long getUnitCount() {
    return unitRepository.count();
  }
  public void deleteUnit(Unit unit) {
    User user = unit.getUser();
    user.getUnits().remove(unit);
    userService.saveUser(user);
    unitRepository.delete(unit);
  }
  public void saveUnit(Unit unit) {
    Unit oldUnit;
    if (unit.getId() != null)
      oldUnit = unitRepository.get(unit.getId());
    else
      oldUnit = null;
    if (oldUnit == null) {
      unit.getUser().getUnits().add(unit);
      userService.saveUser(unit.getUser());
      unitRepository.add(unit);
      return;
    }
    if (oldUnit.getUser() == null && unit.getUser() != null) {
      unit.getUser().getUnits().add(unit);
      userService.saveUser(unit.getUser());
      unitRepository.update(unit);
      return;
    }
    if (!oldUnit.getUser().equals(unit.getUser())) {
      oldUnit.getUser().getUnits().remove(oldUnit);
      unit.getUser().getUnits().add(unit);
      userService.saveUser(oldUnit.getUser());
      userService.saveUser(unit.getUser());
      unitRepository.update(unit);
    }
    if (oldUnit.getUser() != null && unit.getUser() == null) {
      if (!oldUnit.getUser().getUnits().contains(oldUnit))
        oldUnit.getUser().getUnits().add(oldUnit);
      unit.setUser(oldUnit.getUser());
      userService.saveUser(unit.getUser());
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
