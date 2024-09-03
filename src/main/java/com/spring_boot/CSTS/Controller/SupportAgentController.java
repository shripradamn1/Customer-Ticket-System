package com.spring_boot.CSTS.Controller;

import com.spring_boot.CSTS.Service.SupportAgentService;
import com.spring_boot.CSTS.model.SupportAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/agents")
public class SupportAgentController {
    @Autowired
    private SupportAgentService supportAgentService;

    @Autowired
    private SupportAgentService agentService;

    @PostMapping("/team/{teamId}")
    public SupportAgent createAgent(@PathVariable Long teamId, @RequestBody SupportAgent agent) {
        return agentService.createAgent(teamId, agent);
    }
    @GetMapping
    public List<SupportAgent> getagents(){
        return agentService.getAgents();
    }
    @GetMapping("/{agentid}")
    public Optional<SupportAgent> getAgent(@PathVariable Long agentid){
        return agentService.getAgent(agentid);

    }


}
