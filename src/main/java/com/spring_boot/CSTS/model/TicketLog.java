package com.spring_boot.CSTS.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
 
@Entity
@Table(name = "ticket_logs")
public class TicketLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_id")
    @JsonIgnore
    private Ticket ticket;
 
    private String message;
 
    @ManyToOne
    @JoinColumn(name = "updated_by")
    @JsonIgnore
    private SupportAgent updatedBy;
 
    private LocalDateTime timestamp;
 
    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();
    }
 
    // Getters and Setters...
 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public Ticket getTicket() {
        return ticket;
    }
 
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
 
    public SupportAgent getUpdatedBy() {
        return updatedBy;
    }
 
    public void setUpdatedBy(SupportAgent updatedBy) {
        this.updatedBy = updatedBy;
    }
 
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
 
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}