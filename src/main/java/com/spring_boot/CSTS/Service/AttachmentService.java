package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.Repository.AttachmentRepository;
import com.spring_boot.CSTS.Repository.TicketRepository;
import com.spring_boot.CSTS.model.Attachment;
import com.spring_boot.CSTS.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class AttachmentService {

    private static final long MAX_FILE_SIZE = 1048576L; // Example: 1 MB max size

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private TicketRepository ticketRepository;

    // Add a new attachment to a ticket
    public Attachment addAttachment(Long ticketId, MultipartFile file) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File is too large. Max size allowed is " + MAX_FILE_SIZE + " bytes");
        }

        Attachment attachment = new Attachment();
        attachment.setTicket(ticket);
        attachment.setFileName(file.getOriginalFilename());
        attachment.setFileType(file.getContentType());

        try {
            attachment.setData(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file data", e);
        }

        return attachmentRepository.save(attachment);
    }

    // Get an attachment by ID
    public Optional<Attachment> getAttachment(Long attachmentId) {
        return attachmentRepository.findById(attachmentId);
    }

    // Delete an attachment by ID
    public void deleteAttachment(Long attachmentId) {
        attachmentRepository.deleteById(attachmentId);
    }
}
