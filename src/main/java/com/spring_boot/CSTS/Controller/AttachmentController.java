package com.spring_boot.CSTS.Controller;

import com.spring_boot.CSTS.model.Attachment;
import com.spring_boot.CSTS.Service.AttachmentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/tickets/{ticketId}/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    // Add an attachment to a specific ticket
    @PostMapping
    public ResponseEntity<Attachment> uploadAttachment(@PathVariable Long ticketId, @RequestParam("file") MultipartFile file) {
        Attachment savedAttachment = attachmentService.addAttachment(ticketId, file);
        return ResponseEntity.ok(savedAttachment);
    }

    // Get all attachments for a specific ticket
//    @GetMapping
//    public ResponseEntity<List<Attachment>> getAttachments(@PathVariable Long ticketId) {
//        List<Attachment> attachments = attachmentService.getAttachment(ticketId);
//        return ResponseEntity.ok(attachments);
//    }
}
