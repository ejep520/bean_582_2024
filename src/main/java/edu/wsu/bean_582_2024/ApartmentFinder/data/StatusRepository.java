package edu.wsu.bean_582_2024.ApartmentFinder.data;


import edu.wsu.bean_582_2024.ApartmentFinder.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {

}
