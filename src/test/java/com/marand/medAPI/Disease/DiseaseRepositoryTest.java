package com.marand.medAPI.Disease;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DiseaseRepositoryTest {

  @Autowired DiseaseRepository repo;
  private String name = "test_name";
  private Disease disease = new Disease(name);

  @Test
  void findByName_returnsOptionalDisease() {
    assertNotNull(repo.findByName(name));
  }

  @Test
  void ifDiseaseExists_findByName_returnsDisease() {
    repo.save(disease);
    assertTrue(repo.findByName(name).isPresent());
  }

  @Test
  void ifDiseaseDoesNotExist_findByName_returnsEmptyOptional() {
    assertTrue(repo.findByName(name).isEmpty());
  }

  @Test
  void whenSavedIgnoresGivenIdAndGeneratesUniqueOne() {
    long id = 500L;
    assertNotEquals(id, repo.save(new Disease(id, name)).getId());
  }

  @Test
  void whenSavedGeneratesUniqueId() {
    Disease disease1 = repo.save(new Disease());
    Disease disease2 = repo.save(new Disease());
    assertNotEquals(disease1.getId(), disease2.getId());
  }
}
