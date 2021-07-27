package com.marand.medAPI.Common.Services;

import com.marand.medAPI.Common.Objects.BaseDataObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

public abstract class FinderService<E extends BaseDataObject> extends BaseService<E> {
  private final JpaRepository<E, Long> repo;

  protected FinderService(JpaRepository<E, Long> repo) {
    super(repo);
    this.repo = repo;
  }

  protected Optional<E> findOneOptional(long id) {
    return repo.findById(id);
  }

  @Transactional
  public E findOne(long id) {
    return findOneOptional(id).orElseThrow(EntityNotFoundException::new);
  }

  @Transactional
  public List<E> findAll() {
    return repo.findAll();
  }
}
