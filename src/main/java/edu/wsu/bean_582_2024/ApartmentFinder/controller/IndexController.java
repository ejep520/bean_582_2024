package edu.wsu.bean_582_2024.ApartmentFinder.controller;

import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
  @RequestMapping("/")
  public String getIndex() {
    return "index";
  }
  
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login(@ModelAttribute("user")User user, BindingResult errors) {
    return "login";
  }
}
