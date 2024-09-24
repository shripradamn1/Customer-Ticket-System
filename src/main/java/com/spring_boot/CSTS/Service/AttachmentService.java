package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.Repository.AttachmentRepository;
import com.spring_boot.CSTS.model.Attachment;
import com.spring_boot.CSTS.model.Ticket;
import com.spring_boot.CSTS.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private TicketRepository ticketRepository;

    // Save attachment to a ticket
    public Attachment saveAttachment(Long ticketId, MultipartFile file) throws IOException {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (ticketId == null) {
            throw new IllegalArgumentException("Ticket ID must not be null");
        }

        // Save the file to disk
        String fileName = file.getOriginalFilename();
        String filePath = "C:/Users/e031906/Desktop/attachments/" + fileName;
        Path path = Paths.get(filePath);
        file.transferTo(path);

        // Create attachment entity
        Attachment attachment = new Attachment();
        attachment.setFileName(fileName);
        attachment.setFilePath(filePath);
        attachment.setTicket(ticket);

        // Save attachment to database
        return attachmentRepository.save(attachment);
    }

    // Get all attachments linked to a specific ticket
    public List<Attachment> getAttachmentsByTicketId(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        return attachmentRepository.findByTicket(ticket);
    }

    // Get the first attachment linked to a specific ticket
    public Attachment getFirstAttachmentByTicketId(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        List<Attachment> attachments = attachmentRepository.findByTicket(ticket);
        return attachments.isEmpty() ? null : attachments.get(0); // Return the first attachment if available
    }

    // Get attachment by ID
    public Attachment getAttachment(Long attachmentId) {
        return attachmentRepository.findById(attachmentId).orElseThrow(() -> new RuntimeException("Attachment not found"));
    }
}