package ee.tafkin;

import ee.tafkin.errorhandling.ErrorProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableConfigurationProperties({ErrorProperties.class})
@EnableTransactionManagement
public class FilterApp {
  public static void main(String[] args) {
    SpringApplication.run(FilterApp.class, args);
  }
}
