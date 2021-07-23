package com.marand.medAPI.Doctor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marand.medAPI.Common.DTOs.BaseDTOTest;
import com.marand.medAPI.Disease.DiseaseDTO;
import com.marand.medAPI.Patient.PatientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DoctorDTOTest extends BaseDTOTest {

  private final long id = 50L;
  private final String department = "marand";
  private Set<PatientDTO> patients;
  private DoctorDTO dto = createDto();

  protected DoctorDTO createDto() {
    patients =
        new HashSet(
            Collections.singletonList(
                new PatientDTO(
                    55L,
                    "Miha",
                    "Mulec",
                    new HashSet(Collections.singletonList(new DiseaseDTO("a_disease"))))));
    return new DoctorDTO(id, department, patients);
  }

  @BeforeEach
  private void DoctorTestSetup() {
    dto = createDto();
  }

  @Test
  void canBeConstructedWithNoArgs() {
    assertDoesNotThrow((ThrowingSupplier<DoctorDTO>) DoctorDTO::new);
  }

  @Test
  void canBeConstructedWithIdAndDepartment() {
    assertDoesNotThrow(() -> new DoctorDTO(id, department));
  }

  @Test
  void canBeConstructedWithIdAndDepartmentAndPatients() {
    assertDoesNotThrow(this::createDto);
  }

  @Test
  void getDepartment_getsDepartment() {
    assertEquals(department, dto.getDepartment());
  }

  @Test
  void hasEmptyPatientsList_whenConstructed() {
    assertEquals(0, new DoctorDTO().getPatients().size());
  }

  @Test
  void setDepartment_setsDepartment() {
    String newDepartment = "marand_crm";
    dto.setDepartment(newDepartment);
    assertEquals(newDepartment, dto.getDepartment());
  }

  @Test
  void getPatients_getsPatients() {
    assertEquals(patients, dto.getPatients());
  }

  @Test
  void setPatients_setsPatients() {
    Set<PatientDTO> newPatientDTOs =
        new HashSet(
            Collections.singletonList(
                new PatientDTO(
                    60L,
                    "Janez",
                    "Novak",
                    new HashSet(Collections.singletonList(new DiseaseDTO("a_disease"))))));
    dto.setPatients(newPatientDTOs);
    assertEquals(newPatientDTOs, dto.getPatients());
  }

  @Test
  void isSerializable() {
    ObjectMapper mapper = new ObjectMapper();
    assertTrue(mapper.canSerialize(DoctorDTO.class));
  }

  @Test
  void containsNameWhenSerialized() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String objectString = mapper.writeValueAsString(dto);
    assertTrue(objectString.contains(department));
  }

  @Test
  void isDeSerializable() {
    ObjectMapper mapper = new ObjectMapper();
    assertTrue(mapper.canDeserialize(mapper.constructType(DoctorDTO.class)));
  }

  @Test
  void containsNameWhenDeSerialized() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String objectString = mapper.writeValueAsString(dto);
    DoctorDTO entity = mapper.readValue(objectString, DoctorDTO.class);
    assertEquals(department, entity.getDepartment());
  }
}
