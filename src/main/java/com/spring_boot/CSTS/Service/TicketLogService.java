package com.spring_boot.CSTS.Service;
 
import com.spring_boot.CSTS.Repository.TicketLogRepository;
import com.spring_boot.CSTS.Repository.TicketRepository;
import com.spring_boot.CSTS.model.SupportAgent;
import com.spring_boot.CSTS.model.Ticket;
import com.spring_boot.CSTS.model.TicketLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.util.List;
 
@Service
public class TicketLogService {
 
    @Autowired
    private TicketLogRepository ticketLogRepository;
 
    @Autowired
    private TicketRepository ticketRepository;
 
    // Add a log to a ticket
    @Transactional
    public TicketLog addTicketLog(Long ticketId, TicketLog log) {
        if (log.getMessage() == null || log.getMessage().isEmpty()) {
            throw new IllegalArgumentException("Log message cannot be empty");
        }
 
        if (log.getUpdatedBy() == null) {
            throw new IllegalArgumentException("Log must have an 'updatedBy' field");
        }
 
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
 
        log.setTicket(ticket);
        return ticketLogRepository.save(log);
    }
 
    // Automatically create a log during ticket update
    @Transactional
    public void logTicketUpdate(Long ticketId, SupportAgent agent, String message) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
 
        TicketLog log = new TicketLog();
        log.setMessage(message);
        log.setUpdatedBy(agent);
        log.setTicket(ticket);
 
        ticketLogRepository.save(log);
    }
 
    // Get logs by ticketId
    public List<TicketLog> getLogsByTicketId(Long ticketId) {
        return ticketLogRepository.findByTicketId(ticketId);
    }
}