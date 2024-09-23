package com.spring_boot.CSTS.Repository;

import com.spring_boot.CSTS.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Correct method signature for finding by ID
    Optional<Category> findById(Long id);
}
