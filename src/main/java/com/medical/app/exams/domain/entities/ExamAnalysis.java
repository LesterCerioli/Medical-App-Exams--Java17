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
import lombok.Data;


@Entity
@Table(name = "exam_analyses", schema = "public")
@Data
public class ExamAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "exam_type", nullable = false, length = 100)
    private String examType;

    @Column(name = "exam_date")
    private LocalDateTime examDate;

    @Column(name = "original_results", nullable = false, columnDefinition = "jsonb")
    private String originalResults; 

    @Column(name = "analyzed_results", columnDefinition = "jsonb")
    private String analyzedResults;

    @Column(name = "observations", columnDefinition = "jsonb")
    private String observations;

    @Column(name = "analysis_date", insertable = false, updatable = false)
    private LocalDateTime analysisDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizations_id", referencedColumnName = "id")
    private Organization organization;

    @Column(name = "exam_result", columnDefinition = "jsonb")
    private String examResult;

    protected ExamAnalysis() {}

    public ExamAnalysis(String examType, String originalResults, Organization organization) {
        this.examType = examType;
        this.originalResults = originalResults;
        this.organization = organization;
    }

    public ExamAnalysis(String examType, LocalDateTime examDate, String originalResults,
                        String analyzedResults, String observations, Organization organization,
                        String examResult) {
        this(examType, originalResults, organization);
        this.examDate = examDate;
        this.analyzedResults = analyzedResults;
        this.observations = observations;
        this.examResult = examResult;
    }
}