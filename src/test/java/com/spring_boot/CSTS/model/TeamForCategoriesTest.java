package com.spring_boot.CSTS.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TeamForCategoriesTest {

    @Test
    public void testConstructor() {
        Long expectedId = 1L;
        String expectedName = "Development Team";

        TeamForCategories team = new TeamForCategories(expectedId, expectedName);

        assertEquals(expectedId, team.getTeamId(), "Team ID should be set correctly via constructor.");
        assertEquals(expectedName, team.getName(), "Team name should be set correctly via constructor.");
    }

    @Test
    public void testSetTeamId() {
        Long id = 2L;
        TeamForCategories team = new TeamForCategories(1L, "Development Team");
        team.setTeamId(id);

        assertEquals(id, team.getTeamId(), "Team ID should be updated correctly.");
    }

    @Test
    public void testSetName() {
        String name = "Marketing Team";
        TeamForCategories team = new TeamForCategories(1L, "Development Team");
        team.setName(name);

        assertEquals(name, team.getName(), "Team name should be updated correctly.");
    }

    @Test
    public void testGetTeamId() {
        Long id = 3L;
        TeamForCategories team = new TeamForCategories(id, "Development Team");

        assertEquals(id, team.getTeamId(), "Team ID should be retrieved correctly.");
    }

    @Test
    public void testGetName() {
        String name = "HR Team";
        TeamForCategories team = new TeamForCategories(1L, name);

        assertEquals(name, team.getName(), "Team name should be retrieved correctly.");
    }
}
