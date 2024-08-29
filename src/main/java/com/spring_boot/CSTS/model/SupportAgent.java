package com.spring_boot.CSTS.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "support_agents")
public class SupportAgent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void viewAssignedTickets() {

    }public void updateTicketStatus(Ticket ticket, Ticket.Status status) {

        ticket.setStatus(status);
    }
}
