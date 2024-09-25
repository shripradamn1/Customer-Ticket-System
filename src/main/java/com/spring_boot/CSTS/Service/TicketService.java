//package com.spring_boot.CSTS.Service;
//
//import com.spring_boot.CSTS.Exception.TicketNotFoundException;
//import com.spring_boot.CSTS.Repository.*;
//import com.spring_boot.CSTS.model.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.*;
//
//@Service
//public class TicketService {
//
//    @Autowired
//    private TicketRepository ticketRepository;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Autowired
//    private TeamRepository teamRepository;
//
//    @Autowired
//    private SupportAgentRepository supportAgentRepository;
//
//    @Autowired
//    private EmailService emailService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Transactional
//    public Ticket createTicket(Long userId, Long categoryId, Long teamId, Ticket ticket, MultipartFile file) throws IOException {
//
//        if (file != null && !file.isEmpty()) {
//            String filePath = saveFile(file);  // Save file and get path
//            ticket.setAttachment("C:\\Users\\e031906\\Desktop\\attachments");    // Add file path to the ticket
//        } else {
//            ticket.setAttachment(null); // Ensure attachment is null if no file is provided
//        }
//
//        // Validate category ID
//        if (categoryId == null) {
//            throw new IllegalArgumentException("categoryId must be provided");
//        }
//
//        // Fetch the logged-in user's username
//        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
//        User loggedInUser = userRepository.findByUsername(loggedInUsername)
//                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));
//
//        // Fetch category, team, and agents
//        Category category = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new RuntimeException("Category not found"));
//
//        Team team = teamRepository.findById(teamId)
//                .orElseThrow(() -> new RuntimeException("Team not found"));
//
//        Set<SupportAgent> agentsSet = team.getAgents();
//        List<SupportAgent> agents = new ArrayList<>(agentsSet);
//
//        if (!agents.isEmpty()) {
//            Random random = new Random();
//            SupportAgent assignedAgent = agents.get(random.nextInt(agents.size()));
//            ticket.setAssignedTo(assignedAgent);
//        } else {
//            throw new RuntimeException("No agents available in the team");
//        }
//
//        // Set ticket attributes
//        ticket.setUserId(userId);
//        ticket.setCategory(category);
//        ticket.setTeam(team);
//        ticket.setStatus(Ticket.Status.OPEN);
//
//        // Save the ticket
//        Ticket savedTicket = ticketRepository.save(ticket);
//
//        // Send dynamic HTML email notification
//        String emailBody = emailService.buildTicketCreationEmail(
//                loggedInUser.getUsername(), savedTicket.getTitle(), savedTicket.getId(),
//                savedTicket.getDescription(), "http://supportsystem.com/ticket/" + savedTicket.getId(),
//                savedTicket.getAssignedTo().getName()
//        );
//
//        String subject = "New Ticket Created: " + savedTicket.getTitle();
//        emailService.sendEmail(loggedInUser.getEmail(), subject, emailBody, true);
//
//        return savedTicket;
//    }
//
//    private String saveFile(MultipartFile file) throws IOException {
//        String fileName = file.getOriginalFilename();
//        String filePath = "C:\\Users\\e031906\\Desktop\\attachments" + fileName; // Change this path as needed
//
//        File dest = new File(filePath);
//
//        // Create the directory if it doesn't exist
//        if (!dest.getParentFile().exists()) {
//            dest.getParentFile().mkdirs();
//        }
//
//        file.transferTo(dest);  // Transfer the file to the destination
//        return filePath;
//    }
//
//    public List<Ticket> getAllTickets() {
//        return ticketRepository.findAll();
//    }
//
//    public Ticket getTicketByUserId(Long id) {
//        List<Ticket> tickets = ticketRepository.findByUserId(id);
//        if (tickets.isEmpty()) {
//            throw new RuntimeException("Ticket not found");
//        }
//        return tickets.get(0); // Return the first ticket (or handle accordingly)
//    }
//
//    public Ticket findTicketByTitle(String title) {
//        return ticketRepository.findByTitle(title);
//    }
//
//    public List<Ticket> getTicketsByAgent(Optional<SupportAgent> agent) {
//        return ticketRepository.findByAssignedTo(agent);
//    }
//
//    @Transactional
//    public Ticket updateTicketStatus(Long ticketId, Ticket.Status newStatus, Ticket.Priority newPriority) throws Exception {
//        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
//
//        if (ticketOptional.isPresent()) {
//            Ticket ticket = ticketOptional.get();
//            Ticket.Status oldStatus = ticket.getStatus();
//            Ticket.Priority oldPriority = ticket.getPriority();
//
//            ticket.setStatus(newStatus);
//            ticket.setPriority(newPriority);
//
//            Ticket savedTicket = ticketRepository.save(ticket);
//
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
//
//            return savedTicket;
//        } else {
//            throw new Exception("Ticket not found");
//        }
//    }
//
//    private void sendTicketUpdateEmail(User loggedInUser, Ticket savedTicket, Ticket.Status oldStatus, Ticket.Status newStatus, Ticket.Priority oldPriority, Ticket.Priority newPriority) {
//        String subject = "Ticket Updated: " + savedTicket.getTitle();
//        String emailBody = emailService.buildTicketStatusUpdateEmail(
//                loggedInUser.getUsername(),
//                savedTicket.getId(),
//                savedTicket.getTitle(),
//                oldStatus.toString(),
//                newStatus.toString(),
//                oldPriority.toString(),
//                newPriority.toString(),
//                savedTicket.getAssignedTo().getName(),
//                "http://supportsystem.com/ticket/" + savedTicket.getId()
//        );
//        emailService.sendEmail(loggedInUser.getEmail(), subject, emailBody, true);
//    }
//
//    @Transactional
//    public void deleteTicket(Long id) {
//        Ticket ticket = ticketRepository.findById(id)
//                .orElseThrow(() -> new TicketNotFoundException("Ticket with id " + id + " not found"));
//        ticketRepository.delete(ticket);
//    }
//
//
//
//}

