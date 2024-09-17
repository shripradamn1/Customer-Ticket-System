package com.spring_boot.CSTS.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring_boot.CSTS.Repository.AttachmentRepository;
import com.spring_boot.CSTS.Repository.TicketRepository;
import com.spring_boot.CSTS.model.Attachment;
import com.spring_boot.CSTS.model.Ticket;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final TicketRepository ticketRepository;

    public AttachmentService(AttachmentRepository attachmentRepository, TicketRepository ticketRepository) {
        this.attachmentRepository = attachmentRepository;
        this.ticketRepository = ticketRepository;
    }

    public Attachment addAttachment(Long ticketId, MultipartFile file) throws IOException {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        // Store the file details in the Attachment entity
        Attachment attachment = new Attachment(
            file.getOriginalFilename(),
            file.getContentType(),
            file.getBytes(),
            ticket
        );

        return attachmentRepository.save(attachment);
    }

    public List<Attachment> getAttachmentsByTicket(Long ticketId) {
        return attachmentRepository.findByTicketId(ticketId);
    }

    public Optional<Attachment> getAttachment(Long attachmentId) {
        return attachmentRepository.findById(attachmentId);
    }
}
