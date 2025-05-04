package ee.tafkin.filter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Criteria {
  private Long id;
  private String value;
  private String criteria;
  private String comparator;
}
