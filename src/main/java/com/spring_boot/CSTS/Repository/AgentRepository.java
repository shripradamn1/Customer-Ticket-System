package com.spring_boot.CSTS.Repository;

import com.spring_boot.CSTS.model.SupportAgent;
import com.spring_boot.CSTS.model.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AgentRepository extends JpaRepository<SupportAgent, Long> {
    boolean existsByUsername(String username);
    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE your_table AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}
