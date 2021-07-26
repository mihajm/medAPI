package com.marand.medAPI.Report;

import com.marand.medAPI.Common.Entities.GeneratedIdEntityTest;
import com.marand.medAPI.Disease.Disease;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import javax.persistence.Lob;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest extends GeneratedIdEntityTest {

  private static Date startTime = new Date();
  String entityName = Disease.class.getName();
  Long entityId = 5L;
  HashMap<Long, String> entities = new HashMap<Long, String>(Map.of(entityId, entityName));

  @Lob
  Pair<Class<? extends Throwable>, String> exception =
      new ImmutablePair<Class<? extends Throwable>, String>(RuntimeException.class, "message");
  String methodName = "findOne";

  Report report = new Report(entities, methodName, exception);

  @BeforeEach
  private void ReportTestSetup() {
    report = new Report(entities, methodName, exception);
  }

  protected Report createEntity() {
    Report report = new Report();
    report.setStartTime(startTime);
    return report;
  }

  @Test
  void canBeConstructedWithoutArgs() {
    assertDoesNotThrow((ThrowingSupplier<Report>) Report::new);
  }

  @Test
  void canBeConstructedWithMethodName() {
    assertDoesNotThrow(() -> new Report(methodName));
  }

  @Test
  void whenConstructedWithMethodName_hasMethodName() {
    assertEquals(methodName, new Report(methodName).getMethodName());
  }

  @Test
  void canBeConstructedAllArgs() {
    assertDoesNotThrow(this::createEntity);
  }

  @Test
  void whenConstructedWithAllArgs_hasArgs() {
    assertTrue(report.getEntities().containsKey(entityId));
    assertEquals(entityName, report.getEntities().get(entityId));
    assertEquals(exception, report.getException());
    assertNotNull(report.getStartTime());
  }

  @Test
  void canBeConstructedWithEntityAsArgs() {
    assertDoesNotThrow(
        () -> new Report(List.of(new Disease(50L, "a_disease")), methodName, exception));
  }

  @Test
  void whenConstructedHasStartTime() {
    assertNotNull(report.getStartTime());
  }

  @Test
  void whenConstructedWithEntityHasCorrectIdAndName() {
    Disease disease = new Disease(50L, "a_disease");

    Report report = new Report(List.of(disease), methodName, exception);

    assertTrue(report.getEntities().containsKey(disease.getId()));
    assertEquals(disease.getClass().getName(), report.getEntities().get(disease.getId()));
    assertEquals(exception, report.getException());
  }

  @Test
  void getStartTime_getsStartTime() {
    assertNotNull(report.getStartTime());
  }

  @Test
  void setStartTime_setsStartTime() {
    report.setStartTime(startTime);
    assertEquals(startTime, report.getStartTime());
  }

  @Test
  void getException() {
    assertEquals(exception, report.getException());
  }

  @Test
  void setException() {
    Pair<Class<? extends Throwable>, String> newException =
        new ImmutablePair<Class<? extends Throwable>, String>(RuntimeException.class, "message");
    report.setException(newException);
    assertEquals(newException, report.getException());
  }

  @Test
  void getMethodName_getsMethodName() {
    assertEquals(methodName, report.getMethodName());
  }

  @Test
  void setMethodName_setsMethodName() {
    String newMethodName = "count";
    report.setMethodName(newMethodName);
    assertEquals(newMethodName, report.getMethodName());
  }

  @Test
  void setEntities_setsEntityIdAndName() {
    Disease disease = new Disease(50L, "name");
    report.setEntities(List.of(disease));
    assertTrue(report.getEntities().containsKey(disease.getId()));
    assertEquals(disease.getClass().getName(), report.getEntities().get(disease.getId()));
  }

  @Test
  void addEntity_setsEntityIdAndName() {
    Disease disease = new Disease(50L, "name");
    report.addEntity(disease);
    assertTrue(report.getEntities().containsKey(disease.getId()));
    assertEquals(disease.getClass().getName(), report.getEntities().get(disease.getId()));
  }
}
