package ee.tafkin.filter.controller;

import ee.tafkin.common.Paged;
import ee.tafkin.filter.controller.dto.FilterCreationResource;
import ee.tafkin.filter.domain.Filter;
import ee.tafkin.filter.service.FilterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/filter")
public class FilterController {
  private final FilterService filterService;

  @GetMapping
  public Paged<Filter> getFilters(@RequestParam int page, @RequestParam int perPage) {
    if (perPage > 100) {
      throw new IllegalArgumentException("PerPage must be less than 100");
    }
    return filterService.getFilters(page, perPage);
  }

  @PostMapping
  public Filter createFilter(@RequestBody @Valid FilterCreationResource filter) {
    Long filterId = filterService.createFilter(filter);
    return filterService.getFilterById(filterId);
  }
}
