package com.medical.app.exams.domain.contracts;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.medical.app.exams.domain.entities.Patient;

public interface PatientRepositoryContract {
    Patient save(Patient patient);

    
    void softDeleteById(UUID id);

    
    void restoreById(UUID id);

    
    Optional<Patient> findActiveById(UUID id);

    
    Optional<Patient> findActiveByCpf(String cpf);

    
    Optional<Patient> findActiveBySsn(String ssn);

    
    List<Patient> findActiveByNameAndOrganization(String name, UUID organizationId);

    
    List<Patient> findAllActiveByOrganizationId(UUID organizationId);

    
    boolean existsActiveByCpf(String cpf);

    
    boolean existsActiveBySsn(String ssn);

}
