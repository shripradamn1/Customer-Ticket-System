package com.spring_boot.CSTS.model;


import com.spring_boot.CSTS.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TeamForCategories {
    private Long teamId;
    private String name;

    public TeamForCategories(Long id, String name) {
        this.teamId = id;
        this.name = name;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
