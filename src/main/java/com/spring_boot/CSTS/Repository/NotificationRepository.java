
package com.spring_boot.CSTS.Repository;

import com.spring_boot.CSTS.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
