package ee.tafkin.filter.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterCreationResource {
  @NotBlank
  private String name;
  @Valid
  @NotEmpty
  private List<CriteriaCreationResource> criteria;
}
