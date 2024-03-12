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
    User oldUser, newUser;
    newUser = unit.getUser();
    if (unit.getId() != null)
      oldUnit = unitRepository.get(unit.getId());
    else
      oldUnit = null;
    if (oldUnit != null) {
      oldUser = oldUnit.getUser() == null ? null : oldUnit.getUser();
    } else {
      newUser.getUnits().add(unit);
      // userService.saveUser(newUser);
      unitRepository.add(unit);
      return;
    }
    if (oldUser == null) {
      newUser.getUnits().add(unit);
      // userService.saveUser(newUser);
      unitRepository.update(unit);
      return;
    }
    if (oldUser.equals(newUser)) {
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
