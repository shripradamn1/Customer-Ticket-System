package com.spring_boot.CSTS.Controller;

import com.spring_boot.CSTS.Repository.CategoryRepository;
import com.spring_boot.CSTS.Repository.TeamRepository;
import com.spring_boot.CSTS.model.Team;
import com.spring_boot.CSTS.Service.TeamService;
import com.spring_boot.CSTS.model.TeamForCategories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        return teamService.getTeamById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team, @RequestParam Long categoryId) {
        Team createdTeam = teamService.createTeam(team, categoryId);
        return ResponseEntity.ok(createdTeam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories/{categoryId}/teams")
    public List<TeamForCategories> getTeamsByCategory(@PathVariable Long categoryId) {
        System.out.println("recieved category is "+categoryId);
        List<Team> teams= teamService.getTeamsByCategory(categoryId);
        return teams.stream()
                .map(team -> new TeamForCategories(team.getId(), team.getName()))
                .toList();
    }

}
