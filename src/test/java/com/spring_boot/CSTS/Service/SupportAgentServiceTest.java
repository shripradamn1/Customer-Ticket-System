package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.Repository.CategoryRepository;
import com.spring_boot.CSTS.Repository.SupportAgentRepository;
import com.spring_boot.CSTS.Repository.TeamRepository;
import com.spring_boot.CSTS.Repository.TicketRepository;
import com.spring_boot.CSTS.model.Category;
import com.spring_boot.CSTS.model.SupportAgent;
import com.spring_boot.CSTS.model.Team;
import com.spring_boot.CSTS.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class SupportAgentServiceTest {

    @InjectMocks
    private SupportAgentService supportAgentService;

    @Mock
    private SupportAgentRepository agentRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private TeamRepository teamRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAgent() {
        Category category = new Category();
        Team team = new Team();
        SupportAgent agent = new SupportAgent();
        agent.setUsername("agent1");

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));
        when(agentRepository.save(any(SupportAgent.class))).thenReturn(agent);

        SupportAgent result = supportAgentService.createAgent(1L, 1L, agent);
        assertNotNull(result);
        assertEquals("agent1", result.getUsername());
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(teamRepository, times(1)).findById(anyLong());
        verify(agentRepository, times(1)).save(any(SupportAgent.class));
    }

    @Test
    public void testCreateAgent_CategoryNotFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        SupportAgent agent = new SupportAgent();
        assertThrows(RuntimeException.class, () -> supportAgentService.createAgent(1L, 1L, agent));
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(teamRepository, never()).findById(anyLong());
        verify(agentRepository, never()).save(any(SupportAgent.class));
    }

    @Test
    public void testCreateAgent_TeamNotFound() {
        Category category = new Category();
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());

        SupportAgent agent = new SupportAgent();
        assertThrows(RuntimeException.class, () -> supportAgentService.createAgent(1L, 1L, agent));
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(teamRepository, times(1)).findById(anyLong());
        verify(agentRepository, never()).save(any(SupportAgent.class));
    }

    @Test
    public void testGetAgents() {
        SupportAgent agent1 = new SupportAgent();
        SupportAgent agent2 = new SupportAgent();
        List<SupportAgent> agents = Arrays.asList(agent1, agent2);

        when(agentRepository.findAll()).thenReturn(agents);

        List<SupportAgent> result = supportAgentService.getAgents();
        assertEquals(2, result.size());
        verify(agentRepository, times(1)).findAll();
    }

    @Test
    public void testGetAgentById() {
        SupportAgent agent = new SupportAgent();
        when(agentRepository.findByUsername(any(String.class))).thenReturn(Optional.of(agent));

        Optional<SupportAgent> result = supportAgentService.getAgentById("agent1");
        assertTrue(result.isPresent());
        assertEquals(agent, result.get());
        verify(agentRepository, times(1)).findByUsername(eq("agent1"));
    }

    @Test
    public void testUpdateTicketStatus() {
        Ticket ticket = new Ticket();
        ticket.setAssignedTo(new SupportAgent());
        ticket.getAssignedTo().setId(1L);
        ticket.setStatus(Ticket.Status.OPEN);

        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket result = supportAgentService.updateTicketStatus(1L, 1L, Ticket.Status.RESOLVED);
        assertNotNull(result);
        assertEquals(Ticket.Status.RESOLVED, result.getStatus());
        verify(ticketRepository, times(1)).findById(anyLong());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    public void testUpdateTicketStatus_TicketNotFound() {
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> supportAgentService.updateTicketStatus(1L, 1L, Ticket.Status.RESOLVED));
        verify(ticketRepository, times(1)).findById(anyLong());
        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    public void testUpdateTicketStatus_Unauthorized() {
        Ticket ticket = new Ticket();
        ticket.setAssignedTo(new SupportAgent());
        ticket.getAssignedTo().setId(2L); // Different agent ID
        ticket.setStatus(Ticket.Status.OPEN);

        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));

        assertThrows(RuntimeException.class, () -> supportAgentService.updateTicketStatus(1L, 1L, Ticket.Status.RESOLVED));
        verify(ticketRepository, times(1)).findById(anyLong());
        verify(ticketRepository, never()).save(any(Ticket.class));
    }
}
