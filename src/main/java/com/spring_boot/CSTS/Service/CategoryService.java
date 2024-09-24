package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.model.Category;
import com.spring_boot.CSTS.model.Team;
import com.spring_boot.CSTS.Repository.CategoryRepository;
import com.spring_boot.CSTS.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamService teamService;

    public List<Category> getAllCategories() {
        System.out.println("From category service : " + categoryRepository.findAll());
        System.out.println("From category service : " + categoryRepository.findAll().get(1).getId() + ", " + categoryRepository.findAll().get(1).getName());

        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category createCategory(Category category) {
        Set<Team> teams = new HashSet<>();
        if (category.getTeamIds() != null) {
            for (Long teamId : category.getTeamIds()) {
                Team team = teamRepository.findById(teamId)
                        .orElseThrow(() -> new RuntimeException("Team not found"));
                teams.add(team);
            }
        }
        category.setTeams(teams);
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(categoryDetails.getName());
                    Set<Team> teams = new HashSet<>();
                    if (categoryDetails.getTeamIds() != null) {
                        for (Long teamId : categoryDetails.getTeamIds()) {
                            Team team = teamRepository.findById(teamId)
                                    .orElseThrow(() -> new RuntimeException("Team not found"));
                            teams.add(team);
                        }
                    }
                    category.setTeams(teams);
                    return categoryRepository.save(category);
                })
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }


    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }
}
