package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.Repository.AttachmentRepository;
import com.spring_boot.CSTS.model.Attachment;
import com.spring_boot.CSTS.model.Ticket;
import com.spring_boot.CSTS.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        String filePath = "C:/Users/e031760/Desktop/CaseStudy/attachments/" + fileName;
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

    // Get attachment by ID
    public Attachment getAttachment(Long attachmentId) {
        return attachmentRepository.findById(attachmentId).orElseThrow(() -> new RuntimeException("Attachment not found"));
    }
}
