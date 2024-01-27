package edu.wsu.bean_582_2024.ApartmentFinder.controller;

import edu.wsu.bean_582_2024.ApartmentFinder.model.InvalidZipCode;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
  
  @GetMapping
  public User getUserData() {
    User user;
    try {
      user = new User("Mickey", "Mouse", "", "12345", "mmouse", "ABC123");
    } catch (InvalidZipCode e) {
      System.err.println(e.getMessage());
      user = null;
    }
    return user;
  }
}
