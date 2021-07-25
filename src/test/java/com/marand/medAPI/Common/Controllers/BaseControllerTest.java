package com.marand.medAPI.Common.Controllers;

import com.marand.medAPI.Common.DTOs.BaseDTO;
import com.marand.medAPI.Common.Objects.BaseDataObject;
import com.marand.medAPI.Common.Services.UpdaterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public abstract class BaseControllerTest<E extends BaseDataObject, DTO extends BaseDTO> {

  @Autowired protected TestRestTemplate testRestTemplate;

  private UpdaterService<E, DTO> service;

  protected BaseControllerTest(UpdaterService<E, DTO> service) {
    this.service = service;
  }

  protected abstract String getURI();

  protected abstract DTO createDTO();

  protected abstract DTO createDifferentDTO();

  protected abstract Class<E> getClazz();

  protected abstract Class<E[]> getArrayClazz();

  protected ResponseEntity<E> postEntity() {
    return testRestTemplate.postForEntity(getURI(), createDTO(), getClazz());
  }

  protected ResponseEntity<E> postEntity(DTO dto) {
    return testRestTemplate.postForEntity(getURI(), dto, getClazz());
  }

  protected ResponseEntity<LinkedHashMap> postBadEntity(DTO dto) {
    return testRestTemplate.postForEntity(getURI(), dto, LinkedHashMap.class);
  }

  protected ResponseEntity<E> getEntity(long id) {
    return testRestTemplate.getForEntity(getURI() + "/" + id, getClazz());
  }

  protected ResponseEntity<LinkedHashMap> getBadEntity(long id) {
    return testRestTemplate.getForEntity(getURI() + "/" + id, LinkedHashMap.class);
  }

  protected ResponseEntity<E[]> getAllEntities() {
    return testRestTemplate.getForEntity(getURI(), getArrayClazz());
  }

  protected void removeEntity(long id) {
    testRestTemplate.delete(getURI() + "/" + id);
  }

  private long getId(ResponseEntity<E> response) {
    return Objects.requireNonNull(response.getBody()).getId();
  }

  @AfterEach
  private void BaseControllerTestCleanup() {
    service.drop();
  }

  @Test
  void givenValidDTO_whenPostCalled_returnsStatusOK() {
    ResponseEntity<E> response = postEntity();
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void whenPostCalled_returnsEntityInBody() {
    assertTrue(postEntity().getBody() instanceof E);
  }

  @Test
  public void whenGetOneCalled_returnsStatusOK() {
    ResponseEntity<E> response = postEntity();
    assertEquals(HttpStatus.OK, getEntity(getId(response)).getStatusCode());
  }

  @Test
  public void whenGetOneCalled_returnsEntity() {
    ResponseEntity<E> response = postEntity();
    assertEquals(response.getBody(), getEntity(getId(response)).getBody());
  }

  @Test
  public void givenInvalidId_whenGetOneCalled_returnsStatusNotFound() {
    assertEquals(HttpStatus.NOT_FOUND, getEntity(500).getStatusCode());
  }

  @Test
  public void givenInvalidId_whenGetOneCalled_returnsCorrectErrorMessage() {
    LinkedHashMap resp = getBadEntity(500).getBody();
    assertTrue(((String) resp.get("message")).contains("No entity with this id found"));
  }

  @Test
  public void givenInvalidId_whenGetOneCalled_returnsBadRequest() {
    assertEquals(HttpStatus.NOT_FOUND, getEntity(service.count() + 100).getStatusCode());
  }

  @Test
  public void whenGetAllCalled_returnsStatusOK() {
    assertEquals(HttpStatus.OK, getAllEntities().getStatusCode());
  }

  @Test
  public void whenGetAllCalled_returnsListOfEntities() {
    postEntity();
    ResponseEntity<E[]> response = getAllEntities();
    assertFalse(response.getBody().length <= 0);
    for (E entity : Objects.requireNonNull(response.getBody())) {
      assertNotNull(entity);
    }
  }

  @Test
  public void givenNoEntities_whenGetAllCalled_ReturnsEmptyList() {
    assertEquals(0, Objects.requireNonNull(getAllEntities().getBody()).length);
  }

  @Test
  public void givenValidIdAndValidDto_whenPosted_returnsStatusOK() {
    ResponseEntity<E> response = postEntity();

    DTO dto = createDTO();
    dto.setId(getId(response));

    assertEquals(HttpStatus.OK, postEntity(dto).getStatusCode());
  }

  @Test
  public void givenValidIdAndValidDto_whenPosted_UpdatesEntity() {
    ResponseEntity<E> response = postEntity();

    long count = service.count();

    DTO dto = createDifferentDTO();
    dto.setId(getId(response));

    assertEquals(count, service.count());
    assertNotEquals(response.getBody(), postEntity(dto).getBody());
  }

  @Test
  public void givenValidId_whenDeleteCalled_RemovesEntity() {
    service.drop();
    ResponseEntity<E> response = postEntity();
    long count = service.count();
    long id = getId(response);

    removeEntity(id);

    assertEquals(count - 1, service.count());
    assertEquals(HttpStatus.NOT_FOUND, getEntity(id).getStatusCode());
  }
}
