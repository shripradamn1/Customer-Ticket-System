package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.model.SupportAgent;
import com.spring_boot.CSTS.model.Team;
import com.spring_boot.CSTS.Repository.SupportAgentRepository;
import com.spring_boot.CSTS.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupportAgentService {
    @Autowired
    private SupportAgentRepository agentRepository;

    @Autowired
    private TeamRepository teamRepository;

    public SupportAgent createAgent(Long teamId, SupportAgent agent) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        agent.setTeam(team);
        return agentRepository.save(agent);
    }
    public List<SupportAgent> getAgents(){
        return agentRepository.findAll();
    }


    public Optional<SupportAgent> getAgent(Long id){
        return agentRepository.findById(id);
    }
}
