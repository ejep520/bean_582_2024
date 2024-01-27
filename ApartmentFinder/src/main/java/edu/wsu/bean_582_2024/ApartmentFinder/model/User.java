package edu.wsu.bean_582_2024.ApartmentFinder.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Basic class for users of the website.
 * @author Erik Jepsen &lt;erik.jepsen@wsu.edu&gt;
 */
/*
 * I am going to suppose this class is not going to survive in its current form
 * until the end of the project. Alter this class as need be. ELJ -- Jan 26, 2024
 */
@Table
public class User {

  /** First (or given) name of the user */
  @Column
  private String f_name;
  /** Last name of the user */
  @Column
  private String l_name;
  /** Preferred name of the user */
  @Column
  private String pref_name;
  /** Zip code of the user. Five digits only! */
  @Column
  private String zip;
  /** Chosen username of the user */
  @Id
  @Column
  private String u_name;
  /** A hash of the user's password. */
  @Column
  private String pass_hash;

  /**
   * Gets the user's first name
   * @return A String containing the user's first name.
   */
  public String getF_name() {
    return f_name;
  }

  public void setF_name(String f_name) {
    this.f_name = f_name;
  }


  public String getL_name() {
    return l_name;
  }

  public void setL_name(String l_name) {
    this.l_name = l_name;
  }

  public String getPref_name() {
    return pref_name;
  }

  public void setPref_name(String pref_name) {
    this.pref_name = pref_name;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getU_name() {
    return u_name;
  }

  public void setU_name(String u_name) {
    this.u_name = u_name;
  }

  public String getPass_hash() {
    return pass_hash;
  }

  public void setPass_hash(String pass_hash) {
    this.pass_hash = pass_hash;
  }

  /**
   * This is a do-nothing class constructor. Its use is not advised.
   */
  public User() { }

  /**
   * Class Constructor
   * @param f_name User's first name
   * @param l_name User's last name
   * @param pref_name User's preferred name
   * @param zip The user's ZIP code
   * @param u_name The user's username
   * @param pass_hash The user's hashed password
   * @throws InvalidZipCode Thrown if the provided zip code does not meet the requirements.
   */
  @PersistenceCreator
  public User(String f_name, String l_name, String pref_name, String zip, String u_name, String pass_hash) throws InvalidZipCode{
    this.f_name = f_name;
    this.l_name = l_name;
    if (pref_name == null || pref_name.isEmpty()) this.pref_name = this.f_name;
    else this.pref_name = pref_name;
    this.zip = zip;
    this.u_name = u_name;
    this.pass_hash = pass_hash;
    if (!VerifyZip()) throw new InvalidZipCode();
  }

  /**
   * Determines whether the ZIP code provided may be valid
   * @return boolean decision on ZIP code's possible validity. 
   */
  private boolean VerifyZip() {
    int zip_int;
    try {
      zip_int = Integer.parseInt(zip);
    } catch (NumberFormatException e) {
      return false;
    }
    return zip_int > 0 && zip_int <= 99999;
  }
}
