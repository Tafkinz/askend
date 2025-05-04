package ee.tafkin.filter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comparator {
  private Long id;
  private String comparator;
  private String defaultValue;
}
