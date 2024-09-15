
package com.spring_boot.CSTS.Repository;

import com.spring_boot.CSTS.model.Team;
import com.spring_boot.CSTS.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE users AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();


    public Optional<User> findByEmail(String email);

    public Optional<User> findByUsername(String username);
    Optional<Team> findById(User createdBy);
}
