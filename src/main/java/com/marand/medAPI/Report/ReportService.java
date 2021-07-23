package com.marand.medAPI.Report;

import com.marand.medAPI.Common.Services.FinderService;
import org.springframework.stereotype.Service;

@Service
public class ReportService extends FinderService<Report> {

  protected ReportService(ReportRepository repo) {
    super(repo);
  }

  protected Report create() {
    return new Report();
  }
}
