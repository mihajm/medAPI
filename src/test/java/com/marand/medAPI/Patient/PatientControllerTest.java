package com.marand.medAPI.Patient;

import com.marand.medAPI.Common.Controllers.BaseControllerTest;
import com.marand.medAPI.Disease.DiseaseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PatientControllerTest extends BaseControllerTest<Patient, PatientDTO> {

  private long id = 50L;
  private String firstName = "Miha";
  private String lastName = "Mulec";
  private Set<DiseaseDTO> diseases =
      new HashSet(Collections.singletonList(new DiseaseDTO("test_disease_name")));
  private PatientDTO dto = createDTO();
  private PatientService service;

  private String error_firstName_notNull = "First name cannot be blank, empty or null";
  private String error_lastName_notNull = "Last name cannot be blank, empty or null";
  private String error_firstName_wrongSize = "First name must be longer than 1 and shorter than 255 characters";
  private String error_lastName_wrongSize = "Last name must be longer than 1 and shorter than 255 characters";
  private String error_firstName_charMismatch = "A first names first character must be an uppercase letter character, other characters must be lowercase letters with no separation";
  private String error_lastName_charMismatch = "A last names first character must be an uppercase letter character, other characters must be lowercase letters with no separation";

  @Autowired
  protected PatientControllerTest(PatientService service) {
    super(service);
    this.service = service;
  }

  protected String getURI() {
    return "/patients";
  }

  protected PatientDTO createDTO() {
    return new PatientDTO(id, firstName, lastName, diseases);
  }

  protected PatientDTO createDifferentDTO() {
    return new PatientDTO(
        id + 10,
        firstName + "_new",
        lastName + "_new",
        new HashSet(Collections.singletonList(new DiseaseDTO("a_new_disease"))));
  }

  protected Class<Patient> getClazz() {
    return Patient.class;
  }

  protected Class<Patient[]> getArrayClazz() {
    return Patient[].class;
  }

  @BeforeEach
  private void DiseaseControllerTestSetup() {
    dto = createDTO();
  }

  @AfterEach
  private void PatientControllerTestCleanup() {
    service.drop();
  }


  @Test
  public void givenNullFirstName_whenPostCalled_returnsBadRequest() {
    dto.setFirstName(null);
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenNullFirstName_whenPostCalled_returnsValidationError() {
    dto.setFirstName(null);
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenNullFirstName_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setFirstName(null);
    LinkedHashMap resp = postBadEntity(dto).getBody();

    LinkedHashMap errors = (LinkedHashMap) resp.get("validationErrors");
    List<String> expectedErrors = List.of(error_firstName_notNull);

    errors.forEach((fieldName, messages) -> {
      for (String err : expectedErrors) {
        assertTrue(((List<String>) messages).contains(err));
      }
    });
  }

  @Test
  public void givenNullLastName_whenPostCalled_returnsBadRequest() {
    dto.setLastName(null);
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenNullLastName_whenPostCalled_returnsValidationError() {
    dto.setLastName(null);
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenNullLastName_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setLastName(null);
    LinkedHashMap resp = postBadEntity(dto).getBody();

    LinkedHashMap errors = (LinkedHashMap) resp.get("validationErrors");
    List<String> expectedErrors = List.of(error_lastName_notNull);

    errors.forEach((fieldName, messages) -> {
      for (String err : expectedErrors) {
        assertTrue(((List<String>) messages).contains(err));
      }
    });
  }

  @Test
  public void givenEmptyFirstName_whenPostCalled_returnsBadRequest() {
    dto.setFirstName("");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenEmptyFirstName_whenPostCalled_returnsValidationError() {
    dto.setFirstName("");
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenEmptyFirstName_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setFirstName("");
    LinkedHashMap resp = postBadEntity(dto).getBody();

    LinkedHashMap errors = (LinkedHashMap) resp.get("validationErrors");
    List<String> expectedErrors = List.of(error_firstName_notNull, error_firstName_wrongSize, error_firstName_charMismatch);

    errors.forEach((fieldName, messages) -> {
      for (String err : expectedErrors) {
        assertTrue(((List<String>) messages).contains(err));
      }
    });
  }

  @Test
  public void givenEmptyLastName_whenPostCalled_returnsBadRequest() {
    dto.setLastName("");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenEmptyLastName_whenPostCalled_returnsValidationError() {
    dto.setLastName("");
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenEmptyLastName_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setLastName("");
    LinkedHashMap resp = postBadEntity(dto).getBody();

    LinkedHashMap errors = (LinkedHashMap) resp.get("validationErrors");
    List<String> expectedErrors = List.of(error_lastName_notNull, error_lastName_wrongSize, error_lastName_charMismatch);

    errors.forEach((fieldName, messages) -> {
      for (String err : expectedErrors) {
        assertTrue(((List<String>) messages).contains(err));
      }
    });
  }

  @Test
  public void givenBlankFirstName_whenPostCalled_returnsBadRequest() {
    dto.setFirstName(" ");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenBlankFirstName_whenPostCalled_returnsValidationError() {
    dto.setFirstName(" ");
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenBlankFirstName_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setFirstName(" ");
    LinkedHashMap resp = postBadEntity(dto).getBody();

    LinkedHashMap errors = (LinkedHashMap) resp.get("validationErrors");
    List<String> expectedErrors = List.of(error_firstName_notNull, error_firstName_charMismatch);

    errors.forEach((fieldName, messages) -> {
      for (String err : expectedErrors) {
        assertTrue(((List<String>) messages).contains(err));
      }
    });
  }

  @Test
  public void givenBlankLastName_whenPostCalled_returnsBadRequest() {
    dto.setLastName(" ");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenBlankLastName_whenPostCalled_returnsValidationError() {
    dto.setLastName(" ");
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenBlankLastName_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setLastName(" ");
    LinkedHashMap resp = postBadEntity(dto).getBody();

    LinkedHashMap errors = (LinkedHashMap) resp.get("validationErrors");
    List<String> expectedErrors = List.of(error_lastName_notNull, error_lastName_charMismatch);

    errors.forEach((fieldName, messages) -> {
      for (String err : expectedErrors) {
        assertTrue(((List<String>) messages).contains(err));
      }
    });
  }

  @Test
  public void givenFirstNameLargerThan255Chars_whenPostCalled_returnsBadRequest() {
    dto.setFirstName("a".repeat(256));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenFirstNameLargerThan255Chars_whenPostCalled_returnsValidationError() {
    dto.setFirstName("a".repeat(256));
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenFirstNameLargerThan255Chars_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setFirstName("a".repeat(256));
    LinkedHashMap resp = postBadEntity(dto).getBody();

    LinkedHashMap errors = (LinkedHashMap) resp.get("validationErrors");
    List<String> expectedErrors = List.of(error_firstName_wrongSize);

    errors.forEach((fieldName, messages) -> {
      for (String err : expectedErrors) {
        assertTrue(((List<String>) messages).contains(err));
      }
    });
  }

  @Test
  public void givenLastNameLargerThan255Chars_whenPostCalled_returnsBadRequest() {
    dto.setLastName("a".repeat(256));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenLastNameLargerThan255Chars_whenPostCalled_returnsValidationError() {
    dto.setLastName("a".repeat(256));
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenLastNameLargerThan255Chars_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setLastName("a".repeat(256));
    LinkedHashMap resp = postBadEntity(dto).getBody();

    LinkedHashMap errors = (LinkedHashMap) resp.get("validationErrors");
    List<String> expectedErrors = List.of(error_lastName_wrongSize);

    errors.forEach((fieldName, messages) -> {
      for (String err : expectedErrors) {
        assertTrue(((List<String>) messages).contains(err));
      }
    });
  }

  @Test
  public void givenFirstNameWithNonLetterChars_whenPostCalled_returnsBadRequest() {
    dto.setFirstName("123");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenFirstNameWithNonLetterChars_whenPostCalled_returnsValidationError() {
    dto.setFirstName("123");
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenFirstNameWithNonLetterChars_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setFirstName("123");
    LinkedHashMap resp = postBadEntity(dto).getBody();

    LinkedHashMap errors = (LinkedHashMap) resp.get("validationErrors");
    List<String> expectedErrors = List.of(error_firstName_charMismatch);

    errors.forEach((fieldName, messages) -> {
      for (String err : expectedErrors) {
        assertTrue(((List<String>) messages).contains(err));
      }
    });
  }

  @Test
  public void givenLastNameWithNonLetterChars_whenPostCalled_returnsBadRequest() {
    dto.setLastName("123");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenLastNameWithNonLetterChars_whenPostCalled_returnsValidationError() {
    dto.setLastName("123");
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenLastNameWithNonLetterChars_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setLastName("123");
    LinkedHashMap resp = postBadEntity(dto).getBody();

    LinkedHashMap errors = (LinkedHashMap) resp.get("validationErrors");
    List<String> expectedErrors = List.of(error_lastName_charMismatch);

    errors.forEach((fieldName, messages) -> {
      for (String err : expectedErrors) {
        assertTrue(((List<String>) messages).contains(err));
      }
    });
  }

  @Test
  public void givenFirstNameWithoutUpperCasedFirstCharacter_whenPostCalled_returnsBadRequest() {
    dto.setFirstName("abc");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenFirstNameWithoutUpperCasedFirstCharacter_whenPostCalled_returnsValidationError() {
    dto.setFirstName("abc");
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenFirstNameWithoutUpperCasedFirstCharacter_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setFirstName("abc");
    LinkedHashMap resp = postBadEntity(dto).getBody();

    LinkedHashMap errors = (LinkedHashMap) resp.get("validationErrors");
    List<String> expectedErrors = List.of(error_firstName_charMismatch);

    errors.forEach((fieldName, messages) -> {
      for (String err : expectedErrors) {
        assertTrue(((List<String>) messages).contains(err));
      }
    });
  }

  @Test
  public void givenLastNameWithoutUpperCasedFirstCharacter_whenPostCalled_returnsBadRequest() {
    dto.setLastName("abc");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenLastNameWithoutUpperCasedFirstCharacter_whenPostCalled_returnsValidationError() {
    dto.setLastName("abc");
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenLastNameWithoutUpperCasedFirstCharacter_returnsCorrectValidationErrors() {
    dto.setLastName("abc");
    LinkedHashMap resp = postBadEntity(dto).getBody();

    LinkedHashMap errors = (LinkedHashMap) resp.get("validationErrors");
    List<String> expectedErrors = List.of(error_lastName_charMismatch);

    errors.forEach((fieldName, messages) -> {
      for (String err : expectedErrors) {
        assertTrue(((List<String>) messages).contains(err));
      }
    });
  }

  @Test
  public void givenFirstNameWithUpperCasedCharacters_whenPostCalled_returnsBadRequest() {
    dto.setFirstName("ABC");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenFirstNameWithUpperCasedCharacters_whenPostCalled_returnsValidationError() {
    dto.setFirstName("ABC");
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenFirstNameWithUpperCasedCharacters_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setFirstName("ABC");
    LinkedHashMap resp = postBadEntity(dto).getBody();

    LinkedHashMap errors = (LinkedHashMap) resp.get("validationErrors");
    List<String> expectedErrors = List.of(error_firstName_charMismatch);

    errors.forEach((fieldName, messages) -> {
      for (String err : expectedErrors) {
        assertTrue(((List<String>) messages).contains(err));
      }
    });
  }

  @Test
  public void givenLastNameWithUpperCasedCharacters_whenPostCalled_returnsBadRequest() {
    dto.setLastName("ABC");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenLastNameWithUpperCasedCharacters_whenPostCalled_returnsValidationError() {
    dto.setLastName("ABC");
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenLastNameWithUpperCasedCharacters_returnsCorrectValidationErrors() {
    dto.setLastName("ABC");
    LinkedHashMap resp = postBadEntity(dto).getBody();

    LinkedHashMap errors = (LinkedHashMap) resp.get("validationErrors");
    List<String> expectedErrors = List.of(error_lastName_charMismatch);

    errors.forEach((fieldName, messages) -> {
      for (String err : expectedErrors) {
        assertTrue(((List<String>) messages).contains(err));
      }
    });
  }

  @Test
  public void givenNullDiseases_whenPostCalled_returnsBadRequest() {
    dto.setDiseases(null);
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithEmptyName_whenPostCalled_returnsBadRequest() {
    dto.setDiseases(new HashSet(Collections.singletonList(new DiseaseDTO(""))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithBlankName_whenPostCalled_returnsBadRequest() {
    dto.setDiseases(new HashSet(Collections.singletonList(new DiseaseDTO(" "))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  void givenDiseaseWithNullName_whenPostCalled_returnsBadRequest() {
    dto.setDiseases(new HashSet(Collections.singletonList(new DiseaseDTO(null))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithLargerThan255CharLongName_whenPostCalled_returnsBadRequest() {
    dto.setDiseases(new HashSet(Collections.singletonList(new DiseaseDTO("a".repeat(256)))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithNonLetterChars_whenPostCalled_returnsBadRequest() {
    dto.setDiseases(new HashSet(Collections.singletonList(new DiseaseDTO("123"))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithUpperCasedChar_whenPostCalled_returnsBadRequest() {
    dto.setDiseases(new HashSet(Collections.singletonList(new DiseaseDTO("A_disease"))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }
}
