package com.spring_boot.CSTS.Controller;

import com.spring_boot.CSTS.Service.SupportAgentService;
import com.spring_boot.CSTS.model.SupportAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
public class SupportAgentController {

    @Autowired
    private SupportAgentService supportAgentService;

    @PostMapping("/save")
    public ResponseEntity<SupportAgent> saveAgent(@RequestBody SupportAgent agent) {
        SupportAgent savedAgent = supportAgentService.saveAgent(agent);
        return ResponseEntity.ok(savedAgent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupportAgent> getAgentById(@PathVariable Long id) {
        SupportAgent agent = supportAgentService.findAgentById(id);
        if (agent != null) {
            return ResponseEntity.ok(agent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<SupportAgent>> getAllAgents() {
        List<SupportAgent> agents = supportAgentService.getAllAgents();
        return ResponseEntity.ok(agents);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAgentById(@PathVariable Long id) {
        supportAgentService.deleteAgentById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<SupportAgent>> getAgentsByTeamId(@PathVariable Long teamId) {
        List<SupportAgent> agents = supportAgentService.findAgentsByTeamId(teamId);
        return ResponseEntity.ok(agents);
    }
}
