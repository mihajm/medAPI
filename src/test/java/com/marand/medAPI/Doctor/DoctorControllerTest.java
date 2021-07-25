package com.marand.medAPI.Doctor;

import com.marand.medAPI.Common.Controllers.BaseControllerTest;
import com.marand.medAPI.Disease.DiseaseDTO;
import com.marand.medAPI.Patient.PatientDTO;
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
class DoctorControllerTest extends BaseControllerTest<Doctor, DoctorDTO> {

  private long id = 50L;
  private String department = "marand";
  private Set<DiseaseDTO> diseases =
      new HashSet(Collections.singletonList(new DiseaseDTO("test_disease_name")));
  private Set<PatientDTO> patients =
      new HashSet(Collections.singletonList(new PatientDTO(55L, "Miha", "Mulec", diseases)));
  private DoctorDTO dto = createDTO();
  private DoctorService service;

  private String error_notNull = "Department cannot be blank, empty or null";
  private String error_wrongSize = "Department must be longer than 1 and shorter than 255 characters";
  private String error_charMismatch = "Department can only contain lowercase letters with words separated with either an underscore _ or a dash -";

  @Autowired
  protected DoctorControllerTest(DoctorService service) {
    super(service);
    this.service = service;
  }

  protected String getURI() {
    return "/doctors";
  }

  protected DoctorDTO createDTO() {
    return new DoctorDTO(id, department, patients);
  }

  protected DoctorDTO createDifferentDTO() {
    return new DoctorDTO(
        id + 10,
        department + "_new",
        new HashSet(
            Collections.singletonList(
                new PatientDTO(
                    60L,
                    "Janez",
                    "Novak",
                    new HashSet(Collections.singletonList(new DiseaseDTO("a_new_disease")))))));
  }

  protected Class<Doctor> getClazz() {
    return Doctor.class;
  }

  protected Class<Doctor[]> getArrayClazz() {
    return Doctor[].class;
  }

  @BeforeEach
  private void DiseaseControllerTestSetup() {
    dto = createDTO();
  }

  @AfterEach
  private void DoctorControllerTestCleanup() {
    service.drop();
  }

