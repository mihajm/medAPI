package com.marand.medAPI.Doctor;

import com.marand.medAPI.Common.Entities.BaseEntity;
import com.marand.medAPI.Common.Validators.StringValidator;
import com.marand.medAPI.Patient.Patient;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Doctor extends BaseEntity {

  private String department;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(
      name = "doctor_patient",
      joinColumns = @JoinColumn(name = "doctor_id"),
      inverseJoinColumns = @JoinColumn(name = "patient_id"))
  private Set<Patient> patients = new HashSet();

  protected Doctor() {}

  protected Doctor(long id) {
    super(id);
  }

  protected Doctor(long id, String department) {
    super(id);
    setDepartment(department);
  }

  protected Doctor(long id, String department, Set<Patient> patients) {
    super(id);
    setDepartment(department);
    setPatients(patients);
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = new StringValidator(department).validateDepartment();
  }

  public Set<Patient> getPatients() {
    return patients;
  }

  public void setPatients(Set<Patient> patients) {
    throwIfNull(patients);
    this.patients = patients;
  }

  public void addPatient(Patient patient) {
    throwIfNull(patient);
    patients.add(patient);
  }

  public void removePatient(Patient patient) {
    patients.remove(patient);
  }

  private void throwIfNull(Object object) {
    if (object == null) throw new IllegalArgumentException("Patients can't be null");
  }
}
