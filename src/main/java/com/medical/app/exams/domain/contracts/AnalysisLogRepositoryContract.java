package com.medical.app.exams.domain.contracts;

import java.util.Optional;
import java.util.UUID;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;

import com.medical.app.exams.domain.entities.AnalysisLog;

public interface AnalysisLogRepositoryContract {
    AnalysisLog save(AnalysisLog log);

    Optional<AnalysisLog> findById(UUID id);

    void deleteById(UUID id);

    Page<AnalysisLog> findByAnalysisId(UUID analysisId, Pageable pageable);

    Page<AnalysisLog> findByOrganizationId(UUID organizationId, Pageable pageable);


}
