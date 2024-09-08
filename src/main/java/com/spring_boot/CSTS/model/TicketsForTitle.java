package com.spring_boot.CSTS.model;

public class TicketsForTitle {
    private Long id;
    private String title;
    private String description;
    private Ticket.Status status;

    public TicketsForTitle(Long id, String title, Ticket.Status status) {
        this.id=id;
        this.title=title;
        this.status=status;
    }
    public TicketsForTitle(){

    }

    private enum status{
        OPEN, IN_PROGRESS, ASSIGNED, CLOSED, RESOLVED
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Ticket.Status getStatus() {
        return status;
    }

    public void setStatus(Ticket.Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
