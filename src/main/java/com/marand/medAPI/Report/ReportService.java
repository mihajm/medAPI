package com.marand.medAPI.Report;

import com.marand.medAPI.Common.Services.FinderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService extends FinderService<Report> {
  private ReportRepository repo;

  protected ReportService(ReportRepository repo) {
    super(repo);
    this.repo = repo;
  }

  protected Report create() {
    return new Report();
  }

  public List<Report> findByMethodName(String methodName) {
    return repo.findByMethodName(methodName);
  }

}
