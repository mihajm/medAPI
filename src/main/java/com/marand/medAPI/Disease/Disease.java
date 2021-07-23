package com.marand.medAPI.Disease;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marand.medAPI.Common.Entities.GeneratedIdEntity;
import com.marand.medAPI.Common.Validators.StringValidator;
import com.marand.medAPI.Patient.Patient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Disease extends GeneratedIdEntity {

  @Column(unique = false, nullable = false)
  private String name = "";

  @JsonIgnore
  @ManyToMany(fetch = FetchType.EAGER)
  private Set<Patient> patients = new HashSet();

  public Disease() {}

  public Disease(String name) {
    setName(name);
  }

  public Disease(long id, String name) {
    super(id);
    setName(name);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = new StringValidator(name).validateDiseaseName();
  }
}
