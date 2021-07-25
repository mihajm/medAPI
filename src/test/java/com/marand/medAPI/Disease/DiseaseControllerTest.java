package com.marand.medAPI.Disease;

import com.marand.medAPI.Common.Controllers.BaseControllerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DiseaseControllerTest extends BaseControllerTest<Disease, DiseaseDTO> {

  private long id = 50L;
  private String name = "good_at_oop";
  private DiseaseDTO dto = createDTO();
  private DiseaseService service;

  private String error_notNull = "Disease name cannot be blank, empty or null";
  private String error_wrongSize = "Disease name must be longer than 1 and shorter than 255 characters";
  private String error_charMismatch = "Disease name can only contain lowercase letters with words separated with either an underscore _ or a dash -";

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
  public void givenDiseaseWithNullName_whenPostCalled_returnsBadRequest() {
    dto.setName(null);
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithNullName_whenPostCalled_returnsValidationError() {
    dto.setName(null);
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenDiseaseWithNullName_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setName(null);
    LinkedHashMap resp = postBadEntity(dto).getBody();

    LinkedHashMap errors = (LinkedHashMap) resp.get("validationErrors");
    List<String> expectedErrors = List.of(error_notNull);

    errors.forEach((fieldName, messages) -> {
      for (String err : expectedErrors) {
        assertTrue(((List<String>) messages).contains(err));
      }
    });
  }

  @Test
  public void givenDiseaseWithEmptyName_whenPostCalled_returnsBadRequest() {
    dto.setName("");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithEmptyName_whenPostCalled_returnsValidationError() {
    dto.setName("");
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenDiseaseWithEmptyName_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setName("");
    LinkedHashMap resp = postBadEntity(dto).getBody();

    LinkedHashMap errors = (LinkedHashMap) resp.get("validationErrors");
    List<String> expectedErrors = List.of(error_notNull, error_wrongSize, error_charMismatch);

    errors.forEach((fieldName, messages) -> {
      for (String err : expectedErrors) {
        assertTrue(((List<String>) messages).contains(err));
      }
    });
  }

  @Test
  public void givenDiseaseWithBlankName_whenPostCalled_returnsBadRequest() {
    dto.setName(" ");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithBlankName_whenPostCalled_returnsValidationError() {
    dto.setName(" ");
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenDiseaseWithBlankName_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setName(" ");
    LinkedHashMap resp = postBadEntity(dto).getBody();

    LinkedHashMap errors = (LinkedHashMap) resp.get("validationErrors");
    List<String> expectedErrors = List.of(error_notNull, error_charMismatch);

    errors.forEach((fieldName, messages) -> {
      for (String err : expectedErrors) {
        assertTrue(((List<String>) messages).contains(err));
      }
    });
  }

  @Test
  public void givenDiseaseWithLargerThan255CharLongName_whenPostCalled_returnsBadRequest() {
    dto.setName("a".repeat(256));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithLargerThan255CharLongName_whenPostCalled_returnsValidationError() {
    dto.setName("a".repeat(256));
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenDiseaseWithLargerThan255CharLongName_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setName("a".repeat(256));
    LinkedHashMap resp = postBadEntity(dto).getBody();

    LinkedHashMap errors = (LinkedHashMap) resp.get("validationErrors");
    List<String> expectedErrors = List.of(error_wrongSize);

    errors.forEach((fieldName, messages) -> {
      for (String err : expectedErrors) {
        assertTrue(((List<String>) messages).contains(err));
      }
    });
  }

  @Test
  public void givenDiseaseWithNonLetterChars_whenPostCalled_returnsBadRequest() {
    dto.setName("123");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithNonLetterChars_whenPostCalled_returnsValidationError() {
    dto.setName("123");
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenDiseaseWithNonLetterChars_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setName("123");
    LinkedHashMap resp = postBadEntity(dto).getBody();

    LinkedHashMap errors = (LinkedHashMap) resp.get("validationErrors");
    List<String> expectedErrors = List.of(error_charMismatch);

    errors.forEach((fieldName, messages) -> {
      for (String err : expectedErrors) {
        assertTrue(((List<String>) messages).contains(err));
      }
    });
  }

  @Test
  public void givenDiseaseWithUpperCasedChar_whenPostCalled_returnsBadRequest() {
    dto.setName("A_disease");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithUpperCasedChar_whenPostCalled_returnsValidationError() {
    dto.setName("A_disease");
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenDiseaseWithUpperCasedChar_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setName("A_disease");
    LinkedHashMap resp = postBadEntity(dto).getBody();

    LinkedHashMap errors = (LinkedHashMap) resp.get("validationErrors");
    List<String> expectedErrors = List.of(error_charMismatch);

    errors.forEach((fieldName, messages) -> {
      for (String err : expectedErrors) {
        assertTrue(((List<String>) messages).contains(err));
      }
    });
  }
}
