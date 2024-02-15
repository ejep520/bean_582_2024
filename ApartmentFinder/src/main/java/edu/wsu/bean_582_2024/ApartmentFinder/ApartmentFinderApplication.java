package edu.wsu.bean_582_2024.ApartmentFinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ApartmentFinderApplication {
  public static void main(String[] args) {
    SpringApplication.run(ApartmentFinderApplication.class, args);
  }
}
