package com.spring_boot.CSTS.model;

import java.time.LocalDateTime;

public class TicketsForTitle {
    private Long id;
    private String title;
    private String description;
    private Ticket.Status status;
      	private LocalDateTime createdAt = LocalDateTime.now();

    public TicketsForTitle(Long id, String title, Ticket.Status status,LocalDateTime createdAt,String des) {
        this.id=id;
        this.title=title;
        this.status=status;
        this.createdAt=createdAt;
        this.description=des;
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
