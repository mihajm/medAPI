package com.marand.medAPI.Patient;

import com.marand.medAPI.Common.Services.ReportedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class PatientService extends ReportedService<Patient, PatientDTO> {

  @Autowired
  protected PatientService(PatientRepository repo, ApplicationContext context) {
    super(repo, context);
  }

  protected Patient create() {
    return new Patient();
  }
}
