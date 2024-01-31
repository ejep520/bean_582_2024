package edu.wsu.bean_582_2024.ApartmentFinder.helpers;


/**
 * A class of static helpers that allow reuse of commonly applied code.
 */
public class Helpers {
  /**
   * Determines whether the ZIP code provided may be valid
   * @return boolean decision on ZIP code's possible validity. 
   */
  public static boolean IsZipValid(String zip) {
    int intZip;
    try {
      intZip = Integer.parseInt(zip);
    } catch (NumberFormatException e) {
      return false;
    }
    return intZip > 1000 && intZip < 100000;
  }
}
