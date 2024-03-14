package edu.wsu.bean_582_2024.ApartmentFinder.dao;

import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Primary
@EnableJpaRepositories(basePackages = "edu.wsu.bean_582_2024.ApartmentFinder.dao")
@EnableTransactionManagement
public class MySqlJpaConfig {
  @Autowired
  Environment env;
  
  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(
        Objects.requireNonNull(env.getProperty("spring.datasource.driverClassName")));
    dataSource.setUrl(Objects.requireNonNull(env.getProperty("spring.datasource.url")));
    dataSource.setUsername(Objects.requireNonNull(env.getProperty("spring.datasource.username")));
    dataSource.setPassword(Objects.requireNonNull(env.getProperty("spring.datasource.password")));
    return dataSource;
  }
}
