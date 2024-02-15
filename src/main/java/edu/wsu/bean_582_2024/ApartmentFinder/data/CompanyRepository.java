package edu.wsu.bean_582_2024.ApartmentFinder.data;


import edu.wsu.bean_582_2024.ApartmentFinder.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
