package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.Repository.CategoryRepository;
import com.spring_boot.CSTS.Repository.SupportAgentRepository;
import com.spring_boot.CSTS.Repository.TeamRepository;
import com.spring_boot.CSTS.model.Category;
import com.spring_boot.CSTS.model.SupportAgent;
import com.spring_boot.CSTS.model.Team;
import com.spring_boot.CSTS.model.Ticket;
import com.spring_boot.CSTS.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Ticket createTicket(Long userId,Long categoryId,Long teamId,Ticket ticket) {
        if(categoryId==null)
            throw new IllegalArgumentException("categoryId must be given");

        if (ticket.getTeam() == null || teamId == null) {
            throw new IllegalArgumentException("Team must be provided for the ticket.");
        }

        Category category=categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("category not found"));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));


        Set<SupportAgent> agentsSet = team.getAgents();
        List<SupportAgent> agents = new ArrayList<>(agentsSet);


        if (agents != null && !agents.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(Math.min(5, agents.size()));
            SupportAgent assignedAgent = agents.get(randomIndex);
            ticket.setAssignedTo(assignedAgent);
        } else {
            throw new RuntimeException("No agents available in the team");
        }
       // ticket.setCategory(ticket.getCategory());
    ticket.setUserId(userId);
       ticket.setCategory(category);
        ticket.setTeam(team);
        ticket.setStatus(Ticket.Status.OPEN);

        Ticket savedTicket= ticketRepository.save(ticket);
        System.out.println("Ticket created with ID: " + savedTicket.getId()+
                ", Assigned to Agent: " + savedTicket.getAssignedTo().getName());
        return savedTicket;
    }


    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketByUserId(Long id) {
        return (Ticket) ticketRepository.findByUserId(id);
    }

    public Ticket updateTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
    public List<Ticket> getTicketsByAgent(Optional<SupportAgent> agent) {
        return ticketRepository.findByAssignedTo(agent);
    }

    public Ticket findTicketByTitle(String title) {
        return ticketRepository.findByTitle(title);
    }

    public Ticket updateTicketStatus(Long ticketId, Ticket.Status newStatus,Ticket.Priority newPriority) throws Exception {
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
}