package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.Exception.TicketNotFoundException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TicketService {
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

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

    @Autowired
    private AttachmentService attachmentService;

    @Transactional
    public Ticket createTicket(Long userId, Long categoryId, Long teamId, Ticket ticket) throws IOException {
        logger.info("Starting ticket creation for userId: {}, categoryId: {}, teamId: {}", userId, categoryId, teamId);

        if (categoryId == null) {
            logger.error("categoryId must be provided");
            throw new IllegalArgumentException("categoryId must be provided");
        }

        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        logger.info("Logged in user found: {}", loggedInUser.getUsername());

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        logger.info("Category found: {}", category.getName());

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        logger.info("Team found: {}", team.getName());

        Set<SupportAgent> agentsSet = team.getAgents();
        if (agentsSet.isEmpty()) {
            logger.error("No agents available in team: {}", team.getName());
            throw new RuntimeException("No agents available in the team");
        }

        logger.info("Agents found: {}", agentsSet.size());

        Random random = new Random();
        SupportAgent assignedAgent = agentsSet.stream().skip(random.nextInt(agentsSet.size())).findFirst().get();
        ticket.setAssignedTo(assignedAgent);

        logger.info("Assigned agent: {}", assignedAgent.getName());

        ticket.setUserId(userId);
        ticket.setCategory(category);
        ticket.setTeam(team);
        ticket.setStatus(Ticket.Status.OPEN);

        Ticket savedTicket = ticketRepository.save(ticket);
        logger.info("Ticket saved with ID: {}", savedTicket.getId());

//        if (file != null && !file.isEmpty()) {
//            attachmentService.saveAttachment(savedTicket.getId(), file);
//            logger.info("Attachment saved for ticket ID: {}", savedTicket.getId());
//        }

        String emailBody = emailService.buildTicketCreationEmail(
                loggedInUser.getUsername(), savedTicket.getTitle(), savedTicket.getId(),
                savedTicket.getDescription(), "" + savedTicket.getId(),
                savedTicket.getAssignedTo().getName()
        );

        emailService.sendEmail(loggedInUser.getEmail(), "New Ticket Created: " + savedTicket.getTitle(), emailBody, true);
        logger.info("Email sent to user: {}", loggedInUser.getEmail());

        return savedTicket;
    }


//    private String saveFile(MultipartFile file) throws IOException {
//        String fileName = file.getOriginalFilename();
//        String filePath = "C:/Users/e031906/Desktop/attachments/" + fileName;
//
//
//        File dest = new File(filePath);
//
//        // Create the directory if it doesn't exist
//        if (!dest.getParentFile().exists()) {
//            dest.getParentFile().mkdirs();
//        }
//
//        file.transferTo(dest);  // Transfer the file to the destination
//        return filePath;
//    }


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
        System.out.println(ticketOptional.toString() + "      ticket update");

        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            System.out.println(ticket.getTeam() + " ");

            ticket.setStatus(newStatus);
            ticket.setPriority(newPriority);

            Ticket savedTicket = ticketRepository.save(ticket);

            String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            User loggedInUser = userRepository.findByUsername(loggedInUsername)
                    .orElseThrow(() -> new RuntimeException("Logged-in user not found"));
            System.out.println(loggedInUsername);
            if (newStatus.equals(Ticket.Status.RESOLVED)) {
                String feedbackLink = "http://localhost:3000/feedback/" + savedTicket.getId(); // React frontend link
                String subject = "Ticket Resolved: " + savedTicket.getTitle();
                String emailBody = emailService.buildTicketResolvedEmail(
                        loggedInUser.getUsername(),
                        savedTicket.getId(),
                        savedTicket.getTitle(),
                        savedTicket.getAssignedTo().getName(),
                        feedbackLink
                );
                emailService.sendEmail(loggedInUser.getEmail(), subject, emailBody, true);
            } else {
                sendTicketUpdateEmail(loggedInUser, savedTicket, ticket.getStatus(), newStatus, ticket.getPriority(), newPriority);
            }

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
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id " + id + " not found"));
        ticketRepository.delete(ticket);
    }


    public Ticket updateTicket(Long id, String des) {
        // Fetch the ticket by ID
        Optional<Ticket> existingTicketOpt = ticketRepository.findById(id);

        // If the ticket exists, update its fields
        if (existingTicketOpt.isPresent()) {
            Ticket existingTicket = existingTicketOpt.get();

            existingTicket.setDescription(des);


            // Save the updated ticket
            return ticketRepository.save(existingTicket);
        } else {
            throw new TicketNotFoundException("Ticket with ID " + id + " not found.");
        }
    }
}



