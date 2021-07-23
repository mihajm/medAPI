package com.marand.medAPI.Patient;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.marand.medAPI.Common.Annotations.ReflectWith;
import com.marand.medAPI.Common.DTOs.BaseDTO;
import com.marand.medAPI.Disease.DiseaseDTO;
import com.marand.medAPI.Disease.DiseaseService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

public class PatientDTO extends BaseDTO {

  @JsonAlias("first_name")
  @NotBlank(message = "First name cannot be blank, empty or null")
  @Size(
      min = 1,
      max = 255,
      message = "First name must be longer than 1 and shorter than 255 characters")
  @Pattern(
      regexp = "^[A-Z][a-z]+$",
      message =
          "A first names first character must be an uppercase letter character, other characters must be lowercase letters with no separation")
  private String firstName;

  @JsonAlias("last_name")
  @NotBlank(message = "Last name cannot be blank, empty or null")
  @Size(
      min = 1,
      max = 255,
      message = "Last name must be longer than 1 and shorter than 255 characters")
  @Pattern(
      regexp = "^[A-Z][a-z]+$",
      message =
          "A last names first character must be an uppercase letter character, other characters must be lowercase letters with no separation")
  private String lastName;

  @NotNull(
      message = "A patients diseases cannot be null, did you maybe want to set an empty array?")
  @Valid
  @ReflectWith(DiseaseService.class)
  private Set<DiseaseDTO> diseases = new HashSet();

  protected PatientDTO() {}

  protected PatientDTO(long id) {
    super(id);
  }

  public PatientDTO(long id, String firstName, String lastName) {
    super(id);
    setFirstName(firstName);
    setLastName(lastName);
  }

  public PatientDTO(long id, String firstName, String lastName, Set<DiseaseDTO> diseases) {
    super(id);
    setFirstName(firstName);
    setLastName(lastName);
    setDiseases(diseases);
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Set<DiseaseDTO> getDiseases() {
    return diseases;
  }

  public void setDiseases(Set<DiseaseDTO> diseases) {
    this.diseases = diseases;
  }
}
