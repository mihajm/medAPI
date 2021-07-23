package com.marand.medAPI.Report;

import com.marand.medAPI.Common.Entities.GeneratedIdEntity;
import com.marand.medAPI.Common.Objects.BaseDataObject;
import com.marand.medAPI.Disease.Disease;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "document_report")
public class Report extends GeneratedIdEntity {
  private Date startTime;
  private String entityName;
  private long entityId;
  private String message;

  public Report() {
    startTime = new Date();
  }

  public Report(String entityName, long entityId, String message) {
    startTime = new Date();
    setEntityName(entityName);
    setEntityId(entityId);
    setMessage(message);
  }

  public Report(BaseDataObject entity, String message) {
    setEntityId(entity.getId());
    setEntityName(entity.getClass().getName());
    setMessage(message);
  }

  public Date getStartTime() {
    return startTime;
  }

  public String getEntityName() {
    return entityName;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  public long getEntityId() {
    return entityId;
  }

  public void setEntityId(long entityId) {
    this.entityId = entityId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
