package com.marand.medAPI.Common.Services;

import com.marand.medAPI.Common.DTOs.BaseDTO;
import com.marand.medAPI.Common.Objects.BaseDataObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class UpdaterServiceTest<E extends BaseDataObject, DTO extends BaseDTO>
    extends FinderServiceTest<E> {
  private UpdaterService<E, DTO> service;

  protected UpdaterServiceTest(UpdaterService<E, DTO> service) {
    super(service);
    this.service = service;
  }

  protected abstract DTO createDTO();

  @AfterEach
  void UpdaterServiceTestCleanup() {
    service.drop();
  }

  @Test
  void givenDTO_savesEntity() {
    service.drop();
    long count = service.count();
    service.save(createDTO());
    assertEquals(count + 1, service.count());
  }

  @Test
  void givenDTOOFExistingEntity_UpdatesEntity() {
    DTO dto = createDTO();
    E entity = service.save(dto);
    long count = service.count();
    dto.setId(entity.getId());
    service.save(dto);
    assertEquals(count, service.count());
  }

  @Test
  void givenValidId_findOneOrCreate_returnsEntityInDB() {
    E entity = saveEntity();
    DTO dto = createDTO();
    dto.setId(entity.getId());
    assertEquals(entity, service.findOneOrCreate(dto));
  }
}
