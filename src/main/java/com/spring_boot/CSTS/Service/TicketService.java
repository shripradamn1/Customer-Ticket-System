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
    public Ticket createTicket(Long userId, Long categoryId, Long teamId, Ticket ticket,MultipartFile file) throws IOException {

        if (file != null && !file.isEmpty()) {
            String filePath = saveFile(file);  // Save file
            ticket.setAttachment("C:\\Users\\e031906\\Downloads\\demo");    // Add file path to the ticket
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
        String filePath = "C:/Users/e031760/Desktop/CaseStudy/attachments/" + fileName;
    
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
    public Ticket updateTicket(Ticket updatedTicket) {
        Ticket existingTicket = ticketRepository.findById(updatedTicket.getId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        Ticket savedTicket = ticketRepository.save(updatedTicket);

        // Send dynamic email notification for updates
        String subject = "Ticket Updated: " + savedTicket.getTitle();
        String emailBody = emailService.buildTicketStatusUpdateEmail(
            loggedInUser.getUsername(), savedTicket.getId(), savedTicket.getTitle(),
            savedTicket.getStatus().toString(), savedTicket.getAssignedTo().getName(),
            "http://supportsystem.com/ticket/" + savedTicket.getId()
        );
        emailService.sendEmail(loggedInUser.getEmail(), subject, emailBody, true);

        return savedTicket;
    }

    public Ticket updateTicketStatus(Long ticketId, Ticket.Status newStatus, Ticket.Priority newPriority) throws Exception {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            ticket.setPriority(newPriority); // Set priority if updated
            ticket.setStatus(newStatus); // Set status
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
