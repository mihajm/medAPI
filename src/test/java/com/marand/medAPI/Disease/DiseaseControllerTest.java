package com.marand.medAPI.Disease;

import com.marand.medAPI.Common.Controllers.BaseControllerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DiseaseControllerTest extends BaseControllerTest<Disease, DiseaseDTO> {

  private long id = 50L;
  private String name = "good_at_oop";
  private DiseaseDTO dto = createDTO();
  private DiseaseService service;

  @Autowired
  protected DiseaseControllerTest(DiseaseService service) {
    super(service);
    this.service = service;
  }

  protected String getURI() {
    return "/diseases";
  }

  protected DiseaseDTO createDTO() {
    return new DiseaseDTO(id, name);
  }

  protected DiseaseDTO createDifferentDTO() {
    return new DiseaseDTO(id + 10, name + "_new");
  }

  protected Class<Disease> getClazz() {
    return Disease.class;
  }

  protected Class<Disease[]> getArrayClazz() {
    return Disease[].class;
  }

  @BeforeEach
  private void DiseaseControllerTestSetup() {
    dto = createDTO();
  }

  @AfterEach
  private void DiseaseControllerTestCleanup() {
    service.drop();
  }

  @Test
  public void givenDiseaseWithEmptyName_whenPostCalled_returnsBadRequest() {
    dto.setName("");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithBlankName_whenPostCalled_returnsBadRequest() {
    dto.setName(" ");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithNullName_whenPostCalled_returnsBadRequest() {
    dto.setName(null);
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithLargerThan255CharLongName_whenPostCalled_returnsBadRequest() {
    dto.setName("a".repeat(256));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithNonLetterChars_whenPostCalled_returnsBadRequest() {
    dto.setName("123");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithUpperCasedChar_whenPostCalled_returnsBadRequest() {
    dto.setName("A_disease");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }
}
