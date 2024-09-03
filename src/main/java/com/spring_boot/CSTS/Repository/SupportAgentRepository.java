package com.spring_boot.CSTS.Repository;

import com.spring_boot.CSTS.model.SupportAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportAgentRepository extends JpaRepository<SupportAgent, Long> {
//    @Query("SELECT a FROM SupportAgent a LEFT JOIN FETCH a.tickets WHERE a.id = :id")
//    SupportAgent findByIdWithTickets(@Param("id") Long id);
}
