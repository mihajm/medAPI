package com.marand.medAPI.Doctor;

import com.marand.medAPI.Common.Annotations.ReflectWith;
import com.marand.medAPI.Common.DTOs.BaseDTO;
import com.marand.medAPI.Patient.PatientDTO;
import com.marand.medAPI.Patient.PatientService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class DoctorDTO extends BaseDTO {

  @NotBlank(message = "Department cannot be blank, empty or null")
  @Size(
      min = 1,
      max = 255,
      message = "Department must be longer than 1 and shorter than 255 characters")
  @Pattern(
      regexp = "^[a-z_-]+$",
      message =
          "Department can only contain lowercase letters with words separated with either an underscore _ or a dash -")
  private String department;

  @NotNull(message = "A doctors patients cannot be null, did you maybe want to set an empty array?")
  @Valid
  @ReflectWith(PatientService.class)
  private Set<PatientDTO> patients = new HashSet();

  protected DoctorDTO() {}

  protected DoctorDTO(long id) {
    super(id);
  }

  protected DoctorDTO(long id, String department) {
    super(id);
    setDepartment(department);
  }

  public DoctorDTO(long id, String department, Set<PatientDTO> patients) {
    super(id);
    setDepartment(department);
    setPatients(patients);
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public Set<PatientDTO> getPatients() {
    return patients;
  }

  public void setPatients(Set<PatientDTO> patients) {
    this.patients = patients;
  }
}
