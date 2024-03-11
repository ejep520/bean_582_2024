package edu.wsu.bean_582_2024.ApartmentFinder.service;

import edu.wsu.bean_582_2024.ApartmentFinder.data.UnitRepository;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UnitService {
  private final UnitRepository unitRepository;
  public UnitService(UnitRepository unitRepository) {
    this.unitRepository = unitRepository;
  }
  public List<Unit> getAllUnits() {
    return unitRepository.findAll(Sort.by(Sort.Direction.DESC,"featured"));
  }
  public List<Unit> findUnits(String filter) {
    return unitRepository.search(filter);
  }
  public long getUnitCount() {
    return unitRepository.count();
  }
  public void deleteUnit(Unit unit) {
    unitRepository.delete(unit);
  }
  public void saveUnit(Unit unit) {
    unitRepository.save(unit);
  }
  
}
