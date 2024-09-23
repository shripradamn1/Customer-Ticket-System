package com.spring_boot.CSTS.Repository;

import com.spring_boot.CSTS.model.SupportAgent;
import com.spring_boot.CSTS.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByAssignedTo(Optional<SupportAgent> assignedTo);
    List<Ticket> findByUserId(Long userId);
    Ticket findByTitle(String title);
}
