package com.example.CSTS.Service;


import com.example.CSTS.Model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket createTicket(Ticket ticket) {
        ticket.setStatus("OPEN");
        return ticketRepository.save(ticket);
    }
}
