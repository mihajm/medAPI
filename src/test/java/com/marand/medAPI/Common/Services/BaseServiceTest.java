package com.marand.medAPI.Common.Services;

import com.marand.medAPI.Common.Objects.BaseDataObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public abstract class BaseServiceTest<E extends BaseDataObject> {

  private BaseService<E> service;

  protected BaseServiceTest(BaseService<E> service) {
    this.service = service;
  }

  protected abstract E saveEntity();

  @AfterEach
  void BaseServiceTestCleanup() {
    service.drop();
  }

  @Test
  void givenEmptyRepo_countReturnsZero() {
    service.drop();
    assertEquals(0, service.count());
  }

  @Test
  void givenRepoWithOneEntity_countReturnsOne() {
    saveEntity();
    assertEquals(1, service.count());
  }

  @Test
  void savingAnEntity_incrementsCount() {
    service.drop();
    long count = service.count();
    saveEntity();
    assertEquals(count + 1, service.count());
  }

  @Test
  void drop_deletesAllEntities() {
    saveEntity();
    service.drop();
    assertEquals(0, service.count());
  }

  @Test
  void create_returnsNewEntity() {
    assertNotNull(service.create());
  }

  @Test
  void remove_removesEntity() {
    E entity = saveEntity();
    service.remove(entity.getId());
    assertThrows(
        EntityNotFoundException.class,
        () -> {
          service.findOne(entity.getId());
        });

  }
}
