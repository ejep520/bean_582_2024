package edu.wsu.bean_582_2024.ApartmentFinder.model;

import java.util.Objects;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static edu.wsu.bean_582_2024.ApartmentFinder.helpers.Helpers.IsZipValid;

@Table
@Persistent
public class Unit {
  @Column
  private String unitNumber;
  @Column
  private String address;
  @Column
  private String zip;
  @Column
  private int bedrooms;
  @Column
  private float bathrooms;
  @Column
  private String livingRoom;
  @Column
  private String diningRoom;
  @Column
  private String kitchen;
  @Column
  private Integer hashValue;

  public Unit() { }
  
  @PersistenceCreator
  public Unit(String unitNumber, String address, String zip, int bedrooms, float bathrooms,
      String livingRoom, String diningRoom, String kitchen) throws InvalidZipCode{
    this.unitNumber = unitNumber;
    this.address = address;
    this.zip = zip;
    this.bedrooms = bedrooms;
    this.bathrooms = bathrooms;
    this.livingRoom = Objects.requireNonNullElse(livingRoom, "");
    this.diningRoom = Objects.requireNonNullElse(diningRoom, "");
    this.kitchen = Objects.requireNonNullElse(kitchen, "");
    if (!IsZipValid(this.zip)) throw new InvalidZipCode();
    calculateHash();
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
    calculateHash();
  }
  public String getUnitNumber() {
    return unitNumber;
  }

  public void setUnitNumber(String unitNumber) {
    this.unitNumber = unitNumber;
    calculateHash();
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) throws InvalidZipCode{
    if (IsZipValid(zip)) {
      this.zip = zip;
      calculateHash();
    }
    else 
      throw new InvalidZipCode();
  }

  public int getBedrooms() {
    return bedrooms;
  }

  public void setBedrooms(int bedrooms) {
    this.bedrooms = bedrooms;
  }

  public float getBathrooms() {
    return bathrooms;
  }

  public void setBathrooms(float bathrooms) {
    this.bathrooms = bathrooms;
  }

  public String getLivingRoom() {
    return livingRoom;
  }

  public void setLivingRoom(String livingRoom) {
    this.livingRoom = livingRoom;
  }

  public String getDiningRoom() {
    return diningRoom;
  }

  public void setDiningRoom(String diningRoom) {
    this.diningRoom = diningRoom;
  }

  public String getKitchen() {
    return kitchen;
  }

  public void setKitchen(String kitchen) {
    this.kitchen = kitchen;
  }
  
  public Integer getHashValue() {
    return hashValue;
  }

  private void calculateHash() {
    hashValue = (zip.hashCode() * address.hashCode() * unitNumber.hashCode())%Integer.MAX_VALUE;
  }

}
