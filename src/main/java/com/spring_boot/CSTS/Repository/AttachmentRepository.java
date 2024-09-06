package com.spring_boot.CSTS.Repository;
 
import com.spring_boot.CSTS.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
 
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}