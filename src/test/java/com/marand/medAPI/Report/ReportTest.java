package com.marand.medAPI.Report;

import com.marand.medAPI.Common.Entities.GeneratedIdEntityTest;
import com.marand.medAPI.Disease.Disease;
import com.marand.medAPI.Doctor.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest extends GeneratedIdEntityTest {

  String entityName = "Disease";
  long entityId = 5L;
  String message = "message";
  Report report = createEntity();

  @BeforeEach
  private void ReportTestSetup() {
    report = createEntity();
  }

  private Report createEntity() {
    return new Report(entityName, entityId, message);
  }

  @Test
  void canBeConstructedWithoutArgs() {
    assertDoesNotThrow((ThrowingSupplier<Report>) Report::new);
  }

  @Test
  void canBeConstructedAllArgs() {
    assertDoesNotThrow(this::createEntity);
  }

  @Test
  void canBeConstructedWithEntityAsArgs() {
    assertDoesNotThrow(() -> new Report(new Disease(50L, "a_disease"), message));
  }

  @Test
  void whenConstructedHasStartTime() {
    assertNotNull(report.getStartTime());
  }

  @Test
  void whenConstructedWithEntityHasCorrectIdAndName() {
    Disease disease = new Disease (50L, "a_disease");

    Report report = new Report(disease, message);

    assertEquals(disease.getId(), report.getEntityId());
    assertTrue(report.getEntityName().contains("Disease"));
    assertEquals(message, report.getMessage());
  }

  @Test
  void getStartTime_getsStartTime() {
    assertNotNull(report.getStartTime());
  }

  @Test
  void getEntityName() {
    assertEquals(entityName, report.getEntityName());
  }

  @Test
  void setEntityName() {
    String entityName = "Patient";
    report.setEntityName(entityName);
    assertEquals(entityName, report.getEntityName());
  }

  @Test
  void getEntityId() {
    assertEquals(entityId, report.getEntityId());
  }

  @Test
  void setEntityId() {
    long entityId = 55L;
    report.setEntityId(entityId);
    assertEquals(entityId, report.getEntityId());
  }

  @Test
  void getMessage() {
    assertEquals(message, report.getMessage());
  }

  @Test
  void setMessage() {
    String message = "newMessage";
    report.setMessage(message);
    assertEquals(message, report.getMessage());
  }
}
