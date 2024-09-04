package com.spring_boot.CSTS.Repository;

import com.spring_boot.CSTS.model.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
//    @Transactional
//    @Modifying
//    @Query(value = "ALTER TABLE your_table AUTO_INCREMENT = 1", nativeQuery = true)
//    void resetAutoIncrement();
}
