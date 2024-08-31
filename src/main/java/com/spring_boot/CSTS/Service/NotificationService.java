package com.spring_boot.CSTS.Service;

import com.spring_boot.CSTS.model.Notification;
import com.spring_boot.CSTS.model.Ticket;
import com.spring_boot.CSTS.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private NotificationRepository notificationRepository;

    public void sendTicketCreationNotification(Ticket ticket) {
        String subject = "New Ticket Created: " + ticket.getTitle();
        String body = "Dear " + ticket.getCustomer().getName() + ",\n\n"
                + "Your ticket has been created successfully.\n"
                + "Title: " + ticket.getTitle() + "\n"
                + "Description: " + ticket.getDescription() + "\n"
                + "Priority: " + ticket.getPriority() + "\n"
                + "Category: " + ticket.getCategory().getName() + "\n"
                + "Status: " + ticket.getStatus() + "\n\n"
                + "Thank you for contacting support.";

        sendEmail(ticket.getCustomer().getEmail(), subject, body);
        saveNotification(ticket.getCustomer().getId(), body);
    }

    public void sendTicketStatusUpdateNotification(Ticket ticket) {
        String subject = "Ticket Status Updated: " + ticket.getTitle();
        String body = "Dear " + ticket.getCustomer().getName() + ",\n\n"
                + "The status of your ticket has been updated.\n"
                + "Title: " + ticket.getTitle() + "\n"
                + "New Status: " + ticket.getStatus() + "\n\n"
                + "Thank you for contacting support.";

        sendEmail(ticket.getCustomer().getEmail(), subject, body);
        saveNotification(ticket.getCustomer().getId(), body);
    }

    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    private void saveNotification(Long userId, String message) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }
}
