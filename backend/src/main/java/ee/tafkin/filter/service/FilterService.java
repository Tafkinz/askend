package ee.tafkin.filter.service;

import ee.tafkin.common.Paged;
import ee.tafkin.errorhandling.exception.BadRequestException;
import ee.tafkin.errorhandling.exception.ResourceNotFoundException;
import ee.tafkin.filter.controller.dto.CriteriaCreationResource;
import ee.tafkin.filter.controller.dto.FilterCreationResource;
import ee.tafkin.filter.domain.CriteriaType;
import ee.tafkin.filter.domain.Filter;
import ee.tafkin.filter.repository.FilterRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.validator.GenericValidator.isDate;

@Service
@RequiredArgsConstructor
@Transactional
public class FilterService {
  private final FilterRepository filterRepository;
  private final CriteriaService criteriaService;

  public Paged<Filter> getFilters(int page, int perPage) {
    return filterRepository.getFilters(page, perPage, null);
  }

  public Filter getFilterById(Long id) {
    return filterRepository.getFilters(1, 1, id).getItems()
      .stream()
      .findFirst()
      .orElseThrow(() -> new ResourceNotFoundException("Filter with id " + id + " not found"));
  }

  public Long createFilter(@Valid FilterCreationResource filter) {
    Long filterId = filterRepository.createFilter(filter.getName());
    List<CriteriaType> criteriaTypes = criteriaService.getAllCriteria();
    filter.getCriteria().forEach(filterCriteria -> {
      CriteriaType ct = getCriteriaType(filterCriteria, criteriaTypes);

      validateValue(ct.getType(), filterCriteria.getValue());
      ct.getComparators().stream()
        .filter(c -> c.getComparator().equals(filterCriteria.getComparator()))
        .findFirst()
        .ifPresentOrElse(comp -> filterRepository.createFilterCriteria(filterCriteria.getValue(), filterId, comp.getId()), () -> {
          throw new BadRequestException("Invalid comparator %s".formatted(filterCriteria.getComparator()));
        });
    });
    return filterId;
  }

  private static CriteriaType getCriteriaType(CriteriaCreationResource filterCriteria, List<CriteriaType> criteriaTypes) {
    return criteriaTypes.stream()
      .filter(fc -> fc.getName().equals(filterCriteria.getCriteria()))
      .findFirst()
      .orElseThrow(() -> new BadRequestException("Invalid criteria %s".formatted(filterCriteria.getCriteria())));
  }

  private void validateValue(String type, String value) {
    switch (type) {
      case "number": {
        if (!NumberUtils.isCreatable(value)) {
          throw new BadRequestException("Value should be numeric %s".formatted(value));
        }
        break;
      }
      case "date": {
        if (!isDate(value, "yyyy-MM-dd", true)) {
          throw new BadRequestException("Value should be date %s".formatted(value));
        }
        break;
      }
      case "string": {
        if (isBlank(value) || value.length() > 255) {
          throw new BadRequestException("Value missing or too long: %s".formatted(value));
        }
        break;
      }
      default:
        throw new BadRequestException("Unknown value type: %s".formatted(type));
    }
  }
}
