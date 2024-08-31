package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.Repository.SupportAgentRepository;
import com.spring_boot.CSTS.model.SupportAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupportAgentService {

    @Autowired
    private SupportAgentRepository supportAgentRepository;

    public SupportAgent saveAgent(SupportAgent agent) {
        return supportAgentRepository.save(agent);
    }

    public SupportAgent findAgentById(Long id) {
        return supportAgentRepository.findById(id).orElse(null);
    }

    public List<SupportAgent> getAllAgents() {
        return supportAgentRepository.findAll();
    }

    public void deleteAgentById(Long id) {
        supportAgentRepository.deleteById(id);
    }

    public List<SupportAgent> findAgentsByTeamId(Long teamId) {
        return supportAgentRepository.findAll().stream()
                .filter(agent -> agent.getTeam() != null && agent.getTeam().getId().equals(teamId))
                .toList();
    }
}
