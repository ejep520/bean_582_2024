import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.TransactionManager;

@Configuration
@EnableJdbcRepositories
public class ApplicationConfig extends AbstractJdbcConfiguration {
  @Bean
  DataSource dataSource() {
    return DataSourceBuilder
        .create()
        .username(System.getenv("azure_db_admin_name"))
        .password(System.getenv("azure_db_admin_pass"))
        .url("jdbc:sqlserver://bean-582-2024.database.windows.net;database=bean_582_2024;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30")
        .driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
        .build();
  }
  
  @Bean
  NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource); 
  }
  
  @Bean
  TransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }
}
