
package com.spring_boot.CSTS.Repository;

import com.spring_boot.CSTS.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}

