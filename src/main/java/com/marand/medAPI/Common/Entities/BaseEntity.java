package com.marand.medAPI.Common.Entities;

import com.marand.medAPI.Common.Objects.BaseDataObject;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity extends BaseDataObject {
  @Id private long id;

  public BaseEntity() {}

  public BaseEntity(long id) {
    setId(id);
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
