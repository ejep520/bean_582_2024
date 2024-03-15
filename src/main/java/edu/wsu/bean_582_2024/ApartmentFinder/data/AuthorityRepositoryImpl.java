package edu.wsu.bean_582_2024.ApartmentFinder.data;

import edu.wsu.bean_582_2024.ApartmentFinder.dao.AuthorityDao;
import edu.wsu.bean_582_2024.ApartmentFinder.model.Authority;
import org.springframework.stereotype.Component;

@Component("AuthImpl")
public class AuthorityRepositoryImpl implements AuthorityRepository {

  private final AuthorityDao authorityDao;
  
  public AuthorityRepositoryImpl(AuthorityDao authorityDao) {
    this.authorityDao = authorityDao;
  }


  @Override
  public Authority get(Long id) {
    return authorityDao.get(id).orElse(null);
  }

  @Override
  public void add(Authority authority) {
    authorityDao.save(authority);
  }

  @Override
  public void update(Authority authority) {
    authorityDao.update(authority);
  }

  @Override
  public void delete(Authority authority) {
    authorityDao.delete(authority);
  }
}
