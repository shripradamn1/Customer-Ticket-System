package com.spring_boot.CSTS.model;

import org.hibernate.engine.internal.ImmutableEntityEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SupportAgentTest {

    private SupportAgent supportAgent;

    @BeforeEach
    public void setUp() {
        supportAgent = new SupportAgent();
    }

    @Test
    public void testGetAndSetId() {
        Long id = 1L;
        supportAgent.setId(id);
        assertEquals(id, supportAgent.getId(), "SupportAgent ID should be set correctly.");
    }

    @Test
    public void testGetAndSetName() {
        String name = "John Doe";
        supportAgent.setName(name);
        assertEquals(name, supportAgent.getName(), "SupportAgent name should be set correctly.");
    }

    @Test
    public void testGetAndSetUsername() {
        String username = "user";
        supportAgent.setUsername(username);
        assertEquals(username, supportAgent.getUsername(), "SupportAgent username should be set correctly.");
    }

    @Test
    public void testGetAndSetTeam() {
        Team team = new Team();
        team.setId(1L);
        team.setName("Team Alpha");

        supportAgent.setTeam(team);
        assertEquals(team, supportAgent.getTeam(), "SupportAgent team should be set correctly.");
    }

    @Test
    public void testGetAndSetCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Tech Support");

        supportAgent.setCategory(category);
        assertEquals(category, supportAgent.getCategory(), "SupportAgent category should be set correctly.");
    }

    @Test
    public void testGetAndSetAssignedTickets() {
        Ticket ticket1 = new Ticket();
        ticket1.setId(1L);
        ticket1.setDescription("Issue 1");

        Ticket ticket2 = new Ticket();
        ticket2.setId(2L);
        ticket2.setDescription("Issue 2");
        Ticket ticket3 = new Ticket();
        Set<Ticket> tickets = new HashSet<>();
        tickets.add(ticket1);
        tickets.add(ticket2);

        supportAgent.setAssignedTickets(tickets);

        Set<Ticket> retrievedTickets = supportAgent.getAssignedTickets();
        assertEquals(2, retrievedTickets.size(), "SupportAgent should have 2 assigned tickets.");
        assertTrue(retrievedTickets.contains(ticket1), "SupportAgent should have ticket1 assigned.");
        assertTrue(retrievedTickets.contains(ticket2), "SupportAgent should have ticket2 assigned.");
        assertFalse(retrievedTickets.contains(ticket3), "SupportAgent should not have ticket3 assigned.");
    }

    @Test
    public void testEmptyAssignedTickets() {
        Set<Ticket> tickets = new HashSet<>();
        supportAgent.setAssignedTickets(tickets);
        assertTrue(supportAgent.getAssignedTickets().isEmpty(), "Assigned tickets should be empty.");
    }
}
