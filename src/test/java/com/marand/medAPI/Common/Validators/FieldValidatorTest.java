package com.marand.medAPI.Common.Validators;

import com.marand.medAPI.Common.Annotations.ReflectWith;
import com.marand.medAPI.Common.Entities.BaseEntity;
import com.marand.medAPI.Disease.DiseaseDTO;
import com.marand.medAPI.Patient.PatientDTO;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;

import static com.marand.medAPI.Common.Validators.FieldValidator.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FieldValidatorTest {

  @Test
  void givenFieldWithNameEqualToPassedString_whenCheckNameEqualityCalled_returnsTrue()
      throws NoSuchFieldException {
    Field field = BaseEntity.class.getDeclaredField("id");
    assertTrue(hasSameName(field, "id"));
  }

  @Test
  void givenFieldWithNameUnequalToPassedString__returnsFalse() throws NoSuchFieldException {
    Field field = MockEntity.class.getDeclaredField("email");
    assertFalse(hasSameName(field, "name"));
  }

  @Test
  void givenEqualTypeFieldAndObject_whenCheckTypeEqualityCalled_ReturnsTrue()
      throws NoSuchFieldException {
    Field field = MockEntity.class.getDeclaredField("email");
    String param = "test";
    assertTrue(isSameType(field, param));
  }

  @Test
  void givenEqualTypeFieldAndPrimitive_() throws NoSuchFieldException {
    Field field = BaseEntity.class.getDeclaredField("id");
    long param = 5L;
    assertTrue(isSameType(field, param));
  }

  @Test
  void givenEqualTypeFieldAndArrayType_() throws NoSuchFieldException {
    Field field = MockEntity.class.getDeclaredField("strings");
    String[] param = {"1", "2"};
    assertTrue(isSameType(field, param));
  }

  @Test
  void givenEqualTypeFieldAndSetType_() throws NoSuchFieldException {
    Field field = PatientDTO.class.getDeclaredField("diseases");
    Set<DiseaseDTO> diseases = new HashSet(Arrays.asList(new DiseaseDTO("test")));
    assertTrue(isSameType(field, diseases));
  }

  @Test
  void givenNullParam_() throws NoSuchFieldException {
    Field field = MockEntity.class.getDeclaredField("strings");
    assertTrue(isSameType(field, null));
  }

  @Test
  void givenUnequalTypeFieldAndObject_whenCheckTypeEqualityCalled_ReturnsFalse()
      throws NoSuchFieldException {
    Field field = MockEntity.class.getDeclaredField("email");
    Long param = 50L;
    assertFalse(isSameType(field, param));
  }

  @Test
  void givenUnequalTypeFieldAndPrimitive_() throws NoSuchFieldException {
    Field field = BaseEntity.class.getDeclaredField("id");
    byte param = 0;
    assertFalse(isSameType(field, param));
  }

  @Test
  void givenUnequalTypeFieldAndArrayType_() throws NoSuchFieldException {
    Field field = MockEntity.class.getDeclaredField("strings");
    Integer[] param = {1, 2};
    assertFalse(isSameType(field, param));
  }

  @Test
  void givenPrimitive_inParamMap_withValue_whenCheckNotDefaultCalled_returnsTrue() {

    byte b = 1;
    assertFalse(isDefaultValue("Byte", b));
    short s = 1;
    assertFalse(isDefaultValue("Short", s));
    int i = 1;
    assertFalse(isDefaultValue("Integer", i));
    long l = 1L;
    assertFalse(isDefaultValue("Long", l));
    float f = 1.1f;
    assertFalse(isDefaultValue("Float", f));
    double d = 1.0d;
    assertFalse(isDefaultValue("Double", d));
    char c = 'a';
    assertFalse(isDefaultValue("Character", c));
    boolean bool = true;
    assertFalse(isDefaultValue("Boolean", bool));
  }

  @Test
  void givenObject_inParamMap_withValue_returnsTrue() {
    assertFalse(isDefaultValue("object", "object"));
  }

  @Test
  void givenPrimitive_inParamMap_withDefaultValue_whenCheckNotDefaultCalled_returnsFalse() {

    byte b = 0;
    assertTrue(isDefaultValue("Byte", b));
    short s = 0;
    assertTrue(isDefaultValue("Short", s));
    int i = 0;
    assertTrue(isDefaultValue("Integer", i));
    long l = 0l;
    assertTrue(isDefaultValue("Long", l));
    float f = 0.0f;
    assertTrue(isDefaultValue("Float", f));
    double d = 0.0d;
    assertTrue(isDefaultValue("Double", d));
    char c = '\u0000';
    assertTrue(isDefaultValue("Character", c));
    boolean bool = false;
    assertTrue(isDefaultValue("Boolean", bool));
  }

  @Test
  void givenObject_inParamMap_withNullValue_returnsFalse() {
    assertTrue(isDefaultValue("string", null));
  }

  @Test
  void givenEntityWithReflectWithAnnotatedParam_isReflectableField_returnsTrue() throws NoSuchFieldException {
    assertTrue(isReflectableField(MockEntity.class.getDeclaredField("strings")));
  }

  @Test
  void givenNonAnnotatedField_isReflectableField_returnsFalse() throws NoSuchFieldException {
    assertFalse(isReflectableField(MockEntity.class.getDeclaredField("email")));
  }

  @Test
  void givenDTOCollectionWithValues_isValidCollection_returnsTrue() {
    assertTrue(isReflectableCollection(new HashSet(Collections.singletonList(new DiseaseDTO("name")))));
  }

  @Test
  void givenNonDTOCollectionWithValues_isValidCollection_returnsFalse() {
    assertFalse(isReflectableCollection(new HashSet(Collections.singletonList(1))));
  }

  @Test
  void givenEmptyCollection_isValidCollection_returnsFalse() {
    assertFalse(isReflectableCollection(new HashSet(Collections.emptyList())));
  }

  @Test
  void givenArray_isValidCollection_returnsFalse() {
    DiseaseDTO[] diseases = {new DiseaseDTO("name")};
    assertFalse(isReflectableCollection(diseases));
  }

  @Test
  void givenSingleton_isValidCollection_returnsFalse() {
    DiseaseDTO disease = new DiseaseDTO("name");
    assertFalse(isReflectableCollection(disease));
  }

  @Test
  void givenDTOArrayWithValues_isValidArray_returnsFalse() {
    DiseaseDTO[] diseases = {new DiseaseDTO("name")};
    assertTrue(isReflectableArray(diseases));
  }

  @Test
  void givenNonDTOArrayWithValues_isValidArray_returnsFalse() {
    Integer[] ints = {1,2};
    assertFalse(isReflectableArray(ints));
  }

  @Test
  void givenEmptyArray_isValidArray_returnsFalse() {
    String[] strings = {};
    assertFalse(isReflectableArray(strings));
  }

  @Test
  void givenCollection_isValidArray_returnsFalse() {
    assertFalse(isReflectableArray(new HashSet(Collections.singletonList(new DiseaseDTO("name")))));
  }

  @Test
  void givenSingleton_isValidArray_returnsFalse() {
    DiseaseDTO disease = new DiseaseDTO("name");
    assertFalse(isReflectableArray(disease));
  }

  @Test
  void givenSingleton_isValidSingleton_returnsTrue() {
    DiseaseDTO disease = new DiseaseDTO("name");
    assertTrue(isReflectableSingleton(disease));
  }

  @Test
  void givenNonValidSingleton_isValidSingleton_returnsFalse() {
    int i = 0;
    assertFalse(isReflectableSingleton(i));
  }

  @Test
  void givenArray_isValidSingleton_returnsFalse() {
    DiseaseDTO[] diseases = {new DiseaseDTO("name")};
    assertFalse(isReflectableSingleton(diseases));
  }

  @Test
  void givenNull_isValidSingleton_returnsFalse() {
    assertFalse(isReflectableSingleton(null));
  }

  private static class MockEntity extends BaseEntity {
    private String email;

    @ReflectWith
    private String[] strings;

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

    public String[] getStrings() {
      return strings;
    }

    public void setStrings(String[] strings) {
      this.strings = strings;
    }
  }
}
