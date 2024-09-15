package com.spring_boot.CSTS.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    public void testGetAndSetId() {
        Long id = 1L;
        category.setId(id);
        assertEquals(id, category.getId(), "Category ID should be set correctly.");
    }

    @Test
    public void testGetAndSetName() {
        String name = "Sports";
        category.setName(name);
        assertEquals(name, category.getName(), "Category name should be set correctly.");
    }

    @Test
    public void testGetAndSetTeams() {
        Team team1 = new Team();
        team1.setName("Business");
        Team team2 = new Team();
        team2.setName("Management");

        Set<Team> teams = new HashSet<>();
        teams.add(team1);
        teams.add(team2);

        category.setTeams(teams);

        Set<Team> retrievedTeams = category.getTeams();
        assertEquals(2, retrievedTeams.size(), "Category should have 2 teams.");
        assertTrue(retrievedTeams.contains(team1), "Category should contain team1.");
        assertTrue(retrievedTeams.contains(team2), "Category should contain team2.");
    }

    @Test
    public void testEmptyTeams() {
        Set<Team> teams = new HashSet<>();
        category.setTeams(teams);
        assertTrue(category.getTeams().isEmpty(), "Teams set should be empty.");
    }

    @Test
    public void testGetAndSetTeamIds() {
        List<Long> teamIds = List.of(1L, 2L, 3L);
        category.setTeamIds(teamIds);

        List<Long> retrievedTeamIds = category.getTeamIds();
        assertEquals(3, retrievedTeamIds.size(), "TeamIds should contain 3 entries.");
        assertEquals(1L, retrievedTeamIds.get(0), "First Team ID should be 1.");
        assertEquals(2L, retrievedTeamIds.get(1), "Second Team ID should be 2.");
        assertEquals(3L, retrievedTeamIds.get(2), "Third Team ID should be 3.");
    }
}
