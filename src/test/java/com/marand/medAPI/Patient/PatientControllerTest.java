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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
  public void givenNullLastName_whenPostCalled_returnsBadRequest() {
    dto.setLastName(null);
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenEmptyFirstName_whenPostCalled_returnsBadRequest() {
    dto.setFirstName("");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenEmptyLastName_whenPostCalled_returnsBadRequest() {
    dto.setLastName("");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenBlankFirstName_whenPostCalled_returnsBadRequest() {
    dto.setFirstName(" ");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenBlankLastName_whenPostCalled_returnsBadRequest() {
    dto.setLastName(" ");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenFirstNameLargerThan255Chars_whenPostCalled_returnsBadRequest() {
    dto.setFirstName("a".repeat(256));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenLastNameLargerThan255Chars_whenPostCalled_returnsBadRequest() {
    dto.setLastName("a".repeat(256));
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenFirstNameWithNonLetterChars_whenPostCalled_returnsBadRequest() {
    dto.setFirstName("123");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenLastNameWithNonLetterChars_whenPostCalled_returnsBadRequest() {
    dto.setLastName("123");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenFirstNameWithoutUpperCasedFirstCharacter_whenPostCalled_returnsBadRequest() {
    dto.setFirstName("abc");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenLastNameWithoutUpperCasedFirstCharacter_whenPostCalled_returnsBadRequest() {
    dto.setLastName("abc");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenFirstNameWithUpperCasedCharacters_whenPostCalled_returnsBadRequest() {
    dto.setFirstName("ABC");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenLastNameWithUpperCasedCharacters_whenPostCalled_returnsBadRequest() {
    dto.setLastName("ABC");
    assertEquals(HttpStatus.BAD_REQUEST, postEntity(dto).getStatusCode());
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
