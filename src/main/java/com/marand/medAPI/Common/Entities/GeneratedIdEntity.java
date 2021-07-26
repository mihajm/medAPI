package com.marand.medAPI.Common.Entities;

import com.marand.medAPI.Common.Objects.BaseDataObject;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class GeneratedIdEntity extends BaseDataObject {
  @Id @GeneratedValue private long id;

  public GeneratedIdEntity() {}

  protected GeneratedIdEntity(long id) {
    setId(id);
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
