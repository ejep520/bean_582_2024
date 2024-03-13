package edu.wsu.bean_582_2024.ApartmentFinder.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
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
  @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
  private User user;

  public Unit() {
    address = "";
    bedrooms = 0;
    bathrooms = 0.0d;
    livingRoom = "";
    kitchen = "";
    featured = false;
    user = null;
  }

  public Unit(String address, Integer bedrooms, Double bathrooms, String livingRoom, String kitchen,
      Boolean featured, User user) {
    this.address = Objects.requireNonNullElse(address, "");
    this.bedrooms = Objects.requireNonNullElse(bedrooms, 0);
    this.bathrooms = Objects.requireNonNullElse(bathrooms, 0.0d);
    this.livingRoom = Objects.requireNonNullElse(livingRoom, "");
    this.kitchen = Objects.requireNonNullElse(kitchen, "");
    this.featured = Objects.requireNonNullElse(featured, false);
    this.user = user;
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
  
  public User getUser() {
    return user;
  }
  
  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Unit unit = (Unit) o;
    return Objects.equals(address, unit.getAddress()) && Objects.equals(bedrooms,
        unit.getBedrooms()) && Objects.equals(bathrooms, unit.getBathrooms())
        && Objects.equals(livingRoom, unit.getLivingRoom()) && Objects.equals(kitchen,
        unit.getKitchen()) && Objects.equals(featured, unit.getFeatured())
        && Objects.equals(this.getId(), unit.getId()) && Objects.equals(this.getVersion(),
        unit.getVersion());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), address, bedrooms, bathrooms, livingRoom, kitchen,
        featured);
  }
}
