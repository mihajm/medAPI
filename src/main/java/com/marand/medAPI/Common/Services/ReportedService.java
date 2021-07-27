package com.marand.medAPI.Common.Services;

import com.marand.medAPI.Common.DTOs.BaseDTO;
import com.marand.medAPI.Common.Objects.BaseDataObject;
import com.marand.medAPI.Report.Reported;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class ReportedService<E extends BaseDataObject, DTO extends BaseDTO>
    extends UpdaterService<E, DTO> {

  protected ReportedService(JpaRepository<E, Long> repo) {
    super(repo);
  }

  protected ReportedService(JpaRepository<E, Long> repo, ApplicationContext context) {
    super(repo, context);
  }

  @Reported
  public long count() {
    return super.count();
  }

  @Reported
  public List<E> findAll() {
    return super.findAll();
  }

  @Reported
  public E findOne(long id) {
    return super.findOne(id);
  }

  @Reported
  public E save(DTO dto) {
    return super.save(dto);
  }

  @Reported
  public void remove(long id) {
    super.remove(id);
  }
}
