package edu.wsu.bean_582_2024.ApartmentFinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class} )
//@EnableJpaRepositories
//@Import(edu.wsu.bean_582_2024.ApartmentFinder.security.SecurityConfig.class)
//@ComponentScan("edu.wsu.bean_582_2024.ApartmentFinder")
//@EntityScan("edu.wsu.bean_582_ApartmentFinder.model")
public class ApartmentFinderApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApartmentFinderApplication.class, args);
	}
}
