package ee.tafkin.errorhandling;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "filter.errors")
@Data
public class ErrorProperties {
  private Boolean showStacktrace;
}
