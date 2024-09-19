package com.spring_boot.CSTS.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_boot.CSTS.Repository.FeedbackRepository;
import com.spring_boot.CSTS.Repository.TicketRepository;
import com.spring_boot.CSTS.model.Feedback;
import com.spring_boot.CSTS.model.Ticket;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private TicketRepository ticketRepository;

    // Endpoint to receive feedback
    @PostMapping("/{ticketId}")
    public ResponseEntity<?> submitFeedback(@PathVariable Long ticketId, @RequestBody Feedback feedback) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        feedback.setTicket(ticket);
        feedbackRepository.save(feedback);

        return ResponseEntity.ok("Feedback submitted successfully");
    }

        @GetMapping("/api/admin/feedbacks")
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }


    // Endpoint for admin to view feedback for a specific ticket
    @GetMapping("/{ticketId}")
    public ResponseEntity<List<Feedback>> getFeedbackForTicket(@PathVariable Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        List<Feedback> feedbackList = feedbackRepository.findByTicket(ticket);
        return ResponseEntity.ok(feedbackList);
    }
}
