package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.Repository.AgentRepository;
import com.spring_boot.CSTS.Repository.TicketRepository;
import com.spring_boot.CSTS.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class AgentService {
    private final AgentRepository agentRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public AgentService(AgentRepository agentRepository, TicketRepository ticketRepository) {
        this.agentRepository = agentRepository;
        this.ticketRepository = ticketRepository;
    }
    public boolean updateTicketStatus(Long ticketId, String status) {
        return ticketRepository.findById(ticketId)
                .map(ticket -> {
                    try {
                        ticket.setStatus(Ticket.Status.valueOf(status.toUpperCase()));
                        ticketRepository.save(ticket);
                        return true;
                    } catch (IllegalArgumentException e) {

                        return false;
                    }
                }).orElse(false);
    }

    public List<Ticket> getAllUserTickets(){
        return ticketRepository.findAll();
    }

}
