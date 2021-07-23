package com.marand.medAPI.Disease;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marand.medAPI.Common.DTOs.BaseDTOTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import static org.junit.jupiter.api.Assertions.*;

class DiseaseDTOTest extends BaseDTOTest {

  private DiseaseDTO dto = new DiseaseDTO();

  @Test
  void hasAName() {
    assertNotNull(dto.getName());
  }

  @Test
  void hasSettableName() {
    String name = "a_disease";
    dto.setName(name);
    assertEquals(name, dto.getName());
  }

  @Test
  void canBeConstructedWithNoArgs() {
    assertDoesNotThrow((ThrowingSupplier<Disease>) Disease::new);
  }

  @Test
  void canBeConstructedWithName() {
    assertDoesNotThrow(() -> new Disease("name"));
  }

  @Test
  void canBeConstructedWithIdAndName() {
    assertDoesNotThrow(() -> new Disease(50L, "name"));
  }

  @Test
  void isSerializable() {
    ObjectMapper mapper = new ObjectMapper();
    assertTrue(mapper.canSerialize(DiseaseDTO.class));
  }

  @Test
  void containsNameWhenSerialized() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String name = "a_disease";
    dto.setName(name);
    String objectString = mapper.writeValueAsString(dto);
    assertTrue(objectString.contains(name));
  }

  @Test
  void isDeSerializable() {
    ObjectMapper mapper = new ObjectMapper();
    assertTrue(mapper.canDeserialize(mapper.constructType(DiseaseDTO.class)));
  }

  @Test
  void containsNameWhenDeSerialized() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String name = "a_disease";
    dto.setName(name);
    String objectString = mapper.writeValueAsString(dto);
    DiseaseDTO readDTO = mapper.readValue(objectString, DiseaseDTO.class);
    assertEquals(name, readDTO.getName());
  }
}
