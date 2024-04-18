package edu.wsu.bean_582_2024.ApartmentFinder.service;

import edu.wsu.bean_582_2024.ApartmentFinder.model.Role;

public final class TestUsers {
  public final static String USERNAME_1 = "TestAdmin";
  public final static String USER_PASSWORD_1 = "foo";
  public final static Role USER_ROLE_1 = Role.ADMIN;
  public final static String USERNAME_2 = "TestOwner";
  public final static String USER_PASSWORD_2 = "bar";
  public final static Role USER_ROLE_2 = Role.OWNER;
  public final static String USERNAME_3 = "TestUser";
  public final static String USER_PASSWORD_3 = "foobar";
  public final static Role USER_ROLE_3 = Role.USER;
  public final static String BAD_USERNAME = "BadUsername";

  /**
   * @throws UnsupportedOperationException This class may not be instantiated.
   */
  public TestUsers() {
    throw new UnsupportedOperationException("This class may not be instantiated.");
  }
}
