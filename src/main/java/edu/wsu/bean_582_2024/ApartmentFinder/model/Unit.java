package edu.wsu.bean_582_2024.ApartmentFinder.model;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "unit")
@Table(name = "UNITS")
public class Unit extends AbstractEntity {
  private String address;
  private Integer bedrooms;
  private Double bathrooms;
  @Column(name = "living_room")
  private String livingRoom;
  private String kitchen;
  private Boolean featured;

  public Unit() {
    address = "";
    bedrooms = 0;
    bathrooms = 0.0d;
    livingRoom = "";
    kitchen = "";
    featured = false;
  }

  public Unit(String address, Integer bedrooms, Double bathrooms, String livingRoom, String kitchen,
      Boolean featured) {
    this.address = Objects.requireNonNullElse(address, "");
    this.bedrooms = Objects.requireNonNullElse(bedrooms, 0);
    this.bathrooms = Objects.requireNonNullElse(bathrooms, 0.0d);
    this.livingRoom = Objects.requireNonNullElse(livingRoom, "");
    this.kitchen = Objects.requireNonNullElse(kitchen, "");
    this.featured = Objects.requireNonNullElse(featured, false);
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = Objects.requireNonNullElse(address, "");
  }

  public Integer getBedrooms() {
    return bedrooms;
  }

  public void setBedrooms(Integer bedrooms) {
    this.bedrooms = Objects.requireNonNullElse(bedrooms, 0);
  }

  public Double getBathrooms() {
    return bathrooms;
  }

  public void setBathrooms(Double bathrooms) {
    this.bathrooms = Objects.requireNonNullElse(bathrooms, 0.0d);
  }

  public String getLivingRoom() {
    return livingRoom;
  }

  public void setLivingRoom(String livingRoom) {
    this.livingRoom = Objects.requireNonNullElse(livingRoom, "");
  }

  public String getKitchen() {
    return kitchen;
  }

  public void setKitchen(String kitchen) {
    this.kitchen = Objects.requireNonNullElse(kitchen, "");
  }

  public Boolean getFeatured() {
    return featured;
  }

  public void setFeatured(Boolean featured) {
    this.featured = Objects.requireNonNullElse(featured, false);
  }
}
