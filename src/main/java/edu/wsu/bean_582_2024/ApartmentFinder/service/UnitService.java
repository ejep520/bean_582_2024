package edu.wsu.bean_582_2024.ApartmentFinder.service;

import edu.wsu.bean_582_2024.ApartmentFinder.dao.UnitDao;
import edu.wsu.bean_582_2024.ApartmentFinder.data.UnitRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class UnitService {
  private final UnitDao unitDao;
  private final UserService userService;
  public UnitService(UnitDao unitDao, UserService userService) {
    this.unitDao = unitDao;
    this.userService = userService;
  }
  public List<Unit> getAllUnits() {
    List<Unit> units = unitDao.getAll();
    units.sort((e, f) -> f.getFeatured().compareTo(e.getFeatured()));
    return units;
  }
  public List<Unit> findUnits(String filter) {
    return unitDao.search(filter);
  }
  public long getUnitCount() {
    return unitDao.count();
  }
  public void deleteUnit(Unit unit) {
    unitDao.delete(unit);
  }
  public void saveUnit(Unit unit) {
    Unit oldUnit;
    if (unit.getId() != null) {
      try {
        oldUnit = unitDao.getReferenceById(unit.getId());
      } catch (EntityNotFoundException err) {
        unit.getUser().getUnits().add(unit);
        userService.saveUser(unit.getUser());
        unitDao.save(unit);
        return;
      }
    } else oldUnit = null;
    if (oldUnit == null) {
      unit.getUser().getUnits().add(unit);
      userService.saveUser(unit.getUser());
      unitDao.save(unit);
      return;
    }
    if (oldUnit.getUser() == null && unit.getUser() != null) {
      unit.getUser().getUnits().add(unit);
      userService.saveUser(unit.getUser());
      unitDao.save(unit);
      return;
    }
    if (!oldUnit.getUser().equals(unit.getUser())) {
      oldUnit.getUser().getUnits().remove(oldUnit);
      unit.getUser().getUnits().add(unit);
      userService.saveUser(oldUnit.getUser());
      userService.saveUser(unit.getUser());
      unitDao.save(unit);
    }
    if (oldUnit.getUser() != null && unit.getUser() == null) {
      if (!oldUnit.getUser().getUnits().contains(oldUnit))
        oldUnit.getUser().getUnits().add(oldUnit);
      unit.setUser(oldUnit.getUser());
      userService.saveUser(unit.getUser());
      unitDao.save(unit);
    }
  }
  public List<Unit> getUsersUnits(User user) {
    if (user == null)
      return Collections.emptyList();
    return unitDao.findByUser(user);
  }

  public List<Unit> getUserUnitsByFilter(User user, String searchKey) {
    if (user == null)
      return Collections.emptyList();
    if (searchKey == null || searchKey.isEmpty() || searchKey.isBlank())
      return getUsersUnits(user);
    return unitDao.findOwnedUnitsByFilter(user, searchKey);
  }
}
