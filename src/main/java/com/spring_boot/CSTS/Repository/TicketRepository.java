
package com.spring_boot.CSTS.Repository;

import com.spring_boot.CSTS.model.SupportAgent;
import com.spring_boot.CSTS.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByAssignedTo(Optional<SupportAgent> assignedTo);
    List<Ticket> findByUserId(Long userId);
    Ticket findByTitle(String title);

   // List<Ticket> findByAgent(SupportAgent agent);
//    @Transactional
//    @Modifying
//    @Query(value = "ALTER TABLE your_table AUTO_INCREMENT = 1", nativeQuery = true)
//    void resetAutoIncrement();
}

