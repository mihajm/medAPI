package com.marand.medAPI.Report;

import com.marand.medAPI.Common.Objects.BaseDataObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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

  @Around("@annotation(Reported)")
  public Object report(ProceedingJoinPoint joinPoint) throws Throwable {
    return reportSuccessOrFailure(joinPoint, createReport(getMethodName(joinPoint)));
  }

  private Object reportSuccessOrFailure(ProceedingJoinPoint joinPoint, Report report)
      throws Throwable {
    try {
      return reportSuccess(joinPoint, report);
    } catch (Throwable e) {
      reportFailure(e, report);
      throw e;
    } finally {
      service.saveOne(report);
    }
  }

  private String getMethodName(ProceedingJoinPoint joinPoint) {
    return joinPoint.getSignature().getName();
  }

  private Report createReport(String methodName) {
    return new Report(methodName);
  }

  @SuppressWarnings("unchecked")
  private Object reportSuccess(ProceedingJoinPoint joinPoint, Report report) throws Throwable {
    Object retVal = joinPoint.proceed();
    if (isEntityCollection(retVal))
      report.setEntities(List.copyOf((Collection<? extends BaseDataObject>) retVal));
    if (isEntity(retVal)) report.addEntity((BaseDataObject) retVal);
    return retVal;
  }

  private void reportFailure(Throwable e, Report report) {
    report.setException(exceptionClassAndMessage(e));
  }

  private Pair<Class<? extends Throwable>, String> exceptionClassAndMessage(Throwable e) {
    return new ImmutablePair<Class<? extends Throwable>, String>(e.getClass(), e.getMessage());
  }
}
