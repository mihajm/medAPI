package com.marand.medAPI.Doctor;

import com.marand.medAPI.Common.Services.UpdaterService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class DoctorService extends UpdaterService<Doctor, DoctorDTO> {

  protected DoctorService(DoctorRepository repo, ApplicationContext context) {
    super(repo, context);
  }

  protected Doctor create() {
    return new Doctor();
  }
}
