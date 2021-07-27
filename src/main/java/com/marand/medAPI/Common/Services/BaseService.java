package com.marand.medAPI.Common.Services;

import com.marand.medAPI.Common.Objects.BaseDataObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class BaseService<E extends BaseDataObject> {
  private JpaRepository<E, Long> repo;

  protected BaseService(JpaRepository<E, Long> repo) {
    this.repo = repo;
  }

  protected abstract E create();

  public abstract E findOne(long id);

  public abstract List<E> findAll();

  public long count() {
    return repo.count();
  }

  @Transactional
  public E saveOne(E entity) {
    return repo.save(entity);
  }

  @Transactional
  public void remove(long id) {
    repo.deleteById(id);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void drop() {
    repo.deleteAllInBatch();
  }
}
