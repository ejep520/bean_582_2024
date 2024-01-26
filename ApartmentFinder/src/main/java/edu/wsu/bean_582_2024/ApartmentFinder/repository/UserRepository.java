package edu.wsu.bean_582_2024.ApartmentFinder.repository;

import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> { }
