package com.spring_boot.CSTS.Initialization;

import com.spring_boot.CSTS.Repository.UserRepository;
import com.spring_boot.CSTS.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AdminUserInitializer implements CommandLineRunner {

    @Value("${admin.user.username}")
    private String adminUsername;

    @Value("${admin.user.password}")
    private String adminPassword;

    @Value("${admin.user.email}")
    private String adminEmail;



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if the user table is empty
        if (userRepository.count() == 0) {
            // Create a new admin user
            User adminUser = new User();
            adminUser.setUsername(adminUsername);
            adminUser.setEmail(adminEmail);

            adminUser.setPassword(passwordEncoder.encode(adminPassword));  // Store encoded password
            adminUser.setRole("ROLE_ADMIN");
            userRepository.save(adminUser);
            System.out.println("Admin user created successfully !!");
        } else {
            System.out.println("User table is not empty. No admin user created.");
        }
    }
}