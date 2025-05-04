package ee.tafkin.filter.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CriteriaCreationResource {
  @NotBlank
  private String criteria;
  @NotBlank
  private String comparator;
  @NotBlank
  private String value;
}
