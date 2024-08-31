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

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(
            @RequestParam String to, 
            @RequestParam String subject, 
            @RequestParam String body) {
        emailService.sendSimpleMessage(to, subject, body);
        return ResponseEntity.ok("Email sent successfully.");
    }
}
