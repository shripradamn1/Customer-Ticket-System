package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.Repository.AgentRepository;
import com.spring_boot.CSTS.Repository.TicketRepository;
import com.spring_boot.CSTS.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Boolean setTicketPriority(Long ticketId,String priority){
        Optional<Ticket> optionalTicket=ticketRepository.findById(ticketId);

        if(optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            try {
                ticket.setPriority(Ticket.Priority.valueOf(priority.toUpperCase()));
                ticketRepository.save(ticket);
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return false;
    }

}
