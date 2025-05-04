package ee.tafkin.filter.domain;

import lombok.Data;

import java.util.List;

@Data
public class Filter {
  private Long id;
  private String name;
  private List<Criteria> criteria;
}
