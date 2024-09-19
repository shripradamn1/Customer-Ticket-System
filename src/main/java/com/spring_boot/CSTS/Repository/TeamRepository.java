package com.spring_boot.CSTS.Repository;

import com.spring_boot.CSTS.model.Team;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE teams AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
    List<Team> findAllById(Iterable<Long> ids);

    // Find teams by category ID
    List<Team> findByCategoryId(Long categoryId);

    // Count the number of teams

}
