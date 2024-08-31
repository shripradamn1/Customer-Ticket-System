package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.Repository.TeamRepository;
import com.spring_boot.CSTS.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team findTeamById(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public void deleteTeamById(Long id) {
        teamRepository.deleteById(id);
    }
}
