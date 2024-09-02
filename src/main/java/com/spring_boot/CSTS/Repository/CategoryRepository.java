package com.spring_boot.CSTS.Repository;

import com.spring_boot.CSTS.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
