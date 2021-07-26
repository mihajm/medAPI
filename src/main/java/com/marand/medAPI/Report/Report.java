package com.marand.medAPI.Report;

import com.marand.medAPI.Common.Entities.GeneratedIdEntity;
import com.marand.medAPI.Common.Objects.BaseDataObject;
import org.apache.commons.lang3.tuple.Pair;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "document_report")
public class Report extends GeneratedIdEntity {
  private Date startTime = new Date();
  private HashMap<Long, String> entities = new HashMap<Long, String>();
  private String methodName;
  private Pair<Class<? extends Throwable>, String> exception;

  public Report() {
    startTime = new Date();
  }

  public Report(String methodName) {
    setMethodName(methodName);
  }

  public Report(HashMap<Long, String> entities, String methodName, Pair<Class<? extends Throwable>, String> exception) {
    startTime = new Date();
    setEntities(entities);
    setMethodName(methodName);
    setException(exception);
  }

  public Report(List<BaseDataObject> entities, String methodName, Pair<Class<? extends Throwable>, String> exception) {
    setEntities(entities);
    setMethodName(methodName);
    setException(exception);
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public HashMap<Long, String> getEntities() {
    return entities;
  }

  public void setEntities(HashMap<Long, String> entities) {
    this.entities = entities;
  }

  public void setEntities(List<BaseDataObject> entities) {
    entities.forEach(this::addEntity);
  }

  public void addEntity(BaseDataObject entity) {
    entities.put(entity.getId(), entity.getClass().getName());
  }

  public Pair<Class<? extends Throwable>, String> getException() {
    return exception;
  }

  public void setException(Pair<Class<? extends Throwable>, String> exception) {
    this.exception = exception;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }
}
