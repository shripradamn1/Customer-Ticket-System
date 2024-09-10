package com.spring_boot.CSTS.Controller;

import com.spring_boot.CSTS.Repository.TicketRepository;
import com.spring_boot.CSTS.Service.SupportAgentService;
import com.spring_boot.CSTS.model.*;
import com.spring_boot.CSTS.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private SupportAgentService agentService;

    @PostMapping("{userId}/{categoryId}/{teamId}")
    public Ticket createTicket(@PathVariable Long userId, @PathVariable Long categoryId, @PathVariable Long teamId, @RequestBody Ticket ticketData) {
        return ticketService.createTicket(userId, categoryId, teamId, ticketData);
    }

    // Get all tickets
    @GetMapping
    public ResponseEntity<List<TicketsForDelete>> getAllTickets() {

        List<Ticket> tickets = ticketService.getAllTickets();
        List<TicketsForDelete> ticketsForDeletes = tickets.stream().map(t -> new TicketsForDelete(t.getId(), t.getTitle())).toList();
        return ResponseEntity.ok(ticketsForDeletes);
    }


    @GetMapping("/userId/{userId}")
    public List<TicketsForTitle> getTicketsByUserId(@PathVariable Long userId) {
        List<Ticket> tickets = ticketRepository.findByUserId(userId);
        return tickets.stream()
                .map(ticket -> new TicketsForTitle(ticket.getId(), ticket.getTitle(), ticket.getStatus()))
                .collect(Collectors.toList());
    }



    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        ticket.setDescription(ticket.getDescription());
        return ResponseEntity.ok(ticketService.updateTicket(ticket));
    }



    @DeleteMapping("/delete/{ticketId}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long ticketId) {
        ticketService.deleteTicket(ticketId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/agent/{username}")
    public ResponseEntity<List<TicketsForAgents>> getTicketsByAgent(@PathVariable String username) {
        System.out.println("Received agent username: " + username); // Log the received agentId
        Optional<SupportAgent> agentOptional = agentService.getAgentById(username);
        if (agentOptional.isEmpty()) {
            System.out.println("Agent not found");
            return ResponseEntity.notFound().build();
        }

        SupportAgent agent = agentOptional.get();
        List<Ticket> tickets = ticketService.getTicketsByAgent(Optional.of(agent));
        System.out.println("Retrieved tickets: " + tickets); // Log retrieved tickets

        // Map the tickets to TicketsForAgents
        List<TicketsForAgents> agentTickets = tickets.stream()
                .map(ticket -> new TicketsForAgents(ticket.getId(), ticket.getTitle()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(agentTickets); // Return the tickets wrapped in ResponseEntity
    }
    @GetMapping("title/{title}")
    public ResponseEntity<?> getTicketByTitle(@PathVariable String title){

            Ticket ticket = ticketService.findTicketByTitle(title);

        TicketsForTitle ticketsForTitle=new TicketsForTitle();
        ticketsForTitle.setId(ticket.getId());
        ticketsForTitle.setTitle(ticket.getTitle());
        ticketsForTitle.setStatus(ticket.getStatus());
        ticketsForTitle.setDescription(ticket.getDescription());

            if (ticketsForTitle != null) {
                return ResponseEntity.ok(ticketsForTitle);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tickets not found");
            }

    }
    @PutMapping("/{ticketId}/status")
    public ResponseEntity<?> updateTicketStatus(@PathVariable Long ticketId, @RequestParam Ticket.Status status) {
        try {
            Ticket ticket = ticketService.updateTicketStatus(ticketId, status);
            return ResponseEntity.ok(ticket);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update status");
        }
    }
}



