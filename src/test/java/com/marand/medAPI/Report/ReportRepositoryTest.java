package com.marand.medAPI.Report;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReportRepositoryTest {

  @Autowired
  ReportRepository repo;
  private String methodName = "count";
  private Report report = new Report(methodName);

  @Test
  void findByName_returnsOptional() {
    assertNotNull(repo.findByMethodName(methodName));
  }

  @Test
  void ifReportExists_findByMethodName_returnsReportList() {
    repo.save(report);
    assertTrue(repo.findByMethodName(methodName).size() > 0);
  }

  @Test
  void ifReportDoesNotExist_findByName_returnsEmptyList() {
    assertTrue(repo.findByMethodName(methodName).isEmpty());
  }

  @Test
  void ifMultipleReportsExist_returnsAllReportsMatchingMethodName() {
    Report report1 = repo.save(new Report(methodName));
    Report report2 = repo.save(new Report(methodName));
    Report report3 = repo.save(new Report());

    List<Report> reports = repo.findByMethodName(methodName);

    assertTrue(reports.contains(report1));
    assertTrue(reports.contains(report2));
    assertFalse(reports.contains(report3));

  }

  @Test
  void whenSavedIgnoresGivenIdAndGeneratesUniqueOne() {
    long id = 500L;
    Report report = new Report(methodName);
    report.setId(id);
    assertNotEquals(id, repo.save(report).getId());
  }

  @Test
  void whenSavedGeneratesUniqueId() {
    Report report1 = repo.save(new Report());
    Report report2 = repo.save(new Report());
    assertNotEquals(report1.getId(), report2.getId());
  }
}
