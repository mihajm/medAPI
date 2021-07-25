package com.marand.medAPI.Doctor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marand.medAPI.Common.Entities.BaseEntityTest;
import com.marand.medAPI.Disease.Disease;
import com.marand.medAPI.Patient.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DoctorTest extends BaseEntityTest {

  private final long id = 50L;
  private final String department = "marand";
  private Doctor doctor = createEntity();
  private Set<Patient> patients;

  protected Doctor createEntity() {
    patients = new HashSet(Collections.singletonList(new Patient(55L, "Miha", "Mulec", new HashSet(Collections.singletonList(new Disease("a_disease"))))));
    return new Doctor(id, department, patients);
  }

  @BeforeEach
  private void DoctorTestSetup() {
    doctor = createEntity();
  }

  @Test
  void canBeConstructedWithNoArgs() {
    assertDoesNotThrow((ThrowingSupplier<Doctor>) Doctor::new);
  }

  @Test
  void canBeConstructedWithIdAndDepartment() {
    assertDoesNotThrow(() -> new Doctor(id, department));
  }

  @Test
  void whenConstructedWithIdAndDepartment_hasIdAndDepartment() {
    Doctor doctor = new Doctor(id, department);
    assertEquals(id, doctor.getId());
    assertEquals(department, doctor.getDepartment());
  }

  @Test
  void canBeConstructedWithIdAndDepartmentAndPatients() {
    assertDoesNotThrow(this::createEntity);
  }

  @Test
  void whenConstructedWithIdAndDepartmentAndPatients_hasIdAndDepartmentAndPatients() {
    assertEquals(id, doctor.getId());
    assertEquals(department, doctor.getDepartment());
    assertEquals(patients, doctor.getPatients());
  }

  @Test
  void getDepartment_getsDepartment() {
    assertEquals(department, doctor.getDepartment());
  }

  @Test
  void hasEmptyPatientsList_whenConstructed() {
    assertEquals(0, new Doctor().getPatients().size());
  }

  @Test
  void setDepartment_setsDepartment() {
    String newDepartment = "marand_crm";
    doctor.setDepartment(newDepartment);
    assertEquals(newDepartment, doctor.getDepartment());
  }

  @Test
  void getPatients_getsPatients() {
    assertEquals(patients, doctor.getPatients());
  }

  @Test
  void setPatients_setsPatients() {
    Set<Patient> newPatients =
        new HashSet(
            Collections.singletonList(
                new Patient(
                    60L,
                    "Janez",
                    "Novak",
                    new HashSet(Collections.singletonList(new Disease("a_disease"))))));
    doctor.setPatients(newPatients);
    assertEquals(newPatients, doctor.getPatients());
  }

  @Test
  void settingNullPatients() throws IllegalArgumentException {
    assertThrows(IllegalArgumentException.class, () -> doctor.setPatients(null));
  }

  @Test
  void addPatient_addsPatient() {
    Patient patient = new Patient(60L, "Janez", "Novak");
    doctor.addPatient(patient);
    assertTrue(doctor.getPatients().contains(patient));
  }

  @Test
  void addingNullPatient() throws IllegalArgumentException {
    assertThrows(IllegalArgumentException.class, () -> doctor.addPatient(null));
  }

  @Test
  void removePatient_removesPatient() {
    Patient patient = new Patient(60L, "Janez", "Novak");
    doctor.addPatient(patient);
    doctor.removePatient(patient);
    assertFalse(doctor.getPatients().contains(patient));
  }

  @Test
  void givenUppercaseCharacters_WhenSettingDepartment_ConvertsToLowerCased() {
    String department = "UPPER_CASED";
    doctor.setDepartment(department);
    assertEquals(department.toLowerCase(Locale.ROOT), doctor.getDepartment());
  }

  @Test
  void whenSettingNullDepartment() throws IllegalArgumentException {
    assertThrows(IllegalArgumentException.class, () -> doctor.setDepartment(null));
  }

  @Test
  void whenSettingEmptyName() throws IllegalArgumentException {
    assertThrows(IllegalArgumentException.class, () -> doctor.setDepartment(""));
  }

  @Test
  void whenSettingTooLongName() throws IllegalArgumentException {
    assertThrows(IllegalArgumentException.class, () -> doctor.setDepartment("a".repeat(260)));
  }

  @Test
  void whenSettingNameWithIllegalChars() throws IllegalArgumentException {
    assertThrows(IllegalArgumentException.class, () -> doctor.setDepartment("123"));
  }

  @Test
  void isSerializable() {
    ObjectMapper mapper = new ObjectMapper();
    assertTrue(mapper.canSerialize(Doctor.class));
  }

  @Test
  void containsNameWhenSerialized() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String objectString = mapper.writeValueAsString(doctor);
    assertTrue(objectString.contains(department));
  }

  @Test
  void isDeSerializable() {
    ObjectMapper mapper = new ObjectMapper();
    assertTrue(mapper.canDeserialize(mapper.constructType(Doctor.class)));
  }

  @Test
  void containsNameWhenDeSerialized() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String objectString = mapper.writeValueAsString(doctor);
    Doctor entity = mapper.readValue(objectString, Doctor.class);
    assertEquals(department, entity.getDepartment());
  }
}
