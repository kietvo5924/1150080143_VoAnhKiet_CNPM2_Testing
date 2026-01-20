package com.example.organizationapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "organizations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orgID;

    @Column(name = "OrgName", nullable = false, unique = true, length = 255)
    private String orgName;

    @Column(name = "Address")
    private String address;

    @Column(name = "Phone", length = 20)
    private String phone;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "CreatedDate", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    public Organization(String orgName, String address, String phone, String email) {
        this.orgName = orgName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }
}