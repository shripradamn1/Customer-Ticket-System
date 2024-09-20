package com.spring_boot.CSTS.Controller;

import com.spring_boot.CSTS.Service.AttachmentService;
import com.spring_boot.CSTS.model.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/tickets/{ticketId}/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    // Upload an attachment to a ticket
    @PostMapping("/upload")
    public ResponseEntity<Attachment> uploadAttachment(@PathVariable Long ticketId, @RequestParam("file") MultipartFile file) {
        try {
            Attachment attachment = attachmentService.saveAttachment(ticketId, file);
            return new ResponseEntity<>(attachment, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Download an attachment by attachment ID
    @GetMapping("/download/{attachmentId}")
    public ResponseEntity<byte[]> downloadAttachment(@PathVariable Long attachmentId) throws IOException {
        Attachment attachment = attachmentService.getAttachment(attachmentId);
        Path filePath = Paths.get(attachment.getFilePath());
        byte[] fileData = Files.readAllBytes(filePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", attachment.getFileName());

        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }
}
