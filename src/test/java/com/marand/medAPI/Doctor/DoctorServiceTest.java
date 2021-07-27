package com.marand.medAPI.Doctor;

import com.marand.medAPI.Common.Services.ReportedServiceTest;
import com.marand.medAPI.Disease.Disease;
import com.marand.medAPI.Disease.DiseaseDTO;
import com.marand.medAPI.Disease.DiseaseService;
import com.marand.medAPI.Patient.Patient;
import com.marand.medAPI.Patient.PatientDTO;
import com.marand.medAPI.Patient.PatientService;
import com.marand.medAPI.Report.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DoctorServiceTest extends ReportedServiceTest<Doctor, DoctorDTO> {

  private DoctorService service;

  @Autowired private PatientService patientService;

  @Autowired private DiseaseService diseaseService;

  private long id = 50L;
  private String department = "marand";
  private Doctor doctor;
  private DoctorDTO dto;

  @Autowired
  protected DoctorServiceTest(DoctorService service, ReportService reportService) {
    super(service, reportService);
    this.service = service;
  }

  protected Doctor saveEntity() {
    return service.save(createDTO());
  }

  protected DoctorDTO createDTO() {
    return new DoctorDTO(
        id,
        department,
        new HashSet(
            Collections.singletonList(
                new PatientDTO(
                    55L,
                    "Miha",
                    "Mulec",
                    new HashSet(Collections.singletonList(new DiseaseDTO("a_disease")))))));
  }

  @Override
  protected DoctorDTO createAnotherDTO() {
    return new DoctorDTO(
        id + 10,
        department + "new",
        new HashSet(
            Collections.singletonList(
                new PatientDTO(
                    65L,
                    "Janez",
                    "Novak",
                    new HashSet(Collections.singletonList(new DiseaseDTO("another_disease")))))));
  }

  @BeforeEach
  void DoctorServiceTestSetup() {
    dto = createDTO();
    service.drop();
  }

  @AfterEach
  private void DoctorServiceTestCleanup() {
    service.drop();
  }

  @Test
  void createEntity_returnsNewEntity() {
    assertNotNull(service.create());
  }

  @Test
  void givenDTOWithDepartment_setFields_updatesDepartment() {
    doctor = saveEntity();
    String newDepartment = "marand_crm";
    dto.setDepartment(newDepartment);
    Doctor updatedDoctor = service.setFields(doctor, dto);
    assertEquals(newDepartment, updatedDoctor.getDepartment());
  }

  @Test
  void givenDTOWithDiseases_setFields_updatesPatients() {
    doctor = saveEntity();
    String diseaseName = "new_disease";
    long id = 500L;
    String firstName = "Janez";
    String lastName = "Novak";
    Set<PatientDTO> newPatients =
        new HashSet(
            Collections.singletonList(
                new PatientDTO(
                    id,
                    firstName,
                    lastName,
                    new HashSet(Collections.singletonList(new DiseaseDTO(diseaseName))))));
    dto.setPatients(newPatients);
    Doctor updatedDoctor = service.setFields(doctor, dto);
    for (Patient patient : updatedDoctor.getPatients()) {
      assertEquals(id, patient.getId());
      assertEquals(firstName, patient.getFirstName());
      assertEquals(lastName, patient.getLastName());
      for (Disease disease : patient.getDiseases()) assertEquals(diseaseName, disease.getName());
    }
  }

  @Test
  void givenDTOWithPatients_savesPatients() {
    service.drop();
    patientService.drop();
    long expectedNum = patientService.count() + dto.getPatients().size();
    saveEntity();
    assertEquals(expectedNum, patientService.count());
  }

  @Test
  void givenDTOWithDiseases_savesDiseases() {
    service.drop();
    patientService.drop();
    diseaseService.drop();

    Set<DiseaseDTO> diseases =
        new HashSet(Arrays.asList(new DiseaseDTO("a_disease"), new DiseaseDTO("good_at_code")));
    Set<PatientDTO> patients = new HashSet(Set.of(new PatientDTO(50L, "Miha", "Mulec", diseases)));
    dto.setPatients(patients);

    long expectedNum = diseaseService.count() + 2;

    service.save(dto);

    assertEquals(expectedNum, diseaseService.count());
  }
}
