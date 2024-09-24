package com.spring_boot.CSTS.Controller;

import com.spring_boot.CSTS.Service.CategoryService;
import com.spring_boot.CSTS.model.Category;

import com.spring_boot.CSTS.model.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        List<Category>categories=categoryService.getAllCategories();
        List<CategoryDTO> categoryDTOS=categories.stream().map(c->new CategoryDTO(c.getId(),c.getName())).toList();
        return categoryDTOS;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();

    }
}
