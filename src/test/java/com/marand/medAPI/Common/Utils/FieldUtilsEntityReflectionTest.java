package com.marand.medAPI.Common.Utils;

import com.marand.medAPI.Common.Services.UpdaterService;
import com.marand.medAPI.Disease.Disease;
import com.marand.medAPI.Disease.DiseaseDTO;
import com.marand.medAPI.Disease.DiseaseService;
import com.marand.medAPI.Doctor.Doctor;
import com.marand.medAPI.Doctor.DoctorDTO;
import com.marand.medAPI.Doctor.DoctorService;
import com.marand.medAPI.Patient.Patient;
import com.marand.medAPI.Patient.PatientDTO;
import com.marand.medAPI.Patient.PatientRepository;
import com.marand.medAPI.Patient.PatientService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class FieldUtilsEntityReflectionTest {

  @Autowired DiseaseService diseaseService;

  @Autowired PatientService patientService;

  @Autowired DoctorService doctorService;

  @AfterEach
  private void FieldUtilsEntityReflectionTestCleanup() {
    doctorService.drop();
    patientService.drop();
    doctorService.drop();
  }

  @Test
  void givenPatientDTOWithDiseaseDTO_reflectsDiseaseDTOtoDisease() {
    doctorService.drop();
    String name = "a_disease";
    Set<DiseaseDTO> diseases = new HashSet(Collections.singletonList(new DiseaseDTO(name)));
    PatientDTO patientDTO = new PatientDTO(50L, "Miha", "Mulec", diseases);
    Patient patient = patientService.save(patientDTO);
    patient
        .getDiseases()
        .forEach(
            (disease) -> {
              assertNotNull(disease);
              assertEquals(name, disease.getName());
            });
  }

  @Test
  void givenDoctorDTOWithPatientDTO_reflectsPatientDTOtoPatient() {
    doctorService.drop();
    long id = 55L;
    String firstName = "Miha";
    String lastName = "Mulec";
    Set<PatientDTO> patientDTOs =
        new HashSet(Collections.singletonList(new PatientDTO(id, firstName, lastName)));
    DoctorDTO doctorDTO = new DoctorDTO(100L, "marand", patientDTOs);
    Doctor doctor = doctorService.save(doctorDTO);
    doctor
        .getPatients()
        .forEach(
            (patient) -> {
              assertNotNull(patient);
              assertEquals(id, patient.getId());
              assertEquals(firstName, patient.getFirstName());
              assertEquals(lastName, patient.getLastName());
            });
  }
}
