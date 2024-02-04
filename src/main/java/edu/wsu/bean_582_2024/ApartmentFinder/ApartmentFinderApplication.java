package edu.wsu.bean_582_2024.ApartmentFinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableJdbcRepositories
@Import({ApartmentFinderConfiguration.class})
@ComponentScan("edu.wsu.bean_582_2024.ApartmentFinder")
@EnableWebSecurity
@EnableWebMvc
public class ApartmentFinderApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApartmentFinderApplication.class, args);
	}
}
