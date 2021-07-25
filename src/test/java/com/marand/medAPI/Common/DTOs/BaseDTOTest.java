package com.marand.medAPI.Common.DTOs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marand.medAPI.Common.Objects.BaseDataObjectTest;
import com.marand.medAPI.Disease.DiseaseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import static org.junit.jupiter.api.Assertions.*;

public abstract class BaseDTOTest extends BaseDataObjectTest {

  private BaseDTO dto = createDTO();

  protected BaseDTO createDTO() {
    return new BaseDTO();
  }

  @BeforeEach
  private void BaseDTOTestSetup() {
    dto = createDTO();
  }

  @Test
  void hasId() {
    assertNotNull(dto.getId());
  }

  @Test
  void whenConstructedWithoutId_HasNegativeId() {
    assertTrue(dto.getId() < 0);
  }

  @Test
  void whenConstructedWithId_HasId() {
    long id = 1L;
    dto = new BaseDTO(id);
    assertEquals(id, dto.getId());
  }

  @Test
  void idIsSettable() {
    long id = 50L;
    dto.setId(id);
    assertEquals(id, dto.getId());
  }

  @Test
  void canBeConstructedWithNoArgs() {
    assertDoesNotThrow((ThrowingSupplier<BaseDTO>) BaseDTO::new);
  }

  @Test
  void canBeConstructedWithId() {
    assertDoesNotThrow(() -> new BaseDTO(50L));
  }

  @Test
  void isSerializable() {
    ObjectMapper mapper = new ObjectMapper();
    assertTrue(mapper.canSerialize(BaseDTO.class));
  }

  @Test
  void isDeSerializable() {
    ObjectMapper mapper = new ObjectMapper();
    assertTrue(mapper.canDeserialize(mapper.constructType(BaseDTO.class)));
  }

  @Test
  void containsIdWhenSerialized() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    long id = 50L;
    dto.setId(id);
    String objectString = mapper.writeValueAsString(dto);
    assertTrue(objectString.contains(String.valueOf(id)));
  }


  @Test
  void containsIdWhenDeSerialized() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    BaseDTO dto = new BaseDTO();
    long id = 50L;
    dto.setId(id);
    String objectString = mapper.writeValueAsString(dto);
    BaseDTO readDTO = mapper.readValue(objectString, mapper.constructType(BaseDTO.class));
    assertEquals(id, readDTO.getId());
  }

  @Test
  void givenNewDTO_whenGetIdCalled_ReturnsNegativeOne() {
    assertTrue(dto.getId() == -1);
  }
}
