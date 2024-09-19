package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.Repository.*;
import com.spring_boot.CSTS.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Ticket createTicket(Long userId, Long categoryId, Long teamId, Ticket ticket, MultipartFile file) throws IOException {

        if (file != null && !file.isEmpty()) {
            String filePath = saveFile(file);  // Save file and get path
            ticket.setAttachment(filePath);    // Add file path to the ticket
        } else {
            ticket.setAttachment(null); // Ensure attachment is null if no file is provided
        }

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
        String emailBody = emailService.buildTicketCreationEmail(
                loggedInUser.getUsername(), savedTicket.getTitle(), savedTicket.getId(),
                savedTicket.getDescription(), "http://supportsystem.com/ticket/" + savedTicket.getId(),
                savedTicket.getAssignedTo().getName()
        );

        String subject = "New Ticket Created: " + savedTicket.getTitle();
        emailService.sendEmail(loggedInUser.getEmail(), subject, emailBody, true);

        return savedTicket;
    }

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = "C:/path/to/your/upload/dir/" + fileName; // Change this path as needed

        File dest = new File(filePath);

        // Create the directory if it doesn't exist
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        file.transferTo(dest);  // Transfer the file to the destination
        return filePath;
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
        return ticketRepository.findByTitle(title);
    }

    public List<Ticket> getTicketsByAgent(Optional<SupportAgent> agent) {
        return ticketRepository.findByAssignedTo(agent);
    }

    @Transactional
    public Ticket updateTicketStatus(Long ticketId, Ticket.Status newStatus, Ticket.Priority newPriority) throws Exception {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);

        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            Ticket.Status oldStatus = ticket.getStatus();
            Ticket.Priority oldPriority = ticket.getPriority();

            ticket.setStatus(newStatus);
            ticket.setPriority(newPriority);

            Ticket savedTicket = ticketRepository.save(ticket);

//            String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
//            User loggedInUser = userRepository.findByUsername(loggedInUsername)
//                    .orElseThrow(() -> new RuntimeException("Logged-in user not found"));
//
//            if (newStatus == Ticket.Status.RESOLVED) {
//                String feedbackLink = "http://localhost:3000/feedback/" + savedTicket.getId(); // React frontend link
//                String subject = "Ticket Resolved: " + savedTicket.getTitle();
//                String emailBody = emailService.buildTicketResolvedEmail(
//                        loggedInUser.getUsername(),
//                        savedTicket.getId(),
//                        savedTicket.getTitle(),
//                        savedTicket.getAssignedTo().getName(),
//                        feedbackLink
//                );
//                emailService.sendEmail(loggedInUser.getEmail(), subject, emailBody, true);
//            } else {
//                sendTicketUpdateEmail(loggedInUser, savedTicket, oldStatus, newStatus, oldPriority, newPriority);
//            }

            return savedTicket;
        } else {
            throw new Exception("Ticket not found");
        }
    }

    private void sendTicketUpdateEmail(User loggedInUser, Ticket savedTicket, Ticket.Status oldStatus, Ticket.Status newStatus, Ticket.Priority oldPriority, Ticket.Priority newPriority) {
        String subject = "Ticket Updated: " + savedTicket.getTitle();
        String emailBody = emailService.buildTicketStatusUpdateEmail(
                loggedInUser.getUsername(),
                savedTicket.getId(),
                savedTicket.getTitle(),
                oldStatus.toString(),
                newStatus.toString(),
                oldPriority.toString(),
                newPriority.toString(),
                savedTicket.getAssignedTo().getName(),
                "http://supportsystem.com/ticket/" + savedTicket.getId()
        );
        emailService.sendEmail(loggedInUser.getEmail(), subject, emailBody, true);
    }

    @Transactional
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}
