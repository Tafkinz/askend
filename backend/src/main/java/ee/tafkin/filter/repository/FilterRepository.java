package ee.tafkin.filter.repository;

import ee.tafkin.common.Paged;
import ee.tafkin.filter.domain.Filter;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import static java.sql.Types.BIGINT;

@Repository
@RequiredArgsConstructor
public class FilterRepository {
  private final NamedParameterJdbcTemplate jdbcTemplate;

  public Paged<Filter> getFilters(Integer page, Integer perPage, Long id) {
    String sql = """
      SELECT (select count(distinct(id)) from filter where f.valid_from < current_timestamp AND f.valid_until IS NULL or f.valid_until > current_timestamp) as total,
      f.id, f.name, fc.id as filter_criteria_id, fc.value, cvt.comparator, ct.name as criteria_type
      FROM (select * from filter f
      WHERE f.valid_from < current_timestamp
      AND (f.valid_until IS NULL OR f.valid_until > current_timestamp)
      AND (:id IS NULL OR f.id = :id)
      order by f.valid_from DESC limit :perPage offset calculate_offset(:perPage, :page)) f
      JOIN filter_criteria fc ON f.id = fc.filter_id
      JOIN criteria_value_type cvt ON cvt.id = fc.criteria_value_type_id
      JOIN criteria_type ct ON ct.id = cvt.criteria_type_id
      WHERE fc.valid_from < current_timestamp AND fc.valid_until IS NULL or fc.valid_until > current_timestamp;
      """;

    MapSqlParameterSource params = new MapSqlParameterSource()
      .addValue("perPage", perPage)
      .addValue("page", page)
      .addValue("id", id, BIGINT);

    return jdbcTemplate.query(sql, params, new FilterResultSetExtractor(page));
  }

  public Long createFilter(@NotBlank String name) {
    String sql = "INSERT INTO filter (name, valid_from) VALUES (:name, current_timestamp) returning id";

    MapSqlParameterSource params = new MapSqlParameterSource()
      .addValue("name", name);

    return jdbcTemplate.queryForObject(sql, params, Long.class);
  }

  public void createFilterCriteria(@NotBlank String value, Long filterId, Long comparatorValueTypeId) {
    String sql = "INSERT INTO filter_criteria (value, filter_id, criteria_value_type_id, valid_from) VALUES (:value, :filterId, :comparatorValueTypeId, current_timestamp)";

    MapSqlParameterSource params = new MapSqlParameterSource()
      .addValue("value", value)
      .addValue("filterId", filterId)
      .addValue("comparatorValueTypeId", comparatorValueTypeId);

    jdbcTemplate.update(sql, params);
  }
}
