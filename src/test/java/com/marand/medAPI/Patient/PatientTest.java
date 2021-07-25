package com.marand.medAPI.Patient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marand.medAPI.Common.Entities.BaseEntityTest;
import com.marand.medAPI.Disease.Disease;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest extends BaseEntityTest {

  private final long id = 50L;
  private final String firstName = "Miha";
  private final String lastName = "Mulec";
  private final Set<Disease> diseases =
      new HashSet(Collections.singletonList(new Disease("good_at_testing")));
  private Patient patient = new Patient(id, firstName, lastName, diseases);

  @BeforeEach
  private void patientTestSetup() {
    patient = new Patient(id, firstName, lastName, diseases);
  }

  @Test
  void whenConstructed_WithoutParams_DiseaseSet_isEmpty() {
    assertEquals(0, new Patient().getDiseases().size());
  }

  @Test
  void canBeConstructedWithNoArgs() {
    assertDoesNotThrow((ThrowingSupplier<Patient>) Patient::new);
  }

  @Test
  void canBeConstructedWithId() {
    assertDoesNotThrow(() -> new Patient(50L));
  }

  @Test
  void canBeConstructedWithIdFirstANdLastNames() {
    assertDoesNotThrow(() -> new Patient(50L, "Miha", "Mulec"));
  }

  @Test
  void whenConstructedWithIdFirstANdLastNames_hasAllSet() {
    Patient patient = new Patient(id, firstName, lastName);
    assertEquals(id, patient.getId());
    assertEquals(firstName, patient.getFirstName());
    assertEquals(lastName, patient.getLastName());
  }

  @Test
  void canBeConstructedWithIdFirstANdLastNamesAndDiseases() {
    assertDoesNotThrow(() -> new Patient(50L, "Miha", "Mulec", Collections.emptySet()));
  }

  @Test
  void whenConstructedWithIdFirstANdLastNamesAndDiseases_hasAllSet() {
    assertEquals(id, patient.getId());
    assertEquals(firstName, patient.getFirstName());
    assertEquals(lastName, patient.getLastName());
    assertEquals(diseases, patient.getDiseases());
  }

  @Test
  void getFirstName_ReturnsFirstName() {
    assertEquals(firstName, patient.getFirstName());
  }

  @Test
  void setFirstName_SetsFirstName() {
    String name = "Janez";
    patient.setFirstName(name);
    assertEquals(name, patient.getFirstName());
  }

  @Test
  void getLastName_ReturnsLastName() {
    assertEquals(lastName, patient.getLastName());
  }

  @Test
  void setLastName_SetsLastName() {
    String name = "Novak";
    patient.setLastName(name);
    assertEquals(name, patient.getLastName());
  }

  @Test
  void getDiseases_ReturnsDiseases() {
    assertEquals(diseases, patient.getDiseases());
  }

  @Test
  void setDiseases_SetsDiseases() {
    Set<Disease> newDiseases = new HashSet(Collections.singletonList(new Disease("new_disease")));
    patient.setDiseases(newDiseases);
    assertEquals(newDiseases, patient.getDiseases());
  }

  @Test
  void addDisease_addsDisease() {
    Disease newDisease = new Disease("new_disease");
    patient.addDisease(newDisease);
    assertTrue(patient.getDiseases().contains(newDisease));
  }

  @Test
  void removeDisease_removesDisease() {
    Disease newDisease = new Disease("new_disease");
    patient.addDisease(newDisease);
    patient.removeDisease(newDisease);
    assertFalse(patient.getDiseases().contains(newDisease));
  }

  @Test
  void givenUppercaseCharacters_WhenSettingFirstName_ConvertsNonFirstLettersToLowerCased() {
    patient.setFirstName("UPPER_CASED");
    assertEquals("Upper_cased", patient.getFirstName());
  }

  @Test
  void givenUppercaseCharacters_WhenSettingLastName_ConvertsNonFirstLettersToLowerCased() {
    patient.setLastName("UPPER_CASED");
    assertEquals("Upper_cased", patient.getLastName());
  }

  @Test
  void givenLowerCharacters_WhenSettingFirstName_ConvertsFirstLetterToUpperCase() {
    patient.setFirstName("lower_cased");
    assertEquals("Lower_cased", patient.getFirstName());
  }

  @Test
  void givenUppercaseCharacters_WhenSettingLastName_ConvertsFirstLetterToUpperCase() {
    patient.setLastName("lower_cased");
    assertEquals("Lower_cased", patient.getLastName());
  }

  @Test
  void whenSettingNullFirstName() throws IllegalArgumentException {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          patient.setFirstName(null);
        });
  }

  @Test
  void whenSettingNullLastName() throws IllegalArgumentException {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          patient.setLastName(null);
        });
  }

  @Test
  void whenSettingEmptyFirstName() throws IllegalArgumentException {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          patient.setFirstName("");
        });
  }

  @Test
  void whenSettingEmptyLastName() throws IllegalArgumentException {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          patient.setLastName("");
        });
  }

  @Test
  void whenSettingTooLongFirstName() throws IllegalArgumentException {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          patient.setFirstName("a".repeat(260));
        });
  }

  @Test
  void whenSettingTooLongLastName() throws IllegalArgumentException {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          patient.setLastName("a".repeat(260));
        });
  }

  @Test
  void whenSettingFirstNameWithIllegalChars() throws IllegalArgumentException {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          patient.setFirstName("123");
        });
  }

  @Test
  void whenSettingLastNameWithIllegalChars() throws IllegalArgumentException {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          patient.setFirstName("123");
        });
  }

  @Test
  void whenSettingNullDiseases() throws IllegalArgumentException {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          patient.setDiseases(null);
        });
  }

  @Test
  void whenAddingNullDisease() throws IllegalArgumentException {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          patient.addDisease(null);
        });
  }

  @Test
  void isSerializable() {
    ObjectMapper mapper = new ObjectMapper();
    assertTrue(mapper.canSerialize(Patient.class));
  }

  @Test
  void containsNameWhenSerialized() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String objectString = mapper.writeValueAsString(patient);
    assertTrue(objectString.contains(firstName));
    assertTrue(objectString.contains(lastName));
    for (Disease disease : diseases) {
      assertTrue(objectString.contains(String.valueOf(disease.getId())));
      assertTrue(objectString.contains(disease.getName()));
    }
  }

  @Test
  void isDeSerializable() {
    ObjectMapper mapper = new ObjectMapper();
    assertTrue(mapper.canDeserialize(mapper.constructType(Patient.class)));
  }

  @Test
  void containsNameWhenDeSerialized() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String objectString = mapper.writeValueAsString(patient);
    Patient entity = mapper.readValue(objectString, Patient.class);
    assertEquals(firstName, entity.getFirstName());
    assertEquals(lastName, entity.getLastName());
    assertEquals(diseases, entity.getDiseases());
  }
}
