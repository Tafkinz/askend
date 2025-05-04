package ee.tafkin.filter.controller;

import ee.tafkin.filter.domain.CriteriaType;
import ee.tafkin.filter.service.CriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/criteria")
@RequiredArgsConstructor
public class CriteriaController {
  private final CriteriaService criteriaService;

  @GetMapping
  public List<CriteriaType> getAllCriteria() {
    return criteriaService.getAllCriteria();
  }
}
