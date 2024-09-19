package com.spring_boot.CSTS.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring_boot.CSTS.model.Feedback;
import com.spring_boot.CSTS.model.Ticket;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByTicket(Ticket ticket);
}

