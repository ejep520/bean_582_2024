package edu.wsu.bean_582_2024.ApartmentFinder.data;

import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
  @Query("select u " 
      + "from User u "
      + "where lower(u.username) like lower(concat('%', :searchTerm, '%'))")
  List<User> search(@Param("searchTerm") String searchTerm);
  User getUserByUsername(String username);
}
