package com.medical.app.exams.domain.contracts;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;

import com.medical.app.exams.domain.entities.ExamScheduling;

public interface ExamSchedulingRepositoryContract {
    ExamScheduling save(ExamScheduling scheduling);
    void deleteById(UUID id);

    
    Optional<ExamScheduling> findByExamNumber(Integer examNumber);
    
    List<ExamScheduling> findByOrganizationId(UUID organizationId);
    Page<ExamScheduling> findByOrganizationId(UUID organizationId, Pageable pageable);

    
    List<ExamScheduling> findByPatientId(UUID patientId);
    Page<ExamScheduling> findByPatientId(UUID patientId, Pageable pageable);

    
    List<ExamScheduling> findByOrganizationIdAndPatientId(UUID organizationId, UUID patientId);
    Page<ExamScheduling> findByOrganizationIdAndPatientId(UUID organizationId, UUID patientId, Pageable pageable);

    
    List<ExamScheduling> findByStatus(String status);
    Page<ExamScheduling> findByStatus(String status, Pageable pageable);

    
    List<ExamScheduling> findByScheduledDateBetween(LocalDateTime start, LocalDateTime end);
    Page<ExamScheduling> findByScheduledDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    
    List<ExamScheduling> findByOrganizationIdAndScheduledDateBetween(UUID organizationId, LocalDateTime start, LocalDateTime end);
    Page<ExamScheduling> findByOrganizationIdAndScheduledDateBetween(UUID organizationId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    
    List<ExamScheduling> findUpcomingExams(UUID organizationId, LocalDateTime fromDate, LocalDateTime toDate);
    Page<ExamScheduling> findUpcomingExams(UUID organizationId, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);

    
    List<ExamScheduling> findByPatientName(String patientName, UUID organizationId);
    Page<ExamScheduling> findByPatientName(String patientName, UUID organizationId, Pageable pageable);
    
        
    boolean existsByExamNumber(Integer examNumber);

}
