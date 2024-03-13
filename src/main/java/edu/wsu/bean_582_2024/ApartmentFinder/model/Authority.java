package edu.wsu.bean_582_2024.ApartmentFinder.model;

import java.io.Serial;
import java.io.Serializable;
import org.springframework.security.core.GrantedAuthority;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Authority is this application's implementation of the GrantedAuthority class and closely mirrors
 * the SimpleGrantedAuthority class in its abilities.
 * @author Erik Jepsen &lt;erik.jepsen@wsu.edu&gt;
 * @see GrantedAuthority
 * @see org.springframework.security.core.authority.SimpleGrantedAuthority
 */
@Entity
@Table(name = "authorities")
public class Authority extends AbstractEntity implements GrantedAuthority, Serializable {
  @Serial
  private static final long serialVersionUID = 612727736166545439L;
  @ManyToOne(targetEntity = User.class, optional = false,
      fetch = FetchType.EAGER)
  @JoinColumn(name = "userid")
  private User user;
  private String authority;

  public Authority() {}

  public Authority(User user, String authority) {
    this.user = user;
    this.authority = authority;
  }

  @Override
  public String getAuthority() {
    return authority;
  }

  public User getUser() {
    return user;
 }
  public void setUser(User user) {
    this.user = user;
  }
}
