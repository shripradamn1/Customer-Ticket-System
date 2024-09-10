package com.spring_boot.CSTS.Repository;

import com.spring_boot.CSTS.model.Category;
import com.spring_boot.CSTS.model.Team;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
//@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAllById(Iterable<Long> ids);

   // List<Team> findByCategory(Category category);

    List<Team> findByCategoryId(Long categoryId);

//    @Transactional
//    @Modifying
//    @Query(value = "ALTER TABLE your_table AUTO_INCREMENT = 1", nativeQuery = true)
//    void resetAutoIncrement();
}
