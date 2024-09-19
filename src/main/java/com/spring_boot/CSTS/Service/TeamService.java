package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.Repository.CategoryRepository;
import com.spring_boot.CSTS.model.Category;
import com.spring_boot.CSTS.model.Team;
import com.spring_boot.CSTS.Repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Optional<Team> getTeamById(Long id) {
        return teamRepository.findById(id);
    }

    public Team createTeam(Team team, Long categoryId) {
        if(teamRepository.count()==0)
        {
            teamRepository.resetAutoIncrement();
        }
        // Fetch the category by ID
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        // Set the category for the team
        team.setCategory(category);

        // Save the team
        return teamRepository.save(team);
    }
    public Team updateTeam(Long id, Team teamDetails) {
        return teamRepository.findById(id)
                .map(team -> {
                    team.setName(teamDetails.getName());
                    team.setCategory(teamDetails.getCategory());
                    return teamRepository.save(team);
                })
                .orElseThrow(() -> new RuntimeException("Team not found"));
    }
    public List<Team> getTeamsByCategory(Long categoryId) {
        return teamRepository.findByCategoryId(categoryId);
    }

    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }



}
