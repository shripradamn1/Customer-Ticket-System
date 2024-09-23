package com.spring_boot.CSTS.model;

public class SupportAgentDTO {
    private Long id;
    private String name;
    private String username;
    private Long teamId;
    private String teamName; // Add team name instead of embedding full Team object
    private Long categoryId;
    private String categoryName; // Similarly for category

    // Constructors, getters, and setters
    public SupportAgentDTO(Long id, String name, String username, Long teamId, String teamName, Long categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.teamId = teamId;
        this.teamName = teamName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Getters and setters for new fields
    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // other getters and setters
}


