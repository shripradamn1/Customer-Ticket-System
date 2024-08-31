
package com.spring_boot.CSTS.Repository;

import com.spring_boot.CSTS.model.Notification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	List<Notification> findByUserId(Long userId);
}
