package com.marand.medAPI.Common.Services;

import com.marand.medAPI.Common.DTOs.BaseDTO;
import com.marand.medAPI.Common.Objects.BaseDataObject;
import com.marand.medAPI.Report.Report;
import com.marand.medAPI.Report.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public abstract class ReportedServiceTest<E extends BaseDataObject, DTO extends BaseDTO>
    extends UpdaterServiceTest<E, DTO> {

  @Autowired private ReportService reportService;

  private ReportedService<E, DTO> service;

  protected ReportedServiceTest(ReportedService<E, DTO> service) {
    super(service);
    this.service = service;
  }

  protected abstract DTO createAnotherDTO();

  @BeforeEach
  private void ReportedServiceTestSetup() {
    service.drop();
    reportService.drop();
  }

  @AfterEach
  private void ReportedServiceTestCleanup() {
    service.drop();
    reportService.drop();
  }

  @Test
  void whenCountCalled_countCallReported_withMethodName() {
    service.count();
    assertEquals(1, reportService.findByMethodName("count").size());
  }

  @Test
  void whenFindAllCalled_findAllCallReported_withMethodName() {
    service.findAll();
    assertEquals(1, reportService.findByMethodName("findAll").size());
  }

  @Test
  void whenFindAllCalled_findAllCallReported_withReturnedEntities() {
    E entity = saveEntity();
    E anotherEntity = service.save(createAnotherDTO());

    service.findAll();

    Report report = reportService.findByMethodName("findAll").get(0);

    assertTrue(report.getEntities().containsKey(entity.getId()));
    assertEquals(entity.getClass().getName(), report.getEntities().get(entity.getId()));

    assertTrue(report.getEntities().containsKey(anotherEntity.getId()));
    assertEquals(
        anotherEntity.getClass().getName(), report.getEntities().get(anotherEntity.getId()));
  }

  @Test
  void whenFindOneCalled_findOneReported_withMethodName() {
    E entity = saveEntity();
    service.findOne(entity.getId());
    assertEquals(1, reportService.findByMethodName("findOne").size());
  }

  @Test
  void whenFindOneCalledOnValidEntity_findOneCallReported_withReturnedEntity() {
    E entity = saveEntity();

    service.findOne(entity.getId());

    Report report = reportService.findByMethodName("findOne").get(0);

    assertEquals(1, report.getEntities().size());
    assertTrue(report.getEntities().containsKey(entity.getId()));
    assertEquals(entity.getClass().getName(), report.getEntities().get(entity.getId()));
  }

  @Test
  void whenFindOneCalledOnInValidEntity_isThrownAndReported() {

    assertThrows(
        EntityNotFoundException.class,
        () -> {
          service.findOne(5999L);
          assertEquals(1, reportService.count());
        });
  }

  @Test
  void whenFindOneCalledOnInValidEntity_findOneCallReported_withThrownNotFoundException() {

    assertThrows(
        EntityNotFoundException.class,
        () -> {
          service.findOne(5999L);
          Report report = reportService.findByMethodName("findOne").get(0);
          assertEquals(EntityNotFoundException.class, report.getException().getLeft());
        });
  }

  @Test
  void whenSaveCalled_saveReported_withMethodName() {
    saveEntity();
    System.out.println(reportService.findByMethodName("save"));
    assertEquals(1, reportService.findByMethodName("save").size());
  }

  @Test
  void whenValidSaveCalled_saveReported_withSavedEntity() {
    E entity = saveEntity();

    Report report = reportService.findByMethodName("save").get(0);

    assertTrue(report.getEntities().containsKey(entity.getId()));
    assertEquals(entity.getClass().getName(), report.getEntities().get(entity.getId()));
  }

  @Test
  void whenRemoveCalled_removeReported_withMethodName() {
    E entity = saveEntity();
    service.remove(entity.getId());
    assertEquals(1, reportService.findByMethodName("remove").size());
  }

}
