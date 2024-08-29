package com.spring_boot.CSTS.Controller;

import com.spring_boot.CSTS.Service.AgentService;
import com.spring_boot.CSTS.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/tickets")

public class AgentController {

  @Autowired
    AgentService agentService;
 @GetMapping("/all-tickets")
    public ResponseEntity<List<Ticket>> getTickets(){
     return ResponseEntity.ok(agentService.getAllUserTickets());

 }
 @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateTickets(@PathVariable long id,@RequestParam String status){
     return ResponseEntity.ok(agentService.updateTicketStatus(id,status));
 }


}
