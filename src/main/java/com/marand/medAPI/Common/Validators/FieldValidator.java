package com.marand.medAPI.Common.Validators;

import com.marand.medAPI.Common.Annotations.ReflectWith;
import com.marand.medAPI.Common.DTOs.BaseDTO;

import java.lang.reflect.Field;
import java.util.Collection;

public class FieldValidator {

  public static boolean hasSameName(Field field, String name) {
    return (field.getName().equals(name));
  }

  public static boolean isSameType(Field field, Object param) {
    if (param == null) return true;
    Class<?> fieldType = field.getType();
    Class<?> paramClass = param.getClass();

    if (paramClass.isArray()) return isSameArrayType(fieldType, paramClass);
    if (fieldType.isPrimitive()) return (fieldType == paramClass.getDeclaredFields()[0].getType());

    return (fieldType.isAssignableFrom(paramClass) || paramClass == fieldType);
  }

  private static boolean isSameArrayType(Class<?> fieldType, Class<?> paramClass) {
    return (fieldType.isArray() && fieldType.arrayType() == paramClass.arrayType());
  }

  public static boolean isReflectableField(Field field) {
    return field.isAnnotationPresent(ReflectWith.class);
  }

  public static boolean isReflectableCollection(Object object) {
    return (!isNull(object)
        && isCollection(object)
        && !isEmpty((Collection<?>) object)
        && isEntityCollection((Collection<?>) object));
  }

  public static boolean isReflectableSingleton(Object object) {
    return (!isNull(object) && isSingleton(object) && isDTO(object));
  }

  @SuppressWarnings("unchecked")
  public static <T> boolean isReflectableArray(Object object) {
    return (!isNull(object)
        && isArray(object)
        && !isEmpty((T[]) object)
        && isEntityArray((T[]) object));
  }

  private static boolean isEntityCollection(Collection<?> c) {
    for (Object o : c) if (!isDTO(o)) return false;
    return true;
  }

  private static boolean isCollection(Object o) {
    return Collection.class.isAssignableFrom(o.getClass());
  }

  private static boolean isEmpty(Collection<?> c) {
    return c.isEmpty();
  }

  private static <T> boolean isEmpty(T[] a) {
    return a.length == 0;
  }

  private static boolean isArray(Object object) {
    return object.getClass().isArray();
  }

  private static <T> boolean isEntityArray(T[] a) {
    for (Object o : a) if (!isDTO(o)) return false;
    return true;
  }

  private static boolean isNull(Object object) {
    return object == null;
  }

  private static boolean isSingleton(Object object) {
    return (!(isCollection(object) || isArray(object)));
  }

  private static boolean isDTO(Object object) {
    return BaseDTO.class.isAssignableFrom(object.getClass());
  }

  public static boolean isDefaultValue(String key, Object value) {
    if (isNull(value)) return true;
    if (isDefaultId(key, value)) return true;
    return isDefaultPrimitive(value);
  }

  private static boolean isDefaultId(String key, Object value) {
    return value instanceof Long && key.equals("id") && (long) value < 0L;
  }

  private static boolean isDefaultPrimitive(Object value) {
    if (value instanceof Byte && (byte) value == 0) return true;
    if (value instanceof Short && (short) value == 0) return true;
    if (value instanceof Integer && (int) value == 0) return true;
    if (value instanceof Long && (long) value == 0L) return true;
    if (value instanceof Float && (float) value == 0.0f) return true;
    if (value instanceof Double && (double) value == 0.0d) return true;
    if (value instanceof Character && (char) value == '\u0000') return true;
    return value instanceof Boolean && !((boolean) value);
  }
}
