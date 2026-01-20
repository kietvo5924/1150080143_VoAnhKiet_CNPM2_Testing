package com.example.organizationapp.service;

import com.example.organizationapp.entity.Director;
import com.example.organizationapp.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class DirectorService {

    @Autowired
    private DirectorRepository repository;

    public String saveDirector(Director director) {
        // 1. Validate Tên
        if (director.getDirectorName() == null || director.getDirectorName().trim().isEmpty()) {
            return "Error: Tên giám đốc không được để trống!";
        }

        // 2. Validate Phone
        if (director.getPhone() != null && !director.getPhone().isEmpty()) {
            if (!director.getPhone().matches("^[0-9]{9,12}$")) {
                return "Error: Số điện thoại phải là số và dài từ 9-12 ký tự!";
            }
        }

        // 3. Validate Email
        if (director.getEmail() != null && !director.getEmail().isEmpty()) {
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            if (!Pattern.matches(emailRegex, director.getEmail())) {
                return "Error: Email không đúng định dạng!";
            }
        }

        try {
            repository.save(director);
            return "Save successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Lỗi hệ thống khi lưu dữ liệu!";
        }
    }
}