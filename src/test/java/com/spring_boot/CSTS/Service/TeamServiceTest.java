// package com.spring_boot.CSTS.Service;

// import com.spring_boot.CSTS.Repository.TeamRepository;
// import com.spring_boot.CSTS.model.Team;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import java.util.*;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// public class TeamServiceTest {

//     @InjectMocks
//     private TeamService teamService;

//     @Mock
//     private TeamRepository teamRepository;

//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     public void testGetAllTeams() {
//         List<Team> teams = Arrays.asList(new Team(), new Team());

//         when(teamRepository.findAll()).thenReturn(teams);

//         List<Team> result = teamService.getAllTeams();

//         assertNotNull(result);
//         assertEquals(teams.size(), result.size());
//     }

//     @Test
//     public void testGetTeamById_Success() {
//         Long teamId = 1L;
//         Team team = new Team();
//         team.setId(teamId);

//         when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

//         Optional<Team> result = teamService.getTeamById(teamId);

//         assertTrue(result.isPresent());
//         assertEquals(teamId, result.get().getId());
//     }

//     @Test
//     public void testGetTeamById_NotFound() {
//         Long teamId = 1L;

//         when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

//         Optional<Team> result = teamService.getTeamById(teamId);

//         assertFalse(result.isPresent());
//     }

//     @Test
//     public void testCreateTeam() {
//         Team team = new Team();
//         when(teamRepository.save(any(Team.class))).thenReturn(team);

//         Team result = teamService.createTeam(team);

//         assertNotNull(result);
//         verify(teamRepository, times(1)).save(team);
//     }

//     @Test
//     public void testUpdateTeam_Success() {
//         Long teamId = 1L;
//         Team existingTeam = new Team();
//         existingTeam.setId(teamId);
//         existingTeam.setName("Old Name");

//         Team updatedTeam = new Team();
//         updatedTeam.setName("New Name");
//         //updatedTeam.setCategory("New Category");

//         when(teamRepository.findById(teamId)).thenReturn(Optional.of(existingTeam));
//         when(teamRepository.save(any(Team.class))).thenReturn(existingTeam);

//         Team result = teamService.updateTeam(teamId, updatedTeam);

//         assertNotNull(result);
//         assertEquals("New Name", result.getName());
//         //assertEquals("New Category", result.getCategory());
//         verify(teamRepository, times(1)).save(existingTeam);
//     }

//     @Test
//     public void testUpdateTeam_NotFound() {
//         Long teamId = 1L;
//         Team updatedTeam = new Team();
//         updatedTeam.setName("New Name");
//         //updatedTeam.setCategory("New Category");

//         when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

//         RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
//             teamService.updateTeam(teamId, updatedTeam);
//         });

//         assertEquals("Team not found", thrown.getMessage());
//     }

//     @Test
//     public void testGetTeamsByCategory() {
//         Long categoryId = 1L;
//         List<Team> teams = Arrays.asList(new Team(), new Team());

//         when(teamRepository.findByCategoryId(categoryId)).thenReturn(teams);

//         List<Team> result = teamService.getTeamsByCategory(categoryId);

//         assertNotNull(result);
//         assertEquals(teams.size(), result.size());
//     }

//     @Test
//     public void testDeleteTeam() {
//         Long teamId = 1L;

//         teamService.deleteTeam(teamId);

//         verify(teamRepository, times(1)).deleteById(teamId);
//     }
// }
