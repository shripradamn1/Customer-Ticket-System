package com.spring_boot.CSTS.Repository;

import com.spring_boot.CSTS.model.TicketLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketLogRepository extends JpaRepository<TicketLog, Long> {
    List<TicketLog> findByTicketId(Long ticketId);
}
