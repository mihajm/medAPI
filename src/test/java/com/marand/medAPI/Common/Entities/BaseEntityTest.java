package com.marand.medAPI.Common.Entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class BaseEntityTest {

  BaseEntity entity = createEntity();

  protected BaseEntity createEntity() {
    return new BaseEntity();
  }

  @BeforeEach
  private void BaseEntityTestSetup() {
    entity = createEntity();
  }

  @Test
  void hasId() {
    assertTrue(entity.getId() >= 0);
  }

  @Test
  void idIsSettable() {
    long id = 50L;
    entity.setId(id);
    assertEquals(id, entity.getId());
  }

  @Test
  void canBeConstructedWithNoArgs() {
    assertDoesNotThrow(this::createEntity);
  }

  @Test
  void whenConstructedWithId_HasId() {
    long id = 10L;
    assertEquals(id, new BaseEntity(id).getId());
  }

  @Test
  void isSerializable() {
    ObjectMapper mapper = new ObjectMapper();
    assertTrue(mapper.canSerialize(BaseEntity.class));
  }

  @Test
  void containsIdWhenSerialized() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    long id = 50L;
    entity.setId(id);
    String objectString = mapper.writeValueAsString(entity);
    assertTrue(objectString.contains(String.valueOf(id)));
  }

  @Test
  void isDeSerializable() {
    ObjectMapper mapper = new ObjectMapper();
    assertTrue(mapper.canDeserialize(mapper.constructType(BaseEntity.class)));
  }
}
