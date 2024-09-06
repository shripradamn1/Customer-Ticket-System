package com.spring_boot.CSTS.Controller;

import com.spring_boot.CSTS.model.TicketLog;
import com.spring_boot.CSTS.Service.TicketLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tickets/{ticketId}/logs")
public class TicketLogController {

    @Autowired
    private TicketLogService ticketLogService;

    // Add a log to a specific ticket
    @PostMapping
    public ResponseEntity<TicketLog> addTicketLog(@PathVariable Long ticketId, @RequestBody TicketLog log) {
        TicketLog createdLog = ticketLogService.addTicketLog(ticketId, log);
        return ResponseEntity.created(URI.create("/api/tickets/" + ticketId + "/logs/" + createdLog.getId()))
                .body(createdLog);
    }

    // Get all logs for a specific ticket
    @GetMapping
    public ResponseEntity<List<TicketLog>> getTicketLogs(@PathVariable Long ticketId) {
        List<TicketLog> logs = ticketLogService.getLogsByTicketId(ticketId);
        return ResponseEntity.ok(logs);
    }
}
