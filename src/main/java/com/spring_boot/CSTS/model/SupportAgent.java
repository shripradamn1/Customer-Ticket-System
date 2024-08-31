package com.spring_boot.CSTS.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("AGENT")
public class SupportAgent extends User {

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "assignedAgent")
    private List<Ticket> assignedTickets;

    // Getters and Setters
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Ticket> getAssignedTickets() {
        return assignedTickets;
    }

    public void setAssignedTickets(List<Ticket> assignedTickets) {
        this.assignedTickets = assignedTickets;
    }
}
