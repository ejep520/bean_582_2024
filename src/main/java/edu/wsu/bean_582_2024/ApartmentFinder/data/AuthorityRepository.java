package edu.wsu.bean_582_2024.ApartmentFinder.data;

import edu.wsu.bean_582_2024.ApartmentFinder.model.Authority;
import org.springframework.stereotype.Component;

@Component
public interface AuthorityRepository {
  Authority get(Long id);
  void add(Authority authority);
  void update(Authority authority);
  void remove(Authority authority);
}
