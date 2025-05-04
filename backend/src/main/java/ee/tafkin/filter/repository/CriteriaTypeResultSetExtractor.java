package ee.tafkin.filter.repository;

import ee.tafkin.filter.domain.Comparator;
import ee.tafkin.filter.domain.CriteriaType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CriteriaTypeResultSetExtractor implements ResultSetExtractor<List<CriteriaType>> {

  @Override
  public List<CriteriaType> extractData(ResultSet rs) throws SQLException, DataAccessException {
    List<CriteriaType> criteriaTypes = new ArrayList<>();
    while (rs.next()) {
      Long id = rs.getLong("id");
      CriteriaType criteriaType = criteriaTypes.stream().filter(criteria -> criteria.getId().equals(id)).findFirst().orElse(null);
      if (criteriaType == null) {
        CriteriaType criteria = new CriteriaType();
        criteria.setId(id);
        criteria.setType(rs.getString("type"));
        criteria.setName(rs.getString("name"));
        criteria.setDefault(rs.getBoolean("is_default"));
        criteria.setComparators(new ArrayList<>());
        criteriaType = criteria;
        criteriaTypes.add(criteria);
      }
      List<Comparator> comparators = criteriaType.getComparators();
      comparators.add(extractComparator(rs));
    }
    return criteriaTypes;
  }

  private Comparator extractComparator(ResultSet rs) throws SQLException {
    Comparator comparator = new Comparator();
    comparator.setId(rs.getLong("comparator_id"));
    comparator.setComparator(rs.getString("comparator"));
    comparator.setDefaultValue(rs.getString("default_value"));
    return comparator;
  }
}
