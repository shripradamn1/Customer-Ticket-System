package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.model.Category;
import com.spring_boot.CSTS.model.Team;
import com.spring_boot.CSTS.Repository.CategoryRepository;
import com.spring_boot.CSTS.Repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamService teamService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCategories() {
        Category category1 = new Category();
        Category category2 = new Category();
        List<Category> categories = Arrays.asList(category1, category2);

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAllCategories();
        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void testGetCategoryById() {
        Category category = new Category();
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        Optional<Category> result = categoryService.getCategoryById(1L);
        assertTrue(result.isPresent());
        assertEquals(category, result.get());
        verify(categoryRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testCreateCategory() {
        Category category = new Category();
        category.setName("Category1");
        List<Long> teamIds = Arrays.asList(1L, 2L);
        category.setTeamIds(teamIds);

        Team team1 = new Team();
        Team team2 = new Team();
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team1));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(team2));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category result = categoryService.createCategory(category);
        assertNotNull(result);
        assertEquals("Category1", result.getName());
        verify(teamRepository, times(1)).findById(1L);
        verify(teamRepository, times(1)).findById(2L);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void testUpdateCategory() {
        Category existingCategory = new Category();
        existingCategory.setName("Old Name");
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(existingCategory));

        Category categoryDetails = new Category();
        categoryDetails.setName("New Name");
        List<Long> teamIds = Arrays.asList(1L, 2L);
        categoryDetails.setTeamIds(teamIds);

        Team team1 = new Team();
        Team team2 = new Team();
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team1));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(team2));
        when(categoryRepository.save(any(Category.class))).thenReturn(existingCategory);

        Category result = categoryService.updateCategory(1L, categoryDetails);
        assertNotNull(result);
        assertEquals("New Name", result.getName());
        verify(teamRepository, times(1)).findById(1L);
        verify(teamRepository, times(1)).findById(2L);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void testUpdateCategory_CategoryNotFound() {
        Category categoryDetails = new Category();
        categoryDetails.setName("New Name");
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> categoryService.updateCategory(1L, categoryDetails));
        verify(categoryRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testCreateCategory_TeamNotFound() {
        Category category = new Category();
        category.setName("Category1");
        List<Long> teamIds = Arrays.asList(1L);
        category.setTeamIds(teamIds);

        when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> categoryService.createCategory(category));
        verify(teamRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteCategory() {
        doNothing().when(categoryRepository).deleteById(anyLong());

        categoryService.deleteCategory(1L);
        verify(categoryRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testGetAllTeams() {
        Team team1 = new Team();
        Team team2 = new Team();
        List<Team> teams = Arrays.asList(team1, team2);

        when(teamService.getAllTeams()).thenReturn(teams);

        List<Team> result = categoryService.getAllTeams();
        assertEquals(2, result.size());
        verify(teamService, times(1)).getAllTeams();
    }
}
