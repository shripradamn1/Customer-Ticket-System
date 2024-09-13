package com.spring_boot.CSTS.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

class TeamTest {

    private Team team;
    private Category category;
    private SupportAgent agent1, agent2;
    private Ticket ticket1, ticket2;

    @BeforeEach
    public void setUp() {
        team = new Team();
        category = new Category();
        agent1 = new SupportAgent();
        agent2 = new SupportAgent();
        ticket1 = new Ticket();
        ticket2 = new Ticket();
    }

    @Test
    public void testGetAndSetId() {
        Long id = 1L;
        team.setId(id);
        assertEquals(id, team.getId(), "Team ID should be set correctly.");
    }

    @Test
    public void testGetAndSetName() {
        String name = "Support Team A";
        team.setName(name);
        assertEquals(name, team.getName(), "Team name should be set correctly.");
    }

    @Test
    public void testGetAndSetCategory() {
        category.setId(1L);
        category.setName("Customer Support");
        team.setCategory(category);
        assertEquals(category, team.getCategory(), "Category should be set correctly.");
    }

    @Test
    public void testGetAndSetAgents() {
        agent1.setId(1L);
        agent2.setId(2L);
        Set<SupportAgent> agents = new HashSet<>();
        agents.add(agent1);
        agents.add(agent2);

        team.setAgents(agents);

        Set<SupportAgent> retrievedAgents = team.getAgents();
        assertEquals(2, retrievedAgents.size(), "Team should have 2 agents.");
        assertTrue(retrievedAgents.contains(agent1), "Team should contain agent1.");
        assertTrue(retrievedAgents.contains(agent2), "Team should contain agent2.");
    }

    @Test
    public void testEmptyAgents() {
        team.setAgents(new HashSet<>());
        assertTrue(team.getAgents().isEmpty(), "Agents set should be empty.");
    }
}
