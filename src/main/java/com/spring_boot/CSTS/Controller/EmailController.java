package com.spring_boot.CSTS.Controller;

import com.spring_boot.CSTS.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // Existing POST method
    @PostMapping("/send")
    public ResponseEntity<String> sendEmailPost(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String text) {

        try {
            emailService.sendSimpleMessage(to, subject, text);
            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending email: " + e.getMessage());
        }
    }

    // New GET method
    @GetMapping("/get-email")
    public ResponseEntity<String> sendEmailGet(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String text) {

        try {
            emailService.sendSimpleMessage(to, subject, text);
            return ResponseEntity.ok("Email received successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while receiving email: " + e.getMessage());
        }
    }
}
