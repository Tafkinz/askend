package ee.tafkin.filter.repository;

import ee.tafkin.filter.domain.CriteriaType;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.Collections.emptyMap;

@Repository
@RequiredArgsConstructor
public class CriteriaRepository {
  private final NamedParameterJdbcTemplate jdbcTemplate;

  public List<CriteriaType> getAllFilterCriterias() {
    String sql = """
      SELECT ct.id, ct.name, ct.type, ct.is_default, cvt.id as comparator_id, cvt.comparator, cvt.default_value FROM
      criteria_type ct JOIN criteria_value_type cvt ON ct.id = cvt.criteria_type_id
      """;

    return jdbcTemplate.query(sql, emptyMap(), new CriteriaTypeResultSetExtractor());
  }
}
