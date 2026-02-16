package com.medical.app.exams.domain.contracts;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;

import com.medical.app.exams.domain.entities.MedicalExam;

public interface MedicalExamRepositoryContract {
    MedicalExam save(MedicalExam exam);
    void deleteById(UUID id); 
    
    List<MedicalExam> findByOrganizationId(UUID organizationId);
    Page<MedicalExam> findByOrganizationId(UUID organizationId, Pageable pageable);
    
    List<MedicalExam> findByPatientId(UUID patientId);
    Page<MedicalExam> findByPatientId(UUID patientId, Pageable pageable);
    
    List<MedicalExam> findByOrganizationIdAndPatientId(UUID organizationId, UUID patientId);
    Page<MedicalExam> findByOrganizationIdAndPatientId(UUID organizationId, UUID patientId, Pageable pageable);

    
    List<MedicalExam> findByStatus(String status);
    Page<MedicalExam> findByStatus(String status, Pageable pageable);

    
    List<MedicalExam> findByOrganizationIdAndStatus(UUID organizationId, String status);
    Page<MedicalExam> findByOrganizationIdAndStatus(UUID organizationId, String status, Pageable pageable);

    
    List<MedicalExam> findByRequestedAtBetween(LocalDateTime start, LocalDateTime end);
    Page<MedicalExam> findByRequestedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    
    List<MedicalExam> findByOrganizationIdAndRequestedAtBetween(UUID organizationId, LocalDateTime start, LocalDateTime end);
    Page<MedicalExam> findByOrganizationIdAndRequestedAtBetween(UUID organizationId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    
    List<MedicalExam> findByExamType(String examType);
    Page<MedicalExam> findByExamType(String examType, Pageable pageable);

    
    List<MedicalExam> findByOrganizationIdAndExamType(UUID organizationId, String examType);
    Page<MedicalExam> findByOrganizationIdAndExamType(UUID organizationId, String examType, Pageable pageable);

    
    List<MedicalExam> findByOrganizationIdAndPatientIsNull(UUID organizationId);
    Page<MedicalExam> findByOrganizationIdAndPatientIsNull(UUID organizationId, Pageable pageable);

    
    List<MedicalExam> findUpcomingExams(UUID organizationId, LocalDateTime fromDate, LocalDateTime toDate);
    Page<MedicalExam> findUpcomingExams(UUID organizationId, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);

    
    List<Object[]> countByStatusGrouped(UUID organizationId);

    
    int bulkUpdateStatus(List<UUID> examIds, String newStatus);

    
    boolean existsByIdAndDeletedAtIsNull(UUID id);

    
    Optional<String> findPatientNameByExamId(UUID examId);


}
