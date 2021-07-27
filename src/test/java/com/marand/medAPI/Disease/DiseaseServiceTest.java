package com.marand.medAPI.Disease;

import com.marand.medAPI.Common.Services.ReportedServiceTest;
import com.marand.medAPI.Report.Report;
import com.marand.medAPI.Report.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiseaseServiceTest extends ReportedServiceTest<Disease, DiseaseDTO> {

  private String name = "good_at_tests";
  private DiseaseDTO dto = createDTO();

  private DiseaseService service;
  private ReportService reportService;

  @Autowired
  protected DiseaseServiceTest(DiseaseService service, ReportService reportService) {
    super(service, reportService);
    this.service = service;
    this.reportService = reportService;
  }

  protected DiseaseDTO createDTO() {
    return new DiseaseDTO(name);
  }

  protected DiseaseDTO createAnotherDTO() {
    return new DiseaseDTO(name + "new");
  }

  protected Disease saveEntity() {
    return service.save(createDTO());
  }

  @BeforeEach
  private void diseaseServiceTestSetup() {
    service.drop();
    reportService.drop();
  }

  @AfterEach
  private void diseaseServiceTestCleanup() {
    service.drop();
    reportService.drop();
  }

  @Test
  void givenEntityInDB_findOneByName_returnsEntity() {
    Disease disease = saveEntity();
    assertEquals(disease, service.findOneByName(name));
  }

  @Test
  void givenInvalidName_findOneByName() throws EntityNotFoundException {
    assertThrows(
        EntityNotFoundException.class,
        () -> {
          service.findOneByName("non_existent_disease");
        });
  }

  @Test
  void findOneOrCreate_whenNameAlreadyInDb_findsOne() {
    Disease disease = saveEntity();
    assertEquals(disease, service.findOneOrCreate(dto));
  }

  @Test
  void findOneOrCreate_whenNameNotInDb_createsOne() {
    Disease disease = saveEntity();
    dto.setName(disease.getName() + "new");
    assertNotEquals(disease, service.findOneOrCreate(dto));
  }

  @Test
  void findOneOrCreate_WhenIdInDB_findsOne() {
    DiseaseDTO newDTO = new DiseaseDTO(50L, "name");
    Disease disease = service.save(newDTO);
    newDTO.setId(disease.getId());
    newDTO.setName("something_else");
    assertEquals(disease, service.findOneOrCreate(newDTO));
  }

  @Test
  void findOneOrCreate_WhenIdNotInDB_createsOne() {
    DiseaseDTO newDTO = new DiseaseDTO(5000970L, "name");
    Disease disease = service.save(dto);
    assertNotEquals(disease, service.findOneOrCreate(newDTO));
  }

  @Test
  void findOneOrCreate_whenIdAndNameInDB_andIsSameEntity_findsOne() {
    DiseaseDTO newDTO = new DiseaseDTO(550L, "name");
    Disease disease = service.save(newDTO);
    newDTO.setId(disease.getId());
    assertEquals(disease, service.findOneOrCreate(newDTO));
  }

  @Test
  void findOneOrCreate_whenIdAndNameInDB_DoesNotThrow() {
    DiseaseDTO dto = new DiseaseDTO(550L, "name");
    DiseaseDTO otherDTO = new DiseaseDTO(600L, "other_name");
    Disease disease = service.save(dto);
    service.save(otherDTO);
    assertDoesNotThrow(
        () -> {
          service.findOneOrCreate(new DiseaseDTO(disease.getId(), otherDTO.getName()));
        });
  }

  @Test
  void whenFindOneByNameCalled_findOneReported_withMethodName() {
    Disease disease = saveEntity();
    service.findOneByName(disease.getName());
    assertEquals(1, reportService.findByMethodName("findOneByName").size());
  }

  @Test
  void whenFindOneCalledOnValidEntity_findOneCallReported_withReturnedEntity() {
    Disease disease = saveEntity();

    service.findOneByName(disease.getName());

    Report report = reportService.findByMethodName("findOneByName").get(0);

    assertEquals(1, report.getEntities().size());
    assertTrue(report.getEntities().containsKey(disease.getId()));
    assertEquals(disease.getClass().getName(), report.getEntities().get(disease.getId()));
  }

  @Test
  @Transactional(noRollbackFor = EntityNotFoundException.class)
  void whenFindOneCalledOnInValidEntity_isThrownAndReported() {
    try {
      service.findOneByName("this_name_is_not_stored");
    } catch (EntityNotFoundException e) {
      assertEquals(1, reportService.count());
    }
  }

  @Test
  @Transactional(noRollbackFor = EntityNotFoundException.class)
  void whenFindOneCalledOnInValidEntity_findOneCallReported_withThrownNotFoundException() {
    try {
      service.findOneByName("this_name_is_not_stored");
    } catch (EntityNotFoundException e) {
      Report report = reportService.findByMethodName("findOneByName").get(0);
      assertEquals(e.getClass(), report.getException().getLeft());
    }
  }
}
