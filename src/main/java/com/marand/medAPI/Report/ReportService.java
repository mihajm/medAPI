package com.marand.medAPI.Report;

import com.marand.medAPI.Common.Services.FinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportService extends FinderService<Report> {
  private ReportRepository repo;

  @Autowired
  protected ReportService(ReportRepository repo) {
    super(repo);
    this.repo = repo;
  }

  protected Report create() {
    return new Report();
  }

  @Reported
  @Transactional
  public List<Report> findByMethodName(String methodName) {
    return repo.findByMethodName(methodName);
  }
}
