package com.spring_boot.CSTS.Repository;

import com.spring_boot.CSTS.model.SupportAgent;
import com.spring_boot.CSTS.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<SupportAgent, Long> {
}
