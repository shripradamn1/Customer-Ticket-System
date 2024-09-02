package com.spring_boot.CSTS.Repository;

import com.spring_boot.CSTS.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAllById(Iterable<Long> ids);
}
