package com.medical.app.exams.domain.contracts;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.medical.app.exams.domain.entities.ExamAnalysis;

public interface ExamAnalysisRepositoryContract {
    ExamAnalysis save(ExamAnalysis analysis);
    Optional<ExamAnalysis> findById(UUID id);
    void deleteById(UUID id);

    
    List<ExamAnalysis> findByExamType(String examType);
    List<ExamAnalysis> findByExamDate(LocalDateTime examDate);
    List<ExamAnalysis> findByAnalysisDate(LocalDateTime analysisDate);
    List<ExamAnalysis> findByCreatedAt(LocalDateTime createdAt);
    List<ExamAnalysis> findByUpdatedAt(LocalDateTime updatedAt);
    List<ExamAnalysis> findByOriginalResults(String originalResults);
    
    List<ExamAnalysis> findByOrganizationId(UUID organizationId);
    List<ExamAnalysis> findByOrganizationIdAndExamType(UUID organizationId, String examType);
    List<ExamAnalysis> findByOrganizationIdAndExamResultIsNull(UUID organizationId);
    List<ExamAnalysis> findByOrganizationIdAndExamDateBetween(UUID organizationId, LocalDateTime start, LocalDateTime end);

    
    long countByOrganizationId(UUID organizationId);
    long countByOrganizationIdAndExamResultIsNull(UUID organizationId);
    
    Object[] getStatisticsByOrganizationId(UUID organizationId);

}
