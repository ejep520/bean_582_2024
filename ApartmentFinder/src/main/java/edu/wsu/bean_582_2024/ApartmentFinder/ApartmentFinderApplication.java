package edu.wsu.bean_582_2024.ApartmentFinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
//@ComponentScan({" edu.wsu.bean_582_2024.ApartmentFinder.*"})
public class ApartmentFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApartmentFinderApplication.class, args);
	}
}
