package edu.wsu.bean_582_2024.ApartmentFinder.data;

import edu.wsu.bean_582_2024.ApartmentFinder.model.Unit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UnitRepository extends JpaRepository<Unit, Long> {
  @Query("select u " 
      + "from unit u " 
      + "where lower(u.address) like lower(concat('%', :searchTerm, '%'))") 
//      + "OR lower(u.livingRoom) like lower(concat('%', :searchTerm, '%'));") 
//      + "OR lower(u.kitchen) like lower(concat('%' :searchTerm, '%'))") 
//      + "ORDER BY featured DESC;")
  List<Unit> search(@Param("searchTerm") String searchTerm);
}
