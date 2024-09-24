package com.spring_boot.CSTS.Repository;

import com.spring_boot.CSTS.model.Attachment;
import com.spring_boot.CSTS.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByTicket(Ticket ticket);
}