package com.medilabo.patient.service;

import com.medilabo.patient.model.Patient;
import com.medilabo.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> findPatientById(Integer id) {
        return patientRepository.findById(id);
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Optional<Patient> updatePatient(Integer id, Patient updatedPatient) {
        return patientRepository.findById(id)
                .map(existingPatient -> {
                    existingPatient.setFirstName(updatedPatient.getFirstName());
                    existingPatient.setLastName(updatedPatient.getLastName());
                    existingPatient.setBirthDate(updatedPatient.getBirthDate());
                    existingPatient.setGender(updatedPatient.getGender());
                    existingPatient.setAddress(updatedPatient.getAddress());
                    existingPatient.setPhone(updatedPatient.getPhone());
                    return patientRepository.save(existingPatient);
                });
    }
}