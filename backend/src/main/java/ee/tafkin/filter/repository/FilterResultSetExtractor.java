package ee.tafkin.filter.repository;

import ee.tafkin.common.Paged;
import ee.tafkin.filter.domain.Criteria;
import ee.tafkin.filter.domain.Filter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FilterResultSetExtractor implements ResultSetExtractor<Paged<Filter>> {
  private final int currentPage;

  public FilterResultSetExtractor(int currentPage) {
    super();
    this.currentPage = currentPage;
  }

  @Override
  public Paged<Filter> extractData(ResultSet rs) throws SQLException, DataAccessException {
    Paged<Filter> result = new Paged<>(0, currentPage, new ArrayList<>());
    result.setCurrentPage(currentPage);
    while (rs.next()) {
      result.setTotal(rs.getInt("total"));
      Long filterId = rs.getLong("id");
      Filter filter = result.getItems().stream().filter(f -> f.getId().equals(filterId)).findFirst().orElse(null);
      if (filter == null) {
        filter = new Filter();
        filter.setId(filterId);
        filter.setName(rs.getString("name"));
        filter.setCriteria(new ArrayList<>());
        result.getItems().add(filter);
      }
      Criteria criteria = new Criteria();
      criteria.setId(rs.getLong("filter_criteria_id"));
      criteria.setValue(rs.getString("value"));
      criteria.setCriteria(rs.getString("criteria_type"));
      criteria.setComparator(rs.getString("comparator"));
      filter.getCriteria().add(criteria);
    }
    return result;
  }
}
