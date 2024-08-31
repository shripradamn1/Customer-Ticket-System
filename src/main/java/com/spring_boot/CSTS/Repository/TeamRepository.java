package com.spring_boot.CSTS.Repository;

import com.spring_boot.CSTS.model.Team;
import com.spring_boot.CSTS.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByCategoriesContains(Category category);
}
