package com.spring_boot.CSTS.model;

public class TicketsForAgents {
    private Long id;
    private String name;

    public TicketsForAgents(Long id, String title) {
        this.id=id;
        this.name=title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
