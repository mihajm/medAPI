package com.marand.medAPI.Disease;

import com.marand.medAPI.Common.Services.UpdaterServiceTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiseaseServiceTest extends UpdaterServiceTest<Disease, DiseaseDTO> {

  private String name = "good_at_tests";
  private DiseaseDTO dto = createDTO();

  private DiseaseService service;

  @Autowired
  protected DiseaseServiceTest(DiseaseService service) {
    super(service);
    this.service = service;
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
  private void diseaseServiceTestSetup() {service.drop();}

  @AfterEach
  private void diseaseServiceTestCleanup() {
    service.drop();
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
}
