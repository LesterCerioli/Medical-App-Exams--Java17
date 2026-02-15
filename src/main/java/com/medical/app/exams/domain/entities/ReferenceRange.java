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
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;


@Entity
@Table(name = "reference_ranges", schema = "public",
       uniqueConstraints = @UniqueConstraint(columnNames = {"exam_type", "parameter"}))
@Getter
public class ReferenceRange {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "exam_type", nullable = false, length = 100)
    private String examType;

    @Column(name = "parameter", nullable = false, length = 100)
    private String parameter;

    @Column(name = "min_normal", nullable = false)
    private Double minNormal;

    @Column(name = "max_normal", nullable = false)
    private Double maxNormal;

    @Column(name = "unit", nullable = false, length = 50)
    private String unit;

    @Column(name = "conditions", columnDefinition = "jsonb")
    private String conditions; // JSON stored as String

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizations_id", referencedColumnName = "id")
    private Organization organization;
    
    protected ReferenceRange() {}
    
    public ReferenceRange(String examType, String parameter, Double minNormal,
                          Double maxNormal, String unit) {
        this.examType = examType;
        this.parameter = parameter;
        this.minNormal = minNormal;
        this.maxNormal = maxNormal;
        this.unit = unit;
    }
    
    public ReferenceRange(Organization organization, String examType, String parameter,
                          Double minNormal, Double maxNormal, String unit) {
        this(examType, parameter, minNormal, maxNormal, unit);
        this.organization = organization;
    }
    
    public ReferenceRange(Organization organization, String examType, String parameter,
                          Double minNormal, Double maxNormal, String unit,
                          String conditions) {
        this(organization, examType, parameter, minNormal, maxNormal, unit);
        this.conditions = conditions;
    }
    
    public void updateRange(Double minNormal, Double maxNormal, String unit) {
        if (minNormal != null) this.minNormal = minNormal;
        if (maxNormal != null) this.maxNormal = maxNormal;
        if (unit != null) this.unit = unit;
    }

    public void updateConditions(String conditions) {
        this.conditions = conditions;
    }

    public void setOrganization(Organization organization) {
        // Only allowed if organization is not already set (to avoid reassignment)
        if (this.organization == null && organization != null) {
            this.organization = organization;
        }
    }

}
