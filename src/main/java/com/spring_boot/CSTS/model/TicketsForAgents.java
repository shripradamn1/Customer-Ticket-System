package com.spring_boot.CSTS.model;

import com.spring_boot.CSTS.model.Ticket; // Import your Ticket class if necessary

public class TicketsForAgents {
    private Long id;
    private String title;
    private String status;

    private String priority;

    public TicketsForAgents(Long id, String title, Ticket.Status status,Ticket.Priority priority) {
        this.id = id;
        this.title = title;
        this.status = status != null ? status.name() : null;
        this.priority= String.valueOf(priority);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
