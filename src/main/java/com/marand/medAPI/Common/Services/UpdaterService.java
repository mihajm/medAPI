package com.marand.medAPI.Common.Services;

import com.marand.medAPI.Common.DTOs.BaseDTO;
import com.marand.medAPI.Common.Objects.BaseDataObject;
import com.marand.medAPI.Common.Utils.FieldUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;

public abstract class UpdaterService<E extends BaseDataObject, DTO extends BaseDTO>
    extends FinderService<E> {
  JpaRepository<E, Long> repo;

  private ApplicationContext context;

  protected UpdaterService(JpaRepository<E, Long> repo) {
    super(repo);
    this.repo = repo;
  }

  protected UpdaterService(JpaRepository<E, Long> repo, ApplicationContext context) {
    super(repo);
    this.repo = repo;
    this.context = context;
  }

  public E save(DTO dto) {
    return save(entityFrom(dto));
  }

  protected E entityFrom(DTO dto) {
    return setFields(findOneOrCreate(dto), dto);
  }

  public E findOneOrCreate(DTO dto) {
    return findOneOptional(dto.getId()).orElse(create());
  }

  public E setFields(E entity, DTO dto) {
    FieldUtils.transferFields(entity, dto);
    preloadSubEntities(entity, dto);
    return entity;
  }

  @SuppressWarnings("unchecked")
  private void preloadSubEntities(E entity, DTO dto) {
    if (context == null) return;
    FieldUtils.getEntityFields(dto)
            .forEach((field) -> FieldUtils.writeEntityField(entity, field, dto, getSubService(field)));
  }

  @SuppressWarnings("rawtypes")
  private UpdaterService getSubService(Field field) {
    return context.getBean(FieldUtils.getServiceClass(field));
  }
}
