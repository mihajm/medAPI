package com.marand.medAPI.Disease;

import com.marand.medAPI.Common.Controllers.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("diseases")
@Api(tags = "Disease")
public class DiseaseController extends BaseController<Disease, DiseaseDTO> {

  protected DiseaseController(DiseaseService service) {
    super(service);
  }
}
