package com.marand.medAPI.Common.Entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marand.medAPI.Common.Objects.BaseDataObject;
import com.marand.medAPI.Common.Objects.BaseDataObjectTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class GeneratedIdEntityTest extends BaseDataObjectTest {

  GeneratedIdEntity entity = createEntity();

  protected GeneratedIdEntity createEntity() {
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
  void whenConstructedWithId_hasId() {
    long id = 10L;
    assertEquals(id, new GeneratedIdEntity(id).getId());
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
    GeneratedIdEntity entity = new GeneratedIdEntity();
    long id = 50L;
    entity.setId(id);
    String objectString = mapper.writeValueAsString(entity);
    GeneratedIdEntity deserializedEntity = mapper.readValue(objectString, GeneratedIdEntity.class);
    assertEquals(id, deserializedEntity.getId());
  }
}
