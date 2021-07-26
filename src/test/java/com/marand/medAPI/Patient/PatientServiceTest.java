package com.marand.medAPI.Patient;

import com.marand.medAPI.Common.Services.ReportedServiceTest;
import com.marand.medAPI.Disease.Disease;
import com.marand.medAPI.Disease.DiseaseDTO;
import com.marand.medAPI.Disease.DiseaseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PatientServiceTest extends ReportedServiceTest<Patient, PatientDTO> {

  private PatientService service;
  private DiseaseService diseaseService;

  private long id = 50L;
  private String firstName = "Miha";
  private String lastName = "Mulec";
  private Set<DiseaseDTO> diseases =
      new HashSet(Collections.singletonList(new DiseaseDTO("test_disease_name")));
  private PatientDTO dto;
  private Patient patient;

  @Autowired
  protected PatientServiceTest(PatientService service, DiseaseService diseaseService) {
    super(service);
    this.service = service;
    this.diseaseService = diseaseService;
  }

  protected Patient saveEntity() {
    return service.save(createDTO());
  }

  protected PatientDTO createDTO() {
    return new PatientDTO(id, firstName, lastName, diseases);
  }

  protected PatientDTO createAnotherDTO() {
    return new PatientDTO(
        service.count() + 50,
        firstName + "new",
        lastName + "new",
        new HashSet(Collections.singletonList(new DiseaseDTO("another_disease"))));
  }

  @BeforeEach
  private void PatientServiceTestSetup() {
    dto = createDTO();
  }

  @AfterEach
  private void PatientServiceTestCleanup() {
    service.drop();
  }

  @Test
  void createEntity_returnsNewEntity() {
    assertNotNull(service.create());
  }

  @Test
  void givenDTOWithFirstName_setFields_updatesFirstName() {
    patient = saveEntity();
    String newName = "Janez";
    dto.setFirstName(newName);
    Patient updatedPatient = service.setFields(patient, dto);
    assertEquals(newName, updatedPatient.getFirstName());
  }

  @Test
  void givenDTOWithLastName_setFields_updatesLastName() {
    patient = saveEntity();
    String newName = "Novak";
    dto.setLastName(newName);
    Patient updatedPatient = service.setFields(patient, dto);
    assertEquals(newName, updatedPatient.getLastName());
  }

  @Test
  void givenDTOWithDiseases_setFields_updatesDiseases() {
    patient = saveEntity();
    String diseaseName = "test_name";
    Set<DiseaseDTO> newDiseases =
        new HashSet(Collections.singletonList(new DiseaseDTO(diseaseName)));
    dto.setDiseases(newDiseases);
    Patient updatedPatient = service.setFields(patient, dto);
    for (Disease disease : updatedPatient.getDiseases())
      assertEquals(diseaseName, disease.getName());
  }

  @Test
  void givenDTOWithDiseases_savesDiseases() {
    service.drop();
    diseaseService.drop();
    Set<DiseaseDTO> diseaseDTOs = new HashSet(diseases);
    diseaseDTOs.add(new DiseaseDTO("good_at_testing"));
    dto.setDiseases(diseaseDTOs);

    long expectedNum = diseaseService.count() + dto.getDiseases().size();

    service.save(dto);

    assertEquals(expectedNum, diseaseService.count());
  }
}
