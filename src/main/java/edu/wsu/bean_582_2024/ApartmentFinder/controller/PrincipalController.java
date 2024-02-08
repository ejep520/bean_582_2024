package edu.wsu.bean_582_2024.ApartmentFinder.controller;

import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/principal")
public class PrincipalController {

  @GetMapping
  public Principal getPrincipal(Principal principal) {
    return principal;
  }
}
