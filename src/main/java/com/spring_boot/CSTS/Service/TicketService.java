package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.Repository.*;
import com.spring_boot.CSTS.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private SupportAgentRepository supportAgentRepository;

//    @Autowired
//    private TicketLogService ticketLogService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Ticket createTicket(Long userId, Long categoryId, Long teamId, Ticket ticket) {
        // Validate category ID
        if (categoryId == null) {
            throw new IllegalArgumentException("categoryId must be provided");
        }

        // Fetch the logged-in user's username
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        // Fetch category, team, and agents
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

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

        // Set ticket attributes
        ticket.setUserId(userId);
        ticket.setCategory(category);
        ticket.setTeam(team);
        ticket.setStatus(Ticket.Status.OPEN);

        // Save the ticket
        Ticket savedTicket = ticketRepository.save(ticket);

        // Send dynamic HTML email notification
     // When creating a ticket, add the agent name in the email.
        String emailBody = emailService.buildTicketCreationEmail(
            loggedInUser.getUsername(), savedTicket.getTitle(), savedTicket.getId(),
            savedTicket.getDescription(), "http://supportsystem.com/ticket/" + savedTicket.getId(),
            savedTicket.getAssignedTo().getName()
        );

        String subject = "New Ticket Created: " + savedTicket.getTitle();
        emailService.sendEmail(loggedInUser.getEmail(), subject, emailBody, true);

        return savedTicket;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketByUserId(Long id) {
        List<Ticket> tickets = ticketRepository.findByUserId(id);
        if (tickets.isEmpty()) {
            throw new RuntimeException("Ticket not found");
        }
        return tickets.get(0); // Return the first ticket (or handle accordingly)
    }


    public Ticket findTicketByTitle(String title) {
        return ticketRepository.findByTitle(title).orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    public List<Ticket> getTicketsByAgent(Optional<SupportAgent> agent) {
        return ticketRepository.findByAssignedTo(agent);
    }

    @Transactional
    public Ticket updateTicket(Ticket updatedTicket) {
        Ticket existingTicket = ticketRepository.findById(updatedTicket.getId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

//        TicketLog log = new TicketLog();
//        log.setTicket(existingTicket);

//        if (!Objects.equals(existingTicket.getStatus(), updatedTicket.getStatus())) {
//            log.setMessage("Status changed from " + existingTicket.getStatus() + " to " + updatedTicket.getStatus());
//        } else if (!Objects.equals(existingTicket.getAssignedTo(), updatedTicket.getAssignedTo())) {
//            log.setMessage("Assigned agent changed from " + existingTicket.getAssignedTo().getName() + " to " + updatedTicket.getAssignedTo().getName());
//        }

//        if (log.getMessage() != null) {
//            ticketLogService.addTicketLog(existingTicket.getId(), log);
//        }

        Ticket savedTicket = ticketRepository.save(updatedTicket);

        // Send dynamic email notification
        String subject = "Ticket Updated: " + savedTicket.getTitle();
     // Send email when the ticket status is updated
        String emailBody = emailService.buildTicketStatusUpdateEmail(
            loggedInUser.getUsername(), savedTicket.getId(), savedTicket.getTitle(),
            savedTicket.getStatus().toString(), savedTicket.getAssignedTo().getName(),
            "http://supportsystem.com/ticket/" + savedTicket.getId()
        );
        emailService.sendEmail(loggedInUser.getEmail(), subject, emailBody, true);


        return savedTicket;
    }

    // @Transactional
    // public void deleteTicket(Long id) {
    //     ticketRepository.deleteById(id);
    // }
    public List<Ticket> getTicketsByAgent(Optional<SupportAgent> agent) {
        return ticketRepository.findByAssignedTo(agent);
    }

    // public Ticket findTicketByTitle(String title) {
    //     return ticketRepository.findByTitle(title);
    // }

    public Ticket updateTicketStatus(Long ticketId, Ticket.Status newStatus) throws Exception {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            ticket.setPriority(newPriority);
            ticket.setStatus(newStatus); // Assuming your Ticket entity has a `setStatus` method
            return ticketRepository.save(ticket);
        } else {
            throw new Exception("Ticket not found");
        }
    }
    
    @Transactional
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}
