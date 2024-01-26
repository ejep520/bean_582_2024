package edu.wsu.bean_582_2024.ApartmentFinder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class ApartmentFinderApplication {

	@Autowired
	JdbcTemplate jdbcTemplate;
	private static final Logger log = LoggerFactory.getLogger(ApartmentFinderApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ApartmentFinderApplication.class, args);
	}

}
