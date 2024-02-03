package edu.wsu.bean_582_2024.ApartmentFinder.repository;

import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * This interface provides basic functionality to JDBC for
 * manipulation of the User class
 * @author Erik Jepsen &lt;erik.jepsen@wsu.edu&gt;
 */
public interface UserRepository extends CrudRepository<User, String> { }
