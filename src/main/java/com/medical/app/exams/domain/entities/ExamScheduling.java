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
@Table(name = "exam_scheduling", schema = "public")
@Getter
public class ExamScheduling {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", referencedColumnName = "id", nullable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @Column(name = "exam_name", nullable = false, length = 200)
    private String examName;

    @Column(name = "exam_description", columnDefinition = "text")
    private String examDescription;

    @Column(name = "scheduled_date", nullable = false)
    private LocalDateTime scheduledDate;

    @Column(name = "scheduled_end_date")
    private LocalDateTime scheduledEndDate;

    @Column(name = "exam_duration_minutes")
    private Integer examDurationMinutes;

    @Column(name = "status", length = 50)
    private String status = "scheduled"; // valor padrão

    @Column(name = "max_participants")
    private Integer maxParticipants;

    @Column(name = "location", length = 200)
    private String location;

    @Column(name = "instructions", columnDefinition = "text")
    private String instructions;

    @Column(name = "exam_number")
    private Integer examNumber; // gerado pelo serviço
    
    protected ExamScheduling() {}
    
    public ExamScheduling(Organization organization, String examName, LocalDateTime scheduledDate) {
        this.organization = organization;
        this.examName = examName;
        this.scheduledDate = scheduledDate;
    }

    
    public ExamScheduling(Organization organization, Patient patient, String examName,
                          String examDescription, LocalDateTime scheduledDate,
                          LocalDateTime scheduledEndDate, Integer examDurationMinutes,
                          String status, Integer maxParticipants, String location,
                          String instructions, Integer examNumber) {
        this.organization = organization;
        this.patient = patient;
        this.examName = examName;
        this.examDescription = examDescription;
        this.scheduledDate = scheduledDate;
        this.scheduledEndDate = scheduledEndDate;
        this.examDurationMinutes = examDurationMinutes;
        if (status != null) this.status = status;
        this.maxParticipants = maxParticipants;
        this.location = location;
        this.instructions = instructions;
        this.examNumber = examNumber;
    }

    
    public void assignPatient(Patient patient) {
        this.patient = patient;
    }

    public void updateSchedulingDetails(String examName, String examDescription,
                                         LocalDateTime scheduledDate, LocalDateTime scheduledEndDate,
                                         Integer examDurationMinutes, Integer maxParticipants,
                                         String location, String instructions) {
        if (examName != null) this.examName = examName;
        if (examDescription != null) this.examDescription = examDescription;
        if (scheduledDate != null) this.scheduledDate = scheduledDate;
        if (scheduledEndDate != null) this.scheduledEndDate = scheduledEndDate;
        if (examDurationMinutes != null) this.examDurationMinutes = examDurationMinutes;
        if (maxParticipants != null) this.maxParticipants = maxParticipants;
        if (location != null) this.location = location;
        if (instructions != null) this.instructions = instructions;
    }

    public void changeStatus(String newStatus) {
        if (newStatus != null && !newStatus.isBlank()) {
            this.status = newStatus;
        }
    }
    
    public void setExamNumber(Integer examNumber) {
        if (this.examNumber == null && examNumber != null) {
            this.examNumber = examNumber;
        }
    }
    
    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
    
    public void restore() {
        this.deletedAt = null;
    }


}
