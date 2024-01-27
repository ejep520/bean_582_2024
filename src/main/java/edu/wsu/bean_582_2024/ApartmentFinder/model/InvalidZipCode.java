package edu.wsu.bean_582_2024.ApartmentFinder.model;

public class InvalidZipCode extends Exception{
  public InvalidZipCode() {
    super("The zip code provided to the class is not valid.");
  }
}
