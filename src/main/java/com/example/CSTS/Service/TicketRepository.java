package com.example.CSTS.Service;


import com.example.CSTS.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository


public interface TicketRepository extends JpaRepository<Ticket, String> {

}
