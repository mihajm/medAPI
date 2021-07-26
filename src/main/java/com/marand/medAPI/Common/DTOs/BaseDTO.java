package com.marand.medAPI.Common.DTOs;

import com.marand.medAPI.Common.Objects.BaseDataObject;

public class BaseDTO extends BaseDataObject {

  private long id = -1;

  public BaseDTO() {}

  protected BaseDTO(long id) {
    setId(id);
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
