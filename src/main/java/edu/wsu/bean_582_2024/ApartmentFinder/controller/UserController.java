package edu.wsu.bean_582_2024.ApartmentFinder.controller;

import edu.wsu.bean_582_2024.ApartmentFinder.component.UserDTO;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

/**
 * The MVC controller for the user class
 */
@Controller
@RequestMapping("user/")
public class UserController {
  @Autowired
  private UserRepository userRepository;
  @RequestMapping("getAll/")
  public List<User> GetAll() {
    List<User> users = new ArrayList<>();
    for (User thisUser : userRepository.findAll()){
      users.add(thisUser);
    }
    return users;
  }
  @GetMapping("/user/new")
  public String showRegistrationForm(WebRequest request, Model model)
  {
    UserDTO userDTO = new UserDTO();
    model.addAttribute("username", userDTO);
    return "newUser";
  }
}
