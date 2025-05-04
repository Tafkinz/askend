package ee.tafkin.filter.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ee.tafkin.BaseIntegrationTest;
import ee.tafkin.filter.domain.CriteriaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CriteriaControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void getAllCriteria_returnsListOfCriteria() throws Exception {
    MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/criteria"))
      .andExpect(status().isOk())
      .andReturn();

    List<CriteriaType> criteriaTypes = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
    });

    assertThat(criteriaTypes).hasSize(3);
    assertThat(criteriaTypes.getFirst().getName()).isEqualTo("amount");
    assertThat(criteriaTypes.getFirst().getType()).isEqualTo("number");
    assertThat(criteriaTypes.getFirst().isDefault()).isTrue();
    assertThat(criteriaTypes.getFirst().getComparators()).hasSize(3);
    assertThat(criteriaTypes.getFirst().getComparators().getFirst().getComparator()).isEqualTo("More than");
    assertThat(criteriaTypes.getFirst().getComparators().getFirst().getDefaultValue()).isEqualTo("0");
  }
}
