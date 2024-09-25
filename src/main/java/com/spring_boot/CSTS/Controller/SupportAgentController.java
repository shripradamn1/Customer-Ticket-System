package com.spring_boot.CSTS.Controller;

import com.spring_boot.CSTS.Repository.AgentRepository;
import com.spring_boot.CSTS.Service.SupportAgentService;
import com.spring_boot.CSTS.model.SupportAgent;
//import com.spring_boot.CSTS.model.SupportAgentDTO;
import com.spring_boot.CSTS.model.SupportAgentDTO;
import com.spring_boot.CSTS.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/agents")
public class SupportAgentController {
    @Autowired
    AgentRepository agentRepository;
    @Autowired
    private SupportAgentService supportAgentService;
    @Autowired
    private SupportAgentService agentService;
    @PostMapping("/category/{categoryId}/team/{teamId}")
    public SupportAgent createAgent(@PathVariable Long categoryId,@PathVariable Long teamId, @RequestBody SupportAgent agent) {
        return agentService.createAgent(categoryId,teamId, agent);
    }
    @GetMapping("/agents")
    public List<SupportAgent> getagents(){
        return agentService.getAgents();
    }
//    @GetMapping("/{agentid}")
//    public Optional<SupportAgent> getAgent(@PathVariable Long agentid){
//        return agentService.getAgentById(agentid);
//
//    }
    @PutMapping("/update/{agentid}/{ticketid}")
    public Optional<Ticket>updateTicket(@PathVariable Long aid,@PathVariable Long tid,@RequestParam String status){
      Ticket ticket=  agentService.updateTicketStatus(aid,tid, Ticket.Status.valueOf(status));
      return Optional.ofNullable(ticket);

    }
    @GetMapping
    public List<SupportAgentDTO> getAgentss() {
        System.out.println(agentService.getAgentss());
        return agentService.getAgentss();
    }
    @GetMapping("/checkUsername/{username}")
    public ResponseEntity<Map<String, Object>> checkUsername(@PathVariable String username) {
        boolean exists = supportAgentService.checkUsernameExists(username);
        Map<String, Object> response = new HashMap<>();

        // Prepare the response based on existence of the username
        if (!exists) {
            response.put("exists", false);
            response.put("message", "Username does not exists..");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // 409 Conflict
        } else {
            response.put("exists", true);
            response.put("message", "agent name exists");
            return ResponseEntity.ok(response); // 200 OK
        }
    }
}
