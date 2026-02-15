package com.medical.app.exams.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;


@Entity
@Table(name = "medical_exams", schema = "public")
@Getter
public class MedicalExam {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", referencedColumnName = "id", nullable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @Column(name = "exam_type", length = 100)
    private String examType;

    @Column(name = "status", length = 50, nullable = false)
    private String status = "pending"; // Default value

    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    @Column(name = "notes", columnDefinition = "text")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    protected MedicalExam() {}
    
    public MedicalExam(Organization organization) {
        this.organization = organization;
    }

    
    public MedicalExam(Organization organization, Patient patient, String examType,
                       String status, LocalDateTime requestedAt, String notes) {
        this.organization = organization;
        this.patient = patient;
        this.examType = examType;
        if (status != null) this.status = status; 
        this.requestedAt = requestedAt;
        this.notes = notes;
    }

    
    public void assignPatient(Patient patient) {
        this.patient = patient;
    }

    public void updateExamDetails(String examType, LocalDateTime requestedAt, String notes) {
        if (examType != null) this.examType = examType;
        if (requestedAt != null) this.requestedAt = requestedAt;
        if (notes != null) this.notes = notes;
    }

    public void changeStatus(String newStatus) {
        if (newStatus != null && !newStatus.isBlank()) {
            this.status = newStatus;
        }
    }

    
    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
    
    public void restore() {
        this.deletedAt = null;
    }


}
