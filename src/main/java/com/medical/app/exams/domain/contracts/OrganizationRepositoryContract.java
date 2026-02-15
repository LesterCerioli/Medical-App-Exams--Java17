package com.medical.app.exams.domain.contracts;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.medical.app.exams.domain.entities.Organization;

public interface OrganizationRepositoryContract {
     Organization save(Organization organization);
    void deleteById(UUID id); 

    
    Optional<Organization> findActiveByName(String name);

    
    List<Organization> findAllActive();

    
    boolean existsActiveByName(String name);

    
    Optional<Organization> findActiveById(UUID id);

    
    void softDeleteById(UUID id);

    
    void restoreById(UUID id);


}
