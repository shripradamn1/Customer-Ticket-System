package com.spring_boot.CSTS.Controller;

import com.spring_boot.CSTS.Service.TicketService;
import com.spring_boot.CSTS.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        return ResponseEntity.ok(ticketService.createTicket(ticket));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<String> updateTicketStatus(@PathVariable long id, @RequestParam String status) {
        boolean updateSuccessful = ticketService.updateTicketStatus(id, status);
        if (updateSuccessful) {
            return ResponseEntity.ok("Ticket status updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid status or ticket not found.");
        }
    }
}
