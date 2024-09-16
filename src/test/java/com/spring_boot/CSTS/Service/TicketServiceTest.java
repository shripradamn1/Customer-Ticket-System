// package com.spring_boot.CSTS.Service;

// import com.spring_boot.CSTS.Repository.CategoryRepository;
// import com.spring_boot.CSTS.Repository.SupportAgentRepository;
// import com.spring_boot.CSTS.Repository.TeamRepository;
// import com.spring_boot.CSTS.model.Category;
// import com.spring_boot.CSTS.model.SupportAgent;
// import com.spring_boot.CSTS.model.Team;
// import com.spring_boot.CSTS.model.Ticket;
// import com.spring_boot.CSTS.Repository.TicketRepository;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import java.util.*;
// import java.util.stream.Collectors;
// import java.util.stream.Stream;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// public class TicketServiceTest {

//     @InjectMocks
//     private TicketService ticketService;

//     @Mock
//     private TicketRepository ticketRepository;

//     @Mock
//     private CategoryRepository categoryRepository;

//     @Mock
//     private TeamRepository teamRepository;

//     @Mock
//     private SupportAgentRepository supportAgentRepository;

//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     public void testCreateTicket_Success() {
//         Long userId = 1L;
//         Long categoryId = 1L;
//         Long teamId = 1L;

//         Category category = new Category();
//         Team team = new Team();
//         SupportAgent agent = new SupportAgent();
//         agent.setName("Agent 1");
//         team.setAgents(new HashSet<>(Collections.singletonList(agent)));

//         Ticket ticket = new Ticket();
//         ticket.setTitle("Sample Ticket");
//         ticket.setDescription("Sample Description");

//         when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
//         when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
//         when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

//         Ticket createdTicket = ticketService.createTicket(userId, categoryId, teamId, ticket);

//         assertNotNull(createdTicket);
//         assertEquals(userId, createdTicket.getUserId());
//         assertEquals(category, createdTicket.getCategory());
//         //assertEquals(team, createdTicket.getTeam());
//         assertEquals(Ticket.Status.OPEN, createdTicket.getStatus());
//         assertEquals(agent, createdTicket.getAssignedTo());
//     }

//     @Test
//     public void testCreateTicket_NoAgents() {
//         Long userId = 1L;
//         Long categoryId = 1L;
//         Long teamId = 1L;

//         Category category = new Category();
//         Team team = new Team();
//         team.setAgents(new HashSet<>());

//         Ticket ticket = new Ticket();
//         ticket.setTitle("Sample Ticket");
//         ticket.setDescription("Sample Description");

//         when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
//         when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

//         RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
//             ticketService.createTicket(userId, categoryId, teamId, ticket);
//         });

//         assertEquals("No agents available in the team", thrown.getMessage());
//     }

//     @Test
//     public void testUpdateTicketStatus_Success() throws Exception {
//         Long ticketId = 1L;
//         Ticket.Status newStatus = Ticket.Status.CLOSED;
//         Ticket.Priority newPriority = Ticket.Priority.HIGH;

//         Ticket ticket = new Ticket();
//         ticket.setStatus(Ticket.Status.OPEN);
//         ticket.setPriority(Ticket.Priority.MEDIUM);

//         when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
//         when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

//         Ticket updatedTicket = ticketService.updateTicketStatus(ticketId, newStatus, newPriority);

//         assertNotNull(updatedTicket);
//         assertEquals(newStatus, updatedTicket.getStatus());
//         assertEquals(newPriority, updatedTicket.getPriority());
//     }

//     @Test
//     public void testUpdateTicketStatus_TicketNotFound() {
//         Long ticketId = 1L;
//         Ticket.Status newStatus = Ticket.Status.CLOSED;
//         Ticket.Priority newPriority = Ticket.Priority.HIGH;

//         when(ticketRepository.findById(ticketId)).thenReturn(Optional.empty());

//         Exception thrown = assertThrows(Exception.class, () -> {
//             ticketService.updateTicketStatus(ticketId, newStatus, newPriority);
//         });

//         assertEquals("Ticket not found", thrown.getMessage());
//     }

//     @Test
//     public void testGetAllTickets() {
//         List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());

//         when(ticketRepository.findAll()).thenReturn(tickets);

//         List<Ticket> result = ticketService.getAllTickets();

//         assertNotNull(result);
//         assertEquals(tickets.size(), result.size());
//     }

//     @Test
//     public void testDeleteTicket() {
//         Long ticketId = 1L;

//         ticketService.deleteTicket(ticketId);

//         verify(ticketRepository, times(1)).deleteById(ticketId);
//     }
// }
