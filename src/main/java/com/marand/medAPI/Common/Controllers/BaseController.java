package com.marand.medAPI.Common.Controllers;

import com.marand.medAPI.Common.DTOs.BaseDTO;
import com.marand.medAPI.Common.ExceptionHandlers.ControllerExceptionHandler;
import com.marand.medAPI.Common.Objects.BaseDataObject;
import com.marand.medAPI.Common.Services.UpdaterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

public abstract class BaseController<E extends BaseDataObject, DTO extends BaseDTO>
    extends ControllerExceptionHandler {

  private UpdaterService<E, DTO> service;

  protected BaseController(UpdaterService<E, DTO> service) {
    this.service = service;
  }

  @ApiOperation(value = "Returns list containing everything in the database")
  @GetMapping("")
  public List<E> getAll() {
    return service.findAll();
  }

  @ApiOperation(value = "Returns one, if id is valid")
  @GetMapping("/{id}")
  public E getOne(@PathVariable long id) {
    return service.findOne(id);
  }

  @ApiOperation(value = "Saves or updates if entity already exists")
  @PostMapping("")
  public E save(@Valid @RequestBody DTO dto) {
    return service.save(dto);
  }

  @ApiOperation(value = "If id is valid, removes entity with that id")
  @DeleteMapping("/{id}")
  public void removeOne(@PathVariable long id) {
    service.remove(id);
  }
}
