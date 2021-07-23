package com.marand.medAPI.Disease;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marand.medAPI.Common.Entities.GeneratedIdEntityTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class DiseaseTest extends GeneratedIdEntityTest {

  private Disease disease = createEntity();

  protected Disease createEntity() {
    return new Disease();
  }

  @Test
  void hasAName() {
    assertNotNull(disease.getName());
  }

  @Test
  void hasSettableName() {
    String name = "a_disease";
    disease.setName(name);
    assertEquals(name, disease.getName());
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
  void givenUppercaseCharacters_WhenSettingName_ConvertsToLowerCased() {
    String name = "UPPER_CASED";
    disease.setName(name);
    assertEquals(name.toLowerCase(Locale.ROOT), disease.getName());
  }

  @Test
  void whenSettingNullName() throws IllegalArgumentException {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          disease.setName(null);
        });
  }

  @Test
  void whenSettingEmptyName() throws IllegalArgumentException {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          disease.setName("");
        });
  }

  @Test
  void whenSettingTooLongName() throws IllegalArgumentException {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          disease.setName("a".repeat(260));
        });
  }

  @Test
  void whenSettingNameWithIllegalChars() throws IllegalArgumentException {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          disease.setName("123");
        });
  }

  @Test
  void isSerializable() {
    ObjectMapper mapper = new ObjectMapper();
    assertTrue(mapper.canSerialize(Disease.class));
  }

  @Test
  void containsNameWhenSerialized() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String name = "a_disease";
    disease.setName(name);
    String objectString = mapper.writeValueAsString(disease);
    assertTrue(objectString.contains(name));
  }

  @Test
  void isDeSerializable() {
    ObjectMapper mapper = new ObjectMapper();
    assertTrue(mapper.canDeserialize(mapper.constructType(Disease.class)));
  }

  @Test
  void containsNameWhenDeSerialized() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String name = "a_disease";
    disease.setName(name);
    String objectString = mapper.writeValueAsString(disease);
    Disease entity = mapper.readValue(objectString, Disease.class);
    assertEquals(name, entity.getName());
  }
}
