package com.marand.medAPI.Report;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
  List<Report> findByMethodName(String methodName);
}
