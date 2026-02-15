package com.medical.app.exams.domain.entities;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReferenceRangeRepositoryContract {
    ReferenceRange save(ReferenceRange referenceRange);

    
    void deleteById(UUID id);

    
    Optional<ReferenceRange> findByExamTypeAndParameter(String examType, String parameter);

    
    List<ReferenceRange> findByOrganizationId(UUID organizationId);

    
    List<ReferenceRange> findByExamType(String examType);

    
    List<ReferenceRange> findByParameter(String parameter);

    
    List<ReferenceRange> findByOrganizationIdAndExamType(UUID organizationId, String examType);

    
    List<ReferenceRange> findByOrganizationIdAndParameter(UUID organizationId, String parameter);

    
    boolean existsByExamTypeAndParameter(String examType, String parameter);

    
    List<ReferenceRange> findAll();

}
