package com.marand.medAPI.Doctor;

import com.marand.medAPI.Common.Controllers.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("doctors")
@Api(tags = "Doctor")
public class DoctorController extends BaseController<Doctor, DoctorDTO> {

  protected DoctorController(DoctorService service) {
    super(service);
  }
}
