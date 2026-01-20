package com.example.organizationapp;

import com.example.organizationapp.ui.OrganizationForm;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class OrganizationAppApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(OrganizationAppApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);

        SwingUtilities.invokeLater(() -> {
            OrganizationForm form = context.getBean(OrganizationForm.class);
            form.setVisible(true);
        });
    }
}