package com.marand.medAPI.Disease;

import com.marand.medAPI.Common.Services.ReportedService;
import com.marand.medAPI.Report.Reported;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class DiseaseService extends ReportedService<Disease, DiseaseDTO> {

  DiseaseRepository repo;

  DiseaseService(DiseaseRepository repo) {
    super(repo);
    this.repo = repo;
  }

  protected Disease create() {
    return new Disease();
  }

  protected Optional<Disease> findOneByNameOptional(String name) {
    return repo.findByName(name);
  }

  @Reported
  @Transactional
  public Disease findOneByName(String name) {
    return findOneByNameOptional(name).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public Disease findOneOrCreate(DiseaseDTO dto) {
    return findOneByNameOptional(dto.getName())
        .orElse(findOneOptional(dto.getId()).orElseGet(this::create));
  }
}
