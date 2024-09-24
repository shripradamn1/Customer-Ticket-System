package com.spring_boot.CSTS.Controller;

import com.spring_boot.CSTS.Service.AttachmentService;
import com.spring_boot.CSTS.model.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    // Fetch all attachments linked to a ticket by its ID
    @GetMapping("/{ticketId}/attachments")
    public ResponseEntity<List<Attachment>> getAttachmentsByTicketId(@PathVariable Long ticketId) {
        try {
            List<Attachment> attachments = attachmentService.getAttachmentsByTicketId(ticketId);
            return new ResponseEntity<>(attachments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Download the first attachment linked to a ticket by ticketId
    @GetMapping("/{ticketId}/attachments/download")
    public ResponseEntity<byte[]> downloadAttachmentByTicketId(@PathVariable Long ticketId) throws IOException {
        try {
            Attachment attachment = attachmentService.getFirstAttachmentByTicketId(ticketId);
            if (attachment == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Path filePath = Paths.get(attachment.getFilePath());
            byte[] fileData = Files.readAllBytes(filePath);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", attachment.getFileName());
            headers.setContentLength(fileData.length);

            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
