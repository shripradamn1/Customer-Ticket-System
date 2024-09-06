package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.Repository.*;
import com.spring_boot.CSTS.model.Ticket;
import com.spring_boot.CSTS.model.TicketLog;
import com.spring_boot.CSTS.model.SupportAgent;
import com.spring_boot.CSTS.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private SupportAgentRepository supportAgentRepository;

    @Autowired
    private TicketLogService ticketLogService;

    @Autowired
    private EmailService emailService;  // Add email service

    @Transactional
    public Ticket createTicket(Ticket ticket, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        Set<SupportAgent> agentsSet = team.getAgents();
        List<SupportAgent> agents = new ArrayList<>(agentsSet);

        if (!agents.isEmpty()) {
            Random random = new Random();
            SupportAgent assignedAgent = agents.get(random.nextInt(agents.size()));
            ticket.setAssignedTo(assignedAgent);
        } else {
            throw new RuntimeException("No agents available in the team");
        }

        ticket.setTeam(team);
        ticket.setStatus(Ticket.Status.OPEN);

        Ticket savedTicket = ticketRepository.save(ticket);

        // Send email notification for ticket creation
        String subject = "New Ticket Created: " + savedTicket.getTitle();
        String body = "A new ticket has been created with the following details:\n" +
                      "Ticket ID: " + savedTicket.getId() + "\n" +
                      "Title: " + savedTicket.getTitle() + "\n" +
                      "Assigned To: " + savedTicket.getAssignedTo().getName();
        emailService.sendEmail("dhruv15.khandelwal@gmail.com", subject, body);

        return savedTicket;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    @Transactional
    public Ticket updateTicket(Ticket updatedTicket) {
        Ticket existingTicket = ticketRepository.findById(updatedTicket.getId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        // Capture changes and create a log
        TicketLog log = new TicketLog();
        log.setTicket(existingTicket);

        if (!Objects.equals(existingTicket.getStatus(), updatedTicket.getStatus())) {
            log.setMessage("Status changed from " + existingTicket.getStatus() + " to " + updatedTicket.getStatus());
        } else if (!Objects.equals(existingTicket.getAssignedTo(), updatedTicket.getAssignedTo())) {
            log.setMessage("Assigned agent changed from " + existingTicket.getAssignedTo().getName() + " to " + updatedTicket.getAssignedTo().getName());
        }

        if (log.getMessage() != null) {
            ticketLogService.addTicketLog(existingTicket.getId(), log);
        }

        Ticket savedTicket = ticketRepository.save(updatedTicket);

        // Send email notification for ticket update
        String subject = "Ticket Updated: " + savedTicket.getTitle();
        String body = "Ticket ID: " + savedTicket.getId() + " has been updated.\n" +
                      "Updated Status: " + savedTicket.getStatus() + "\n" +
                      "Assigned To: " + savedTicket.getAssignedTo().getName();
        emailService.sendEmail("dhruv15.khandelwal@gmail.com", subject, body);

        return savedTicket;
    }

    @Transactional
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}
