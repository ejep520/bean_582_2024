package edu.wsu.bean_582_2024.ApartmentFinder.component;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class UserDTO {
  @NotNull
  @NotEmpty
  private String username;
  
  @NotNull
  @NotEmpty
  private String password;
  private String matchingPass;
  
  
  @NotNull
  @NotEmpty
  @Length(min=5,max=5)
  private String zip;
  
  @NotNull
  private String prefName;
  
  @NotNull
  @NotEmpty
  private Integer role;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMatchingPass() {
    return matchingPass;
  }

  public void setMatchingPass(String matchingPass) {
    this.matchingPass = matchingPass;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getPrefName() {
    return prefName;
  }

  public void setPrefName(String prefName) {
    this.prefName = prefName;
  }

  public Integer getRole() {
    return role;
  }

  public void setRole(Integer role) {
    this.role = role;
  }
}
