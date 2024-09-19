package com.spring_boot.CSTS.model;

public class SupportAgentDTO {
    private Long id;
    private String name;
    private String username;
    private Team team;
    private Category category;

    // Constructors, getters, and setters
    public SupportAgentDTO(Long id, String name, String username, Team team, Category category) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.team = team;
        this.category = category;
    }

    // Getters and setters
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
