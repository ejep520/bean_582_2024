package edu.wsu.bean_582_2024.ApartmentFinder.data;

import edu.wsu.bean_582_2024.ApartmentFinder.dao.UnitDao;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UnitRepositoryImpl implements UnitRepository {

  private final UnitDao unitDao;
  
  public UnitRepositoryImpl(UnitDao unitDao) {
    this.unitDao = unitDao;
  }
  
  @Override
  public List<Unit> getAll() {
    return unitDao.getAll();
  }
  
  @Override
  public Unit get(Long id) {
    return unitDao.get(id).orElse(null);
  }

  @Override
  public void add(Unit unit) {
    unitDao.save(unit);
  }

  @Override
  public void update(Unit unit) {
    unitDao.update(unit);
  }

  @Override
  public void delete(Unit unit) {
    unitDao.delete(unit);
  }

  @Override
  public List<Unit> search(String searchKey) {
    return unitDao.find(searchKey);
  }

  @Override
  public List<Unit> findByUser(User searchKey) {
    return unitDao.findByUser(searchKey);
  }

  @Override
  public List<Unit> findOwnedUnitsByFilter(User user, String searchKey) {
    return unitDao.findOwnedUnitsByFilter(user, searchKey);
  }
  
  @Override
  public Long count() {
    return unitDao.count();
  }
}
