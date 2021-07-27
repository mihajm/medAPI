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

  @Test
  void whenFindByMethodNameCalled_findByMethodNameCallReported_withMethodName() {
    service.saveOne(new Report("method"));
    service.findByMethodName("method");
    assertEquals(1, service.findByMethodName("findByMethodName").size());
  }

  @Test
  void whenFindByMethodNameCalled_findByMethodNameCallReported_withReturnedEntities() {
    Report savedReport = service.saveOne(new Report("method"));
    Report anotherReport = service.saveOne(new Report("method"));

    service.findByMethodName("method");

    Report report = service.findByMethodName("findByMethodName").get(0);

    assertTrue(report.getEntities().containsKey(savedReport.getId()));
    assertEquals(savedReport.getClass().getName(), report.getEntities().get(savedReport.getId()));

    assertTrue(report.getEntities().containsKey(anotherReport.getId()));
    assertEquals(
            anotherReport.getClass().getName(), report.getEntities().get(anotherReport.getId()));
  }
}
