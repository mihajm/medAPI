package com.marand.medAPI.Report;

import com.marand.medAPI.Common.Objects.BaseDataObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static com.marand.medAPI.Common.Validators.FieldValidator.isEntity;
import static com.marand.medAPI.Common.Validators.FieldValidator.isEntityCollection;

@Aspect
@Component
public class ReportAspect {

  @Autowired private ReportService service;

  @AfterReturning(value = "@annotation(Reported)", returning = "returnValue")
  public void report(JoinPoint joinPoint, Object returnValue) {
    reportSuccess(returnValue, createReport(getMethodName(joinPoint)));
  }

  @AfterThrowing(value = "@annotation(Reported)", throwing = "ex")
  public void reportThrow(JoinPoint joinPoint, Throwable ex) {
    reportFailure(ex, createReport(getMethodName(joinPoint)));
  }

  private String getMethodName(JoinPoint joinPoint) {
    return joinPoint.getSignature().getName();
  }

  private Report createReport(String methodName) {
    return new Report(methodName);
  }

  @SuppressWarnings("unchecked")
  private void reportSuccess(Object retVal, Report report) {
    if (isEntityCollection(retVal))
      report.setEntities(List.copyOf((Collection<? extends BaseDataObject>) retVal));

    if (isEntity(retVal)) report.addEntity((BaseDataObject) retVal);

    service.saveOne(report);
  }

  private void reportFailure(Throwable e, Report report) {
    report.setException(
        new ImmutablePair<Class<? extends Throwable>, String>(e.getClass(), e.getMessage()));
    service.saveOne(report);
  }
}
