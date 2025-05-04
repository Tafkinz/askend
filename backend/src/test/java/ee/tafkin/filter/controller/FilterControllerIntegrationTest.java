package ee.tafkin.filter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.tafkin.BaseIntegrationTest;
import ee.tafkin.filter.controller.dto.CriteriaCreationResource;
import ee.tafkin.filter.controller.dto.FilterCreationResource;
import ee.tafkin.filter.domain.Filter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class FilterControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired private ObjectMapper objectMapper;

  @Test
  public void saveFilter_returnsCreatedFilter() throws Exception {
    FilterCreationResource filterCreationResource = new FilterCreationResource();
    filterCreationResource.setName("test-filter");
    CriteriaCreationResource criteriaCreationResource = new CriteriaCreationResource();
    criteriaCreationResource.setComparator("Ends with");
    criteriaCreationResource.setValue("the end");
    criteriaCreationResource.setCriteria("title");
    filterCreationResource.setCriteria(List.of(
      criteriaCreationResource
    ));

    MvcResult result = mvc.perform(post("/filter")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(filterCreationResource)))
      .andExpect(status().isOk())
      .andReturn();

    Filter filter = objectMapper.readValue(result.getResponse().getContentAsString(), Filter.class);

    assertThat(filter.getName()).isEqualTo("test-filter");
    assertThat(filter.getId()).isNotNull();
    assertThat(filter.getCriteria().getFirst().getCriteria()).isEqualTo("title");
    assertThat(filter.getCriteria().getFirst().getComparator()).isEqualTo("Ends with");
    assertThat(filter.getCriteria().getFirst().getValue()).isEqualTo("the end");
  }
}
