package edu.wsu.bean_582_2024.ApartmentFinder.model;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static edu.wsu.bean_582_2024.ApartmentFinder.helpers.Helpers.IsZipValid;

/**
 * The user class
 * @author Erik Jepsen &lt;erik.jepsen@wsu.edu&gt;
 */
@Table
@Persistent
public class User {
  /** The email address of the user; the primary key of the table. */
  @Column
  @Id
  private String username;
  /** The preferred name(s) of the user. */
  @Column
  private String prefName;
  /** The role of the user<br/>
   * 0 - Registered user<br/>
   * 1 - Property owner<br/>
   * 2 - Site admin
   */
  @Column
  private Integer role;
  /** A hash of the user's password. */
  @Column
  private String password;
  /** The saved home ZIP code of the user */
  @Column
  private String zip;
  private final static String EMAIL_NULL_ERROR = "The email address may not be null.";
  private static final Integer DEFAULT_USER_LEVEL = 0;

  public User() {
    username = "";
    prefName = "";
    role = DEFAULT_USER_LEVEL;
    zip = "";
    password = "";
  }
  
  public User(String username, String prefName, Integer role, String password, String zip)
      throws InvalidZipCode {
    if (username == null || username.isEmpty() || username.isBlank())
      throw new NullPointerException(EMAIL_NULL_ERROR);
    if (!IsZipValid(zip)) throw new InvalidZipCode();
    this.username = username;
    this.prefName = Objects.requireNonNullElse(prefName, "");
    this.role = Objects.requireNonNullElse(role, DEFAULT_USER_LEVEL);
    this.password = password;
    this.zip = zip;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String email) {
    if (email == null || email.isEmpty() || email.isBlank())
      throw new NullPointerException(EMAIL_NULL_ERROR);
    this.username = email;
  }

  public String getPrefName() {
    return prefName;
  }

  public void setPrefName(String prefName) {
    this.prefName = Objects.requireNonNullElse(prefName, "");
  }

  public Integer getRole() {
    return role;
  }

  public void setRole(Integer role) {
    this.role = Objects.requireNonNullElse(role, DEFAULT_USER_LEVEL);
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) throws InvalidZipCode {
    if (IsZipValid(zip))
      this.zip = zip;
    else 
      throw new InvalidZipCode();
  }
}
