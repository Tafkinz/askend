package ee.tafkin.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paged<T> {
  private int total;
  private int currentPage;
  private List<T> items;
}
