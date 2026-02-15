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
@Table(name = "patients", schema = "public")
@Getter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", referencedColumnName = "id", nullable = false)
    private Organization organization;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String ssn;

    private String name;

    private LocalDateTime dob;

    private String gender;

    private String address;

    private String contact;

    protected Patient() {}

    public Patient(Organization organization) {
        this.organization = organization;
    }

    public Patient(Organization organization, String cpf, String ssn, String name,
                   LocalDateTime dob, String gender, String address, String contact) {
        this.organization = organization;
        this.cpf = cpf;
        this.ssn = ssn;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.contact = contact;
    }

    public void updatePersonalInfo(String name, LocalDateTime dob, String gender) {
        this.name = name;
        this.dob = dob;
        this.gender = gender;
    }

    public void updateContactInfo(String address, String contact) {
        this.address = address;
        this.contact = contact;
    }

    public void updateCpf(String cpf) {
        this.cpf = cpf;
    }

    public void updateSsn(String ssn) {
        this.ssn = ssn;
    }
    
    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
    
    public void restore() {
        this.deletedAt = null;
    }

}
