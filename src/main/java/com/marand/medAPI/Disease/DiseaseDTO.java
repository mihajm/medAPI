package com.marand.medAPI.Disease;

import com.marand.medAPI.Common.DTOs.BaseDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class DiseaseDTO extends BaseDTO {

  @NotBlank(message = "Disease name cannot be blank, empty or null")
  @Size(
      min = 1,
      max = 255,
      message = "Disease name must be longer than 1 and shorter than 255 characters")
  @Pattern(
      regexp = "^[a-z_-]+$",
      message =
          "Disease name can only contain lowercase letters with words separated with either an underscore _ or a dash -")
  private String name = "";

  protected DiseaseDTO() {}

  protected DiseaseDTO(long id) {
    super(id);
  }

  public DiseaseDTO(String name) {
    setName(name);
  }

  protected DiseaseDTO(long id, String name) {
    super(id);
    setName(name);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
