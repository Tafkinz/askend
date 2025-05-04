package ee.tafkin.filter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriteriaType {
  private Long id;
  private String name;
  private String type;
  private boolean isDefault;
  private List<Comparator> comparators;
}
