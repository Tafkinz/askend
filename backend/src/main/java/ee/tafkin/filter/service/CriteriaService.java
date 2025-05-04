package ee.tafkin.filter.service;

import ee.tafkin.filter.domain.CriteriaType;
import ee.tafkin.filter.repository.CriteriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CriteriaService {
  private final CriteriaRepository criteriaRepository;

  @Cacheable(value = "allCriteria")
  public List<CriteriaType> getAllCriteria() {
    return criteriaRepository.getAllFilterCriterias();
  }
}
