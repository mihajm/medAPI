package com.marand.medAPI.Common.Entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GeneratedIdEntityTest {

  GeneratedIdEntity entity = createEntity();

  private GeneratedIdEntity createEntity() {
    return new GeneratedIdEntity();
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

  @Test
  void containsIdWhenDeserialized() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    long id = 50L;
    entity.setId(id);
    String objectString = mapper.writeValueAsString(entity);
    GeneratedIdEntity entity = mapper.readValue(objectString, GeneratedIdEntity.class);
    assertEquals(id, entity.getId());
  }
}
