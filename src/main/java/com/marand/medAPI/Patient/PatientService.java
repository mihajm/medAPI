package com.marand.medAPI.Patient;

import com.marand.medAPI.Common.Services.UpdaterService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class PatientService extends UpdaterService<Patient, PatientDTO> {

  protected PatientService(PatientRepository repo, ApplicationContext context) {
    super(repo, context);
  }

  protected Patient create() {
    return new Patient();
  }
}
