package edu.wsu.bean_582_2024.ApartmentFinder.dao;

import edu.wsu.bean_582_2024.ApartmentFinder.model.Authority;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Optional;
import org.slf4j.LoggerFactory;

public class AuthorityDao extends DaoHelper implements Dao<Authority> {

  public AuthorityDao(EntityManager entityManager) {
    super(entityManager, LoggerFactory.getLogger(AuthorityDao.class));
  }  
  
  @Override
  public Optional<Authority> get(long id) {
    return Optional.ofNullable(entityManager.find(Authority.class, id));
  }

  @Override
  public List<Authority> getAll() {
    Query query = entityManager.createQuery("select e from Authority e");
    return castList(Authority.class, query.getResultList());
  }

  @Override
  public void save(Authority authority) {
    executeInsideTransaction(entityManager -> entityManager.persist(authority));
  }

  /**
   * This is a do-nothing function here to sate the interface. Authorities are immutable.
   */
  @Override
  public void update(Authority authority, Object... params) {
  }

  @Override
  public void delete(Authority authority) {
    executeInsideTransaction(entityManager -> entityManager.remove(authority));
  }
}
