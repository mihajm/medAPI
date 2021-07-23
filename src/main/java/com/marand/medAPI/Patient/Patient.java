package com.marand.medAPI.Patient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marand.medAPI.Common.Entities.BaseEntity;
import com.marand.medAPI.Common.Validators.StringValidator;
import com.marand.medAPI.Disease.Disease;
import com.marand.medAPI.Doctor.Doctor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Patient extends BaseEntity {

  private String firstName;

  private String lastName;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(
      name = "patient_disease",
      joinColumns = @JoinColumn(name = "patient_id"),
      inverseJoinColumns = @JoinColumn(name = "disease_id"))
  private Set<Disease> diseases = new HashSet();

  @JsonIgnore
  @ManyToMany(fetch = FetchType.EAGER)
  private Set<Doctor> doctors = new HashSet();

  public Patient() {}

  protected Patient(long id) {
    super(id);
  }

  public Patient(long id, String firstName, String lastName) {
    super(id);
    setFirstName(firstName);
    setLastName(lastName);
  }

  public Patient(long id, String firstName, String lastName, Set<Disease> diseases) {
    super(id);
    setFirstName(firstName);
    setLastName(lastName);
    setDiseases(diseases);
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = new StringValidator(firstName).validatePatientName();
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = new StringValidator(lastName).validatePatientName();
  }

  public Set<Disease> getDiseases() {
    return diseases;
  }

  public void setDiseases(Set<Disease> diseases) {
    throwIfNull(diseases);
    this.diseases = diseases;
  }

  public void addDisease(Disease disease) {
    throwIfNull(disease);
    diseases.add(disease);
  }

  public void removeDisease(Disease disease) {
    diseases.remove(disease);
  }

  private void throwIfNull(Object object) {
    if (object == null) throw new IllegalArgumentException("Diseases can't be null");
  }
}
