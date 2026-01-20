package com.example.organizationapp.repository;

import com.example.organizationapp.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    boolean existsByOrgName(String orgName);
}