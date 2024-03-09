package edu.wsu.bean_582_2024.ApartmentFinder.model;

import java.io.Serial;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.DigestUtils;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Table(name = "apt_users")
@Entity
public class User extends AbstractEntity implements UserDetails, CredentialsContainer {
  @Serial
  private static final long serialVersionUID = -614412803435355964L;
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
  @OneToMany(targetEntity = Authority.class)
  private final List<Authority> authorities = new ArrayList<>();
  @OneToMany(targetEntity=Unit.class)
  private final List<Unit> units = new ArrayList<>();

  public User() {
    username = "";
    passwordHash = "";
    enabled = false;
  }

  public User(String username, String password, Role role) {
    generateSalt();
    this.username = username;
    this.role = role;
    enabled = true;
    this.passwordHash =
        DigestUtils.md5DigestAsHex((password + passwordSalt).getBytes(StandardCharsets.UTF_8));
  }

  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public String getPassword() {
    return passwordHash;
  }

  public void setPassword(String password) {
    if (passwordSalt == null || passwordSalt.isEmpty() || passwordSalt.isBlank())
      this.passwordHash = password;
    else
      this.passwordHash =
          DigestUtils.md5DigestAsHex((password + passwordSalt).getBytes(StandardCharsets.UTF_8));
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
    return DigestUtils.md5DigestAsHex((password + passwordSalt).getBytes(StandardCharsets.UTF_8))
        .equals(passwordHash);
  }

  public void generateSalt() {
    passwordSalt = RandomStringUtils.random(10, true, true);
  }

  public boolean isPasswordSaltEmpty() {
    return passwordSalt == null || passwordSalt.isEmpty() || passwordSalt.isBlank();
  }

  public List<Unit> getUnits() {
    return units;
  }
  
  @Override
  public void eraseCredentials() {}
  
  @Override
  public String toString() {
    return username;
  }
}
