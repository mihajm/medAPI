package com.marand.medAPI.Common.Services;

import com.marand.medAPI.Common.Objects.BaseDataObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class FinderServiceTest<E extends BaseDataObject> extends BaseServiceTest<E> {

  private FinderService<E> service;

  protected FinderServiceTest(FinderService<E> service) {
    super(service);
    this.service = service;
  }

  @AfterEach
  private void FinderServiceTestCleanup() {
    service.drop();
  }

  @Test
  void givenValidId_findOneReturnsEntity() {
    E entity = saveEntity();
    assertNotNull(service.findOne(entity.getId()));
  }

  @Test
  void findAll_ReturnsSetOfAllEntities() {
    saveEntity();
    saveEntity();
    assertEquals(service.count(), service.findAll().size());
  }
}