  @Test
  public void givenNullDepartment_whenPostCalled_returnsBadRequest() {
    dto.setDepartment(null);
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenNullDepartment_whenPostCalled_returnsValidationError() {
    dto.setDepartment(null);
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenNullDepartment_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setDepartment(null);
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
  public void givenEmptyDepartment_whenPostCalled_returnsBadRequest() {
    dto.setDepartment("");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenEmptyDepartment_whenPostCalled_returnsValidationError() {
    dto.setDepartment("");
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenEmptyDepartment_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setDepartment("");
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
  public void givenBlankDepartment_whenPostCalled_returnsBadRequest() {
    dto.setDepartment(" ");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenBlankDepartment_whenPostCalled_returnsValidationError() {
    dto.setDepartment(" ");
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenBlankDepartment_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setDepartment(" ");
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
  public void givenDepartmentLargerThan255Chars_whenPostCalled_returnsBadRequest() {
    dto.setDepartment("a".repeat(256));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDepartmentLargerThan255Chars_whenPostCalled_returnsValidationError() {
    dto.setDepartment("a".repeat(256));
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenDepartmentLargerThan255Chars_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setDepartment("a".repeat(256));
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
  public void givenDepartmentWithNonLetterChars_whenPostCalled_returnsBadRequest() {
    dto.setDepartment("123");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDepartmentWithNonLetterChars_whenPostCalled_returnsValidationError() {
    dto.setDepartment("123");
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenDepartmentWithNonLetterChars_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setDepartment("123");
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
  public void givenDepartmentWithUpperCasedCharacters_whenPostCalled_returnsBadRequest() {
    dto.setDepartment("ABC");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDepartmentWithUpperCasedCharacters_whenPostCalled_returnsValidationError() {
    dto.setDepartment("ABC");
    LinkedHashMap resp = postBadEntity(dto).getBody();
    assertTrue(((String) resp.get("message")).contains("ValidationError"));
  }

  @Test
  void givenDepartmentWithUpperCasedCharacters_whenPostCalled_returnsCorrectValidationErrors() {
    dto.setDepartment("ABC");
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
  public void givenNullPatients_whenPostCalled_returnsBadRequest() {
    dto.setPatients(null);
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenNullFirstName_whenPostCalled_returnsBadRequest() {
    dto.setPatients(
        new HashSet(Collections.singletonList(new PatientDTO(55L, null, "Mulec", diseases))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenNullLastName_whenPostCalled_returnsBadRequest() {
    dto.setPatients(
        new HashSet(Collections.singletonList(new PatientDTO(55L, "Miha", null, diseases))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenEmptyFirstName_whenPostCalled_returnsBadRequest() {
    dto.setPatients(
        new HashSet(Collections.singletonList(new PatientDTO(55L, "", "Mulec", diseases))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenEmptyLastName_whenPostCalled_returnsBadRequest() {
    dto.setPatients(
        new HashSet(Collections.singletonList(new PatientDTO(55L, "Miha", "", diseases))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenBlankFirstName_whenPostCalled_returnsBadRequest() {
    dto.setPatients(
        new HashSet(Collections.singletonList(new PatientDTO(55L, " ", "Mulec", diseases))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenBlankLastName_whenPostCalled_returnsBadRequest() {
    dto.setPatients(
        new HashSet(Collections.singletonList(new PatientDTO(55L, "Miha", " ", diseases))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenFirstNameLargerThan255Chars_whenPostCalled_returnsBadRequest() {
    dto.setPatients(
        new HashSet(
            Collections.singletonList(new PatientDTO(55L, "a".repeat(256), "Mulec", diseases))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenLastNameLargerThan255Chars_whenPostCalled_returnsBadRequest() {
    dto.setPatients(
        new HashSet(
            Collections.singletonList(new PatientDTO(55L, "Miha", "a".repeat(256), diseases))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenFirstNameWithNonLetterChars_whenPostCalled_returnsBadRequest() {
    dto.setPatients(
        new HashSet(Collections.singletonList(new PatientDTO(55L, "123", "Mulec", diseases))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenLastNameWithNonLetterChars_whenPostCalled_returnsBadRequest() {
    dto.setPatients(
        new HashSet(Collections.singletonList(new PatientDTO(55L, "Miha", "123", diseases))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenFirstNameWithoutUpperCasedFirstCharacter_whenPostCalled_returnsBadRequest() {
    dto.setPatients(
        new HashSet(Collections.singletonList(new PatientDTO(55L, "abc", "Mulec", diseases))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenLastNameWithoutUpperCasedFirstCharacter_whenPostCalled_returnsBadRequest() {
    dto.setPatients(
        new HashSet(Collections.singletonList(new PatientDTO(55L, "Miha", "abc", diseases))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenFirstNameWithUpperCasedCharacters_whenPostCalled_returnsBadRequest() {
    dto.setPatients(
        new HashSet(Collections.singletonList(new PatientDTO(55L, "ABC", "Mulec", diseases))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenLastNameWithUpperCasedCharacters_whenPostCalled_returnsBadRequest() {
    dto.setPatients(
        new HashSet(Collections.singletonList(new PatientDTO(55L, "Miha", "ABC", diseases))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenNullDiseases_whenPostCalled_returnsBadRequest() {
    dto.setPatients(
        new HashSet(Collections.singletonList(new PatientDTO(55L, "Miha", "Mulec", null))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithEmptyName_whenPostCalled_returnsBadRequest() {
    Set<DiseaseDTO> invalidDiseases = new HashSet(Collections.singletonList(new DiseaseDTO("")));
    dto.setPatients(
        new HashSet(
            Collections.singletonList(new PatientDTO(55L, "Miha", "Mulec", invalidDiseases))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithBlankName_whenPostCalled_returnsBadRequest() {
    Set<DiseaseDTO> invalidDiseases = new HashSet(Collections.singletonList(new DiseaseDTO(" ")));
    dto.setPatients(
        new HashSet(
            Collections.singletonList(new PatientDTO(55L, "Miha", "Mulec", invalidDiseases))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  void givenDiseaseWithNullName_whenPostCalled_returnsBadRequest() {
    Set<DiseaseDTO> invalidDiseases = new HashSet(Collections.singletonList(new DiseaseDTO(null)));
    dto.setPatients(
        new HashSet(
            Collections.singletonList(new PatientDTO(55L, "Miha", "Mulec", invalidDiseases))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithLargerThan255CharLongName_whenPostCalled_returnsBadRequest() {
    Set<DiseaseDTO> invalidDiseases =
        new HashSet(Collections.singletonList(new DiseaseDTO("a".repeat(256))));
    dto.setPatients(
        new HashSet(
            Collections.singletonList(new PatientDTO(55L, "Miha", "Mulec", invalidDiseases))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithNonLetterChars_whenPostCalled_returnsBadRequest() {
    Set<DiseaseDTO> invalidDiseases = new HashSet(Collections.singletonList(new DiseaseDTO("123")));
    dto.setPatients(
        new HashSet(
            Collections.singletonList(new PatientDTO(55L, "Miha", "Mulec", invalidDiseases))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenDiseaseWithUpperCasedChar_whenPostCalled_returnsBadRequest() {
    Set<DiseaseDTO> invalidDiseases =
        new HashSet(Collections.singletonList(new DiseaseDTO("A_disease")));
    dto.setPatients(
        new HashSet(
            Collections.singletonList(new PatientDTO(55L, "Miha", "Mulec", invalidDiseases))));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }
}
