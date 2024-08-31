package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.Repository.TicketHistoryRepository;
import com.spring_boot.CSTS.model.Ticket;
import com.spring_boot.CSTS.model.TicketHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketHistoryService {

    @Autowired
    private TicketHistoryRepository ticketHistoryRepository;

    /*
     * Records a history entry for a ticket.
     *
     * @param ticket   The ticket related to the history entry.
     * @param action   The action performed (e.g., "Ticket Created", "Status Updated").
     * @param oldValue The old value before the action (can be null).
     * @param newValue The new value after the action.
     */
    public void recordHistory(Ticket ticket, String action, String oldValue, String newValue) {
        TicketHistory history = new TicketHistory();
        history.setTicket(ticket);
        history.setAction(action);
        history.setOldValue(oldValue);
        history.setNewValue(newValue);
        ticketHistoryRepository.save(history);
    }

    /*
     * Retrieves the history of a specific ticket by its ID.
     *
     * @param ticketId The ID of the ticket.
     * @return A list of history entries related to the ticket.
     */
    public List<TicketHistory> getTicketHistory(Long ticketId) {
        return ticketHistoryRepository.findByTicketId(ticketId);
    }
}
