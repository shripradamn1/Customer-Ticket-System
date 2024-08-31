package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.model.*;
import com.spring_boot.CSTS.Repository.TicketRepository;
import com.spring_boot.CSTS.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private NotificationService notificationService;

    public Ticket createTicket(Ticket ticket) {
        // Assign the ticket to a team based on the category
        Team team = teamRepository.findByCategoriesContains(ticket.getCategory());
        ticket.setAssignedTeam(team);

        // Save the new ticket
        Ticket savedTicket = ticketRepository.save(ticket);

        // Send notification upon ticket creation
        notificationService.sendTicketCreationNotification(savedTicket);

        return savedTicket;
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public boolean updateTicketStatus(Long id, String status) {
        return ticketRepository.findById(id)
                .map(ticket -> {
                    try {
                        ticket.setStatus(Ticket.Status.valueOf(status.toUpperCase()));
                        ticketRepository.save(ticket);
                        notificationService.sendTicketStatusUpdateNotification(ticket);
                        return true;
                    } catch (IllegalArgumentException e) {
                        return false;
                    }
                }).orElse(false);
    }
}
