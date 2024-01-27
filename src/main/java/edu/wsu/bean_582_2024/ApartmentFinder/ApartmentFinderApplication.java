package edu.wsu.bean_582_2024.ApartmentFinder;

import edu.wsu.bean_582_2024.ApartmentFinder.model.InvalidZipCode;
import edu.wsu.bean_582_2024.ApartmentFinder.model.User;
import edu.wsu.bean_582_2024.ApartmentFinder.repository.UserRepository;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class ApartmentFinderApplication {

	@Autowired
	JdbcTemplate jdbcTemplate;
	private static final Logger log = LoggerFactory.getLogger(ApartmentFinderApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ApartmentFinderApplication.class, args);
	}

	@Bean
	ApplicationListener<ApplicationReadyEvent> basicApplicationListener(UserRepository userRepository) throws InvalidZipCode {
		return event ->userRepository.saveAll(Stream.of("A", "B", "C").map(name-> {
          try {
            return new User("Tony", "Stark", "", "54321", "tstark", "CBA321");
          } catch (InvalidZipCode e) {
            throw new RuntimeException(e);
          }
        }).toList())
				.forEach(System.out::println);
	}
	
}
