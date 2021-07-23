package com.marand.medAPI.Common.Objects;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;

public abstract class BaseDataObject {

  protected BaseDataObject() {}

  public abstract long getId();

  public abstract void setId(long id);

  @Override
  public boolean equals(Object other) {
    if (this.getClass() != other.getClass()) return false;
    return reflectionEquals(this, other);
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }
}
