package com.medical.app.exams.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "analysis_logs", schema = "public")
@Data
public class AnalysisLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_id", referencedColumnName = "id")
    private ExamAnalysis analysis;

    @Column(name = "action", nullable = false, length = 50)
    private String action;

    @Column(name = "details", columnDefinition = "jsonb")
    private String details; // JSON armazenado como String (pode ser convertido para Map/List no serviço)

    @CreationTimestamp
    @Column(name = "performed_at")
    private LocalDateTime performedAt;

    @Column(name = "performed_by", length = 100)
    private String performedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizations_id", referencedColumnName = "id")
    private Organization organization;

    protected AnalysisLog() {}

    public AnalysisLog(ExamAnalysis analysis, String action, Organization organization) {
        this(analysis, action, null, null, organization);
    }

    public AnalysisLog(ExamAnalysis analysis, String action, String details, String performedBy, Organization organization) {
        this.analysis = analysis;
        this.action = action;
        this.details = details;
        this.performedBy = performedBy;
        this.organization = organization;
    }
}