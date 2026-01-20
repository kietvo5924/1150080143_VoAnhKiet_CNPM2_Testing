package com.example.organizationapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "directors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer directorId;

    @Column(name = "director_name", nullable = false)
    private String directorName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    // QUAN TRỌNG: Liên kết với bảng Organization
    @ManyToOne
    @JoinColumn(name = "org_id", nullable = false) // Khóa ngoại
    private Organization organization;

    // Constructor tiện dùng
    public Director(String directorName, String phone, String email, Organization organization) {
        this.directorName = directorName;
        this.phone = phone;
        this.email = email;
        this.organization = organization;
    }
}