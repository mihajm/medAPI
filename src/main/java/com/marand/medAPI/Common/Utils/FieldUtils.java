package com.marand.medAPI.Common.Utils;

import com.marand.medAPI.Common.Annotations.ReflectWith;
import com.marand.medAPI.Common.DTOs.BaseDTO;
import com.marand.medAPI.Common.Objects.BaseDataObject;
import com.marand.medAPI.Common.Services.UpdaterService;
import com.marand.medAPI.Common.Validators.FieldValidator;
import com.marand.medAPI.Common.Validators.StringValidator;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.NotWritablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.TypeMismatchException;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.marand.medAPI.Common.Validators.FieldValidator.*;

public class FieldUtils {

  private static List<Field> getDeclaredFields(Class<?> clazz) {
    return List.of(clazz.getDeclaredFields());
  }

  public static List<Field> getAllDeclaredFields(Class<?> clazz) {
    List<Field> fields = new ArrayList(getDeclaredFields(clazz));
    if (clazz.getSuperclass() == null) return fields;
    fields.addAll(getAllDeclaredFields(clazz.getSuperclass()));
    return fields;
  }

  private static List<Field> getAllDeclaredFields(Object object) {
    return getAllDeclaredFields(object.getClass());
  }

  public static Optional<Object> readField(Object provider, String fieldName) {
    try {
      return Optional.ofNullable(
          PropertyAccessorFactory.forBeanPropertyAccess(provider)
              .getPropertyValue(new StringValidator(fieldName).hasValue()));
    } catch (NotReadablePropertyException ignored) {
      return Optional.empty();
    }
  }

  public static Map<String, Object> readFields(Object provider) {
    Map<String, Object> fields = new HashMap();
    getAllDeclaredFields(provider)
        .forEach(
            (field) ->
                readField(provider, field.getName())
                    .ifPresent(val -> fields.put(field.getName(), val)));
    return fields;
  }

  public static void writeField(Object target, String fieldName, Object value) {
    if (isDefaultValue(fieldName, value)) return;
    try {
      PropertyAccessorFactory.forBeanPropertyAccess(target)
          .setPropertyValue(new StringValidator(fieldName).hasValue(), value);
    } catch (TypeMismatchException | NotWritablePropertyException ignored) {
    }
  }

  public static void writeFields(Object target, Map<String, Object> params) {
    params.forEach((key, value) -> writeField(target, key, value));
  }

  public static void transferFields(Object target, Object provider) {
    writeFields(target, readFields(provider));
  }

  public static <DTO extends BaseDTO> Set<Field> getEntityFields(DTO dto) {
    return getAllDeclaredFields(dto).stream()
        .filter(FieldValidator::isReflectableField)
        .collect(Collectors.toSet());
  }

  @SuppressWarnings("rawtypes")
  public static Class<? extends UpdaterService> getServiceClass(Field field) {
    return field.getAnnotation(ReflectWith.class).value();
  }

  private static <E extends BaseDataObject, DTO extends BaseDTO> void writeEntityCollection(
      E target, String fieldName, Collection<DTO> DTOs, UpdaterService<E, DTO> service) {

    PropertyAccessorFactory.forBeanPropertyAccess(target)
        .setPropertyValue(fieldName, DTOs.stream().map(service::entityFrom).collect(Collectors.toSet()));
  }

  @SuppressWarnings("unchecked")
  public static <E extends BaseDataObject, DTO extends BaseDTO> void writeEntityField(
      E target, Field field, DTO provider, UpdaterService<E, DTO> service) {
    Optional<Object> value = readField(provider, field.getName());
    if (value.isEmpty()) return;
    if (isReflectableCollection(value.get()))
      writeEntityCollection(target, field.getName(), (Collection<DTO>) value.get(), service);
  }
}
