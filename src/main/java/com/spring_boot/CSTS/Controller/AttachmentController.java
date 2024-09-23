package com.spring_boot.CSTS.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.spring_boot.CSTS.Service.AttachmentService;
import com.spring_boot.CSTS.model.Attachment;

import java.util.List;

@RestController
@RequestMapping("/api/tickets/{ticketId}/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    // Upload an attachment to a ticket
    @PostMapping("/upload")
    public ResponseEntity<Attachment> uploadAttachment(@PathVariable Long ticketId, @RequestParam("file") MultipartFile file) {
        try {
            Attachment attachment = attachmentService.addAttachment(ticketId, file);
            return ResponseEntity.ok(attachment);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Get all attachments for a specific ticket
    @GetMapping
    public ResponseEntity<List<Attachment>> getAttachments(@PathVariable Long ticketId) {
        List<Attachment> attachments = attachmentService.getAttachmentsByTicket(ticketId);
        return ResponseEntity.ok(attachments);
    }

    // Download a specific attachment by attachment ID
    @GetMapping("/{attachmentId}/download")
    public ResponseEntity<byte[]> downloadAttachment(@PathVariable Long ticketId, @PathVariable Long attachmentId) {
        Attachment attachment = attachmentService.getAttachment(attachmentId)
                .orElseThrow(() -> new RuntimeException("Attachment not found"));

        // Check if the attachment belongs to the correct ticket
        if (!attachment.getTicket().getId().equals(ticketId)) {
            throw new RuntimeException("Attachment does not belong to this ticket");
        }

        // Return the file as a downloadable response
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + attachment.getFileName() + "\"")
                .body(attachment.getData());
    }
}
