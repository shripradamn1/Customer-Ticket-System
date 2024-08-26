
package com.spring_boot.CSTS.Repository;
import com.spring_boot.CSTS.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
