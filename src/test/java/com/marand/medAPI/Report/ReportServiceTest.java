package com.marand.medAPI.Report;

import com.marand.medAPI.Common.Services.FinderServiceTest;
import com.marand.medAPI.Disease.Disease;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ReportServiceTest extends FinderServiceTest<Report> {

  String entityName = Disease.class.getName();
  long entityId = 10L;
  String message = "message";
  private ReportService service;
  private Report report = new Report(entityName, entityId, message);

  @Autowired
  protected ReportServiceTest(ReportService service) {
    super(service);
    this.service = service;
  }

  @Override
  protected Report saveEntity() {
    return service.save(report);
  }

  @AfterEach
  private void ReportServiceTestCleanup() {
    service.drop();
  }

  @Test
  void create_returnsNewReport() {
    assertNotNull(service.create().getStartTime());
  }
}
