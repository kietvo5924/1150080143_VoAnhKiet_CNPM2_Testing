package com.example.organizationapp.service;

import com.example.organizationapp.entity.Organization;
import com.example.organizationapp.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository repository;

    public boolean isDirectorButtonEnabled = false;

    public String saveOrganization(Organization org) {
        isDirectorButtonEnabled = false;

        if (org.getOrgName() == null || org.getOrgName().trim().isEmpty()) {
            return "Error: Organization Name cannot be empty";
        }

        if (org.getOrgName().length() < 3 || org.getOrgName().length() > 255) {
            return "Error: Organization Name must be between 3 and 255 characters";
        }

        if (repository.existsByOrgName(org.getOrgName())) {
            return "Error: Organization Name already exists";
        }

        if (org.getPhone() != null && !org.getPhone().isEmpty()) {
            if (!org.getPhone().matches("^[0-9]{9,12}$")) {
                return "Error: Phone must contains only digits and length 9-12";
            }
        }

        if (org.getEmail() != null && !org.getEmail().isEmpty()) {
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            if (!Pattern.matches(emailRegex, org.getEmail())) {
                return "Error: Invalid Email format";
            }
        }

        try {
            repository.save(org);
            isDirectorButtonEnabled = true;
            return "Save successfully";
        } catch (Exception e) {
            return "Error: System error when saving to database";
        }
    }
}