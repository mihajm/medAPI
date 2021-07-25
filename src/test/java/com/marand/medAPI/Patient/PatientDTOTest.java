package com.marand.medAPI.Patient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marand.medAPI.Common.DTOs.BaseDTOTest;
import com.marand.medAPI.Disease.DiseaseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PatientDTOTest extends BaseDTOTest {

  private final long id = 50L;
  private final String firstName = "Miha";
  private final String lastName = "Mulec";
  private final Set<DiseaseDTO> diseases =
      new HashSet(Collections.singletonList(new DiseaseDTO("good_at_testing")));
  private PatientDTO dto = new PatientDTO(id, firstName, lastName, diseases);

  @BeforeEach
  private void patientTestSetup() {
    dto = new PatientDTO(id, firstName, lastName, diseases);
  }

  @Test
  void whenConstructed_WithoutParams_DiseaseSet_isEmpty() {
    assertEquals(0, new PatientDTO().getDiseases().size());
  }

  @Test
  void canBeConstructedWithNoArgs() {
    assertDoesNotThrow((ThrowingSupplier<PatientDTO>) PatientDTO::new);
  }

  @Test
  void canBeConstructedWithId() {
    assertDoesNotThrow(() -> new PatientDTO(50L));
  }

  @Test
  void canBeConstructedWithIdFirstANdLastNames() {
    assertDoesNotThrow(() -> new PatientDTO(50L, "Miha", "Mulec"));
  }

  @Test
  void whenConstructedWithIdFirstANdLastNames_hasAllSet() {
    PatientDTO dto = new PatientDTO(id, firstName, lastName);
    assertEquals(id, dto.getId());
    assertEquals(firstName, dto.getFirstName());
    assertEquals(lastName, dto.getLastName());
  }

  @Test
  void canBeConstructedWithIdFirstANdLastNamesAndDiseases() {
    assertDoesNotThrow(() -> new PatientDTO(50L, "Miha", "Mulec", Collections.emptySet()));
  }

  @Test
  void whenConstructedWithIdFirstANdLastNamesAndDiseases_hasAllSet() {
    assertEquals(id, dto.getId());
    assertEquals(firstName, dto.getFirstName());
    assertEquals(lastName, dto.getLastName());
    assertEquals(diseases, dto.getDiseases());
  }

  @Test
  void getFirstName_ReturnsFirstName() {
    assertEquals(firstName, dto.getFirstName());
  }

  @Test
  void setFirstName_SetsFirstName() {
    String name = "Janez";
    dto.setFirstName(name);
    assertEquals(name, dto.getFirstName());
  }

  @Test
  void getLastName_ReturnsLastName() {
    assertEquals(lastName, dto.getLastName());
  }

  @Test
  void setLastName_SetsLastName() {
    String name = "Novak";
    dto.setLastName(name);
    assertEquals(name, dto.getLastName());
  }

  @Test
  void getDiseases_ReturnsDiseases() {
    assertEquals(diseases, dto.getDiseases());
  }

  @Test
  void setDiseases_SetsDiseases() {
    Set<DiseaseDTO> newDiseases =
        new HashSet(Collections.singletonList(new DiseaseDTO("new_disease")));
    dto.setDiseases(newDiseases);
    assertEquals(newDiseases, dto.getDiseases());
  }

  @Test
  void isSerializable() {
    ObjectMapper mapper = new ObjectMapper();
    assertTrue(mapper.canSerialize(PatientDTO.class));
  }

  @Test
  void containsNameWhenSerialized() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String objectString = mapper.writeValueAsString(dto);
    assertTrue(objectString.contains(firstName));
    assertTrue(objectString.contains(lastName));
    for (DiseaseDTO disease : diseases) {
      assertTrue(objectString.contains(String.valueOf(disease.getId())));
      assertTrue(objectString.contains(disease.getName()));
    }
  }

  @Test
  void isDeSerializable() {
    ObjectMapper mapper = new ObjectMapper();
    assertTrue(mapper.canDeserialize(mapper.constructType(PatientDTO.class)));
  }

  @Test
  void containsNameWhenDeSerialized() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String objectString = mapper.writeValueAsString(dto);
    PatientDTO entity = mapper.readValue(objectString, PatientDTO.class);
    assertEquals(firstName, entity.getFirstName());
    assertEquals(lastName, entity.getLastName());
    assertEquals(diseases, entity.getDiseases());
  }
}
