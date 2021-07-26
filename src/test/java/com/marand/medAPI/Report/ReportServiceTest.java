package com.marand.medAPI.Report;

import com.marand.medAPI.Common.Services.FinderServiceTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportServiceTest extends FinderServiceTest<Report> {

  private ReportService service;
  private String methodName = "count";

  @Autowired
  protected ReportServiceTest(ReportService service) {
    super(service);
    this.service = service;
  }

  @AfterEach
  private void reportServiceTestSetup() {
    service.drop();
  }

  protected Report saveEntity() {
    return service.saveOne(service.create());
  }

  @Test
  void create_ReturnsNewReport() {
    assertNotNull(service.create());
  }

  @Test
  void findByMethodName_returnsListWithEntitiesWithMatchingMethodName() {
    Report report1 = service.saveOne(new Report(methodName));
    Report report2 = service.saveOne(new Report(methodName));
    Report report3 = service.saveOne(new Report());

    List<Report> reports = service.findByMethodName(methodName);

    assertEquals(2, reports.size());
    assertTrue(reports.contains(report1));
    assertTrue(reports.contains(report2));
    assertFalse(reports.contains(report3));
  }

  @Test
  void findByMethodName_returnsEmptyListWhenDBEmpty() {
    assertTrue(service.findByMethodName(methodName).isEmpty());
  }
}
