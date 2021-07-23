package com.marand.medAPI.Patient;

import com.marand.medAPI.Common.Controllers.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("patients")
@Api(tags = "Patient")
public class PatientController extends BaseController<Patient, PatientDTO> {

  protected PatientController(PatientService service) {
    super(service);
  }
}
