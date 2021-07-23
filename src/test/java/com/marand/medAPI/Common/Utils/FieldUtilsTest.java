package com.marand.medAPI.Common.Utils;

import com.marand.medAPI.Common.DTOs.BaseDTO;
import com.marand.medAPI.Common.Entities.BaseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FieldUtilsTest {

  private long id = 50L;
  private String email = "email";
  private MockEntity mockEntity;

  @BeforeEach
  private void FieldUtilsTestSetup() {
    mockEntity = new MockEntity(id, email);
  }

  @Test
  void getAllDeclaredFields_ReturnsListOfFieldsFromClass() {
    assertNotNull(FieldUtils.getAllDeclaredFields(MockEntity.class));
  }

  @Test
  void getAllDeclaredFields_HasAllFieldsFromClass() {
    assertTrue(FieldUtils.getAllDeclaredFields(MockEntity.class).size() > 0);
  }

  @Test
  void getAllDeclaredFields_HasAllFieldsFromClassAndSuperClasses() {
    assertTrue(FieldUtils.getAllDeclaredFields(MockEntity.class).size() > 1);
  }

  @Test
  void readField_returnsValue() {
    assertNotNull(FieldUtils.readField(mockEntity, "email"));
  }

  @Test
  void readField_returnsParamValue() {
    assertEquals(email, FieldUtils.readField(mockEntity, "email").orElseThrow());
  }

  @Test
  void readField_returnsSuperClassParamValue() {
    assertEquals(id, FieldUtils.readField(mockEntity, "id").orElseThrow());
  }

  @Test
  void writeField_SetsClassParamValue() {
    String anotherEmail = "new_email";
    FieldUtils.writeField(mockEntity, "email", anotherEmail);
    assertEquals(anotherEmail, mockEntity.getEmail());
  }

  @Test
  void writeField_SetsSuperClassParamValue() {
    long anotherId = 55L;
    FieldUtils.writeField(mockEntity, "id", anotherId);
    assertEquals(anotherId, mockEntity.getId());
  }

  @Test
  void writeField_SetsMultipleValues() {
    long anotherId = 55L;
    String anotherEmail = "new_email";

    Map<String, Object> params = new HashMap();
    params.put("id", anotherId);
    params.put("email", anotherEmail);

    FieldUtils.writeFields(mockEntity, params);

    assertEquals(anotherId, mockEntity.getId());
    assertEquals(anotherEmail, mockEntity.getEmail());
  }

  @Test
  void transferFields_SetsFieldsOfOtherEntity_toThoseOfProvidedObject() {
    MockEntity newEntity = new MockEntity();
    FieldUtils.transferFields(newEntity, mockEntity);
    assertEquals(mockEntity, newEntity);
  }

  @Test
  void transferFields_SetsFieldsOfOtherEntity_toThoseOfProvidedDifferentObject() {
    MockEntity newEntity = new MockEntity();
    MockDTO dto = new MockDTO(55L, "myEmail");
    FieldUtils.transferFields(newEntity, dto);
    assertEquals(dto.getId(), newEntity.getId());
    assertEquals(dto.getEmail(), newEntity.getEmail());
  }

  @Test
  void givenNonExistentField_readField_returnsEmptyOptional() {
    assertTrue(FieldUtils.readField(mockEntity, "name").isEmpty());
  }

  @Test
  void givenNullField_readField() throws IllegalArgumentException {
    assertThrows(IllegalArgumentException.class, () -> FieldUtils.readField(mockEntity, null));
  }

  @Test
  void givenEmptyField_readField() throws IllegalArgumentException {
    assertThrows(IllegalArgumentException.class, () -> FieldUtils.readField(mockEntity, ""));
  }

  @Test
  void givenBlankField_readField() throws IllegalArgumentException {
    assertThrows(IllegalArgumentException.class, () -> FieldUtils.readField(mockEntity, " "));
  }

  @Test
  void givenObjectWithUnreadableParams_readFields_ignoresUnreadableFields() {
    Map<String, Object> paramMap = FieldUtils.readFields(new MockNoGetter(50L));
    assertFalse(paramMap.containsKey("id"));
    assertTrue(paramMap.containsKey("string"));
    assertEquals("test", paramMap.get("string"));
  }

  @Test
  void givenFieldNameOfNonExistentField_writeField_ignoresField() {
    FieldUtils.writeField(mockEntity, "name", "string");
    assertFalse(mockEntity.toString().contains("name"));
    assertFalse(mockEntity.toString().contains("string"));
  }

  @Test
  void givenWrongValueType_writeField_ignoresField() {
    FieldUtils.writeField(mockEntity, "id", "string");
    assertEquals(id, mockEntity.getId());
    assertEquals(email, mockEntity.getEmail());
    assertFalse(mockEntity.toString().contains("string"));
  }

  @Test
  void givenWrongValueNames_writeFields_ignoresField() {
    FieldUtils.writeFields(mockEntity, new HashMap(Collections.singletonMap("name", "String")));
    assertEquals(id, mockEntity.getId());
    assertEquals(email, mockEntity.getEmail());
    assertFalse(mockEntity.toString().contains("name"));
    assertFalse(mockEntity.toString().contains("string"));
  }

  @Test
  void givenWrongValueType_writeFields_ignoresField() {
    FieldUtils.writeFields(mockEntity, new HashMap(Collections.singletonMap("id", "String")));
    assertEquals(id, mockEntity.getId());
    assertEquals(email, mockEntity.getEmail());
    assertFalse(mockEntity.toString().contains("string"));
  }

  private static class MockEntity extends BaseEntity {
    private String email;

    protected MockEntity() {}

    protected MockEntity(long id) {
      super(id);
    }

    protected MockEntity(long id, String email) {
      super(id);
      setEmail(email);
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }
  }

  private static class MockDTO extends BaseDTO {
    private String email;

    protected MockDTO() {}

    protected MockDTO(long id) {
      super(id);
    }

    protected MockDTO(long id, String email) {
      super(id);
      setEmail(email);
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }
  }

  private static class MockNoGetter {
    private long id;

    private String string = "test";

    protected MockNoGetter() {}

    protected MockNoGetter(long id) {
      setId(id);
    }

    public void setId(long id) {
      this.id = id;
    }

    public String getString() {
      return string;
    }

    public void setString(String string) {
      this.string = string;
    }
  }

  private static class NotMockDTO extends BaseDTO {
    private String name;
    private Set<String> strings = new HashSet();

    protected NotMockDTO() {}

    protected NotMockDTO(long id) {
      super(id);
    }

    protected NotMockDTO(long id, String name, Set<String> strings) {
      super(id);
      setName(name);
      setStrings(strings);
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Set<String> getStrings() {
      return strings;
    }

    public void setStrings(Set<String> strings) {
      this.strings = strings;
    }

    public void addString(String string) {
      this.strings.add(string);
    }
  }
}
