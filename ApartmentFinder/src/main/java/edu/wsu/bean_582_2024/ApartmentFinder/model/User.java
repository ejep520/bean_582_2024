package edu.wsu.bean_582_2024.ApartmentFinder.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import org.springframework.util.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

@Table(name="APT_USERS")
@Entity
public class User extends AbstractEntity {
  @NotNull
  @NotEmpty
  private String username;
  @NotNull
  @NotEmpty
  private String passwordHash;
  private String passwordSalt;
  @NotNull
  private Boolean enabled;
  private Role role;
  
  public User() {
    
  }

  public User(String username, String password, Role role) {
    this.username = username;
    this.role = role;
    enabled = true;
    passwordSalt = RandomStringUtils.random(10);
    this.passwordHash =DigestUtils.md5DigestAsHex((password + passwordSalt).getBytes(
        StandardCharsets.UTF_8));  
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return passwordHash;
  }

  public void setPassword(String password) {
    this.passwordHash = DigestUtils
        .md5DigestAsHex(
            (password + passwordSalt)
                .getBytes(StandardCharsets.UTF_8)
        );
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
  
  public boolean checkPassword(String password) {
    return DigestUtils.md5DigestAsHex((password + passwordSalt).getBytes(StandardCharsets.UTF_8)).equals(passwordHash);
  }

}
