package com.spring_boot.CSTS.Service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body, boolean isHtml) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, isHtml);  // true for HTML content

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    // Method to build the HTML email content for ticket creation
    public String buildTicketCreationEmail(String userName, String ticketTitle, Long ticketId, 
                                           String ticketDescription, String ticketLink, String supportAgentName) {
        return String.format("""
           <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ticket Submission Successful</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        .email-container {
            background-color: #ffffff;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            max-width: 600px;
            margin: 0 auto;
        }
        .header {
            background-color: #4c9fd4;
            padding: 20px;
            text-align: center;
            color: white;
            font-size: 24px;
            font-weight: bold;
        }
        .content {
            padding: 20px;
        }
        .ticket-info {
            margin: 20px 0;
            padding: 15px;
            background-color: #f9f9f9;
            border-left: 4px solid #4c9fd4;
        }
        .btn {
            display: inline-block;
            background-color: #4c9fd4;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
        }
        .btn:hover {
            background-color: #3b8ab0;
        }
        .footer {
            padding: 20px;
            text-align: center;
            font-size: 12px;
            color: #777;
        }
    </style>
</head>
<body>
    <div class="email-container">
        <div class="header">
            Great News! Your Ticket Is Submitted!
        </div>

        <div class="content">
            <p>Hello %s,</p>
            <p>We are excited to confirm that we’ve received your request. Your new ticket number is <strong>#%d</strong> and we've assigned it to a support agent, <strong>%s</strong>.</p>
            <p>Here’s what you told us about the issue:</p>
            <div class="ticket-info">
                <p>%s</p>
            </div>
            <p>To check the status of your ticket or add more information, please click the button below:</p>
            <a href="%s" class="btn">View Ticket</a>
        </div>

        <div class="footer">
            Awesome Support © 2024. We’re here to help you!
        </div>
    </div>
</body>
</html>
        """, userName, ticketId, supportAgentName, ticketDescription, ticketLink);
    }

    // Method to build the HTML email content for ticket status updates
    public String buildTicketStatusUpdateEmail(String userName, Long ticketId, String ticketTitle, 
    String oldStatus, String newStatus,
    String oldPriority, String newPriority,
    String supportAgentName, String ticketLink) {
return String.format("""
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Ticket Status Updated</title>
<style>
body {
font-family: Arial, sans-serif;
background-color: #f4f4f4;
margin: 0;
padding: 20px;
}
.email-container {
background-color: #ffffff;
border-radius: 8px;
overflow: hidden;
box-shadow: 0 0 10px rgba(0,0,0,0.1);
max-width: 600px;
margin: 0 auto;
}
.header {
background-color: #4c9fd4;
padding: 20px;
text-align: center;
color: white;
font-size: 24px;
font-weight: bold;
}
.content {
padding: 20px;
}
.ticket-info {
margin: 20px 0;
padding: 15px;
background-color: #f9f9f9;
border-left: 4px solid #4c9fd4;
}
.btn {
display: inline-block;
background-color: #4c9fd4;
color: white;
padding: 10px 20px;
text-decoration: none;
border-radius: 5px;
font-weight: bold;
}
.btn:hover {
background-color: #3b8ab0;
}
.footer {
padding: 20px;
text-align: center;
font-size: 12px;
color: #777;
}
</style>
</head>
<body>
<div class="email-container">
<div class="header">
Your Ticket Status Has Been Updated!
</div>

<div class="content">
<p>Hello %s,</p>
<p>Your ticket <strong>#%d</strong> titled "<strong>%s</strong>" has been updated.</p>
<p>Previous status: <strong>%s</strong></p>
<p>New status: <strong>%s</strong></p>
<p>Previous priority: <strong>%s</strong></p>
<p>New priority: <strong>%s</strong></p>
<p>Assigned agent: <strong>%s</strong></p>
<p>To check the status of your ticket or add more information, please click the button below:</p>
<a href="%s" class="btn">View Ticket</a>
</div>

<div class="footer">
Awesome Support © 2024. We’re here to help you!
</div>
</div>
</body>
</html>
""", userName, ticketId, ticketTitle, oldStatus, newStatus, oldPriority, newPriority, supportAgentName, ticketLink);
}

public String buildTicketResolvedEmail(String userName, Long ticketId, String ticketTitle, 
String supportAgentName, String feedbackLink) {
return String.format("""
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Ticket Resolved</title>
<style>
body {
font-family: Arial, sans-serif;
background-color: #f4f4f4;
margin: 0;
padding: 20px;
}
.email-container {
background-color: #ffffff;
border-radius: 8px;
overflow: hidden;
box-shadow: 0 0 10px rgba(0,0,0,0.1);
max-width: 600px;
margin: 0 auto;
}
.header {
background-color: #4c9fd4;
padding: 20px;
text-align: center;
color: white;
font-size: 24px;
font-weight: bold;
}
.content {
padding: 20px;
}
.footer {
padding: 20px;
text-align: center;
font-size: 12px;
color: #777;
}
</style>
</head>
<body>
<div class="email-container">
<div class="header">
Your Ticket Has Been Resolved!
</div>

<div class="content">
<p>Hello %s,</p>
<p>Your ticket <strong>#%d</strong> titled "<strong>%s</strong>" has been resolved.</p>
<p>Assigned agent: <strong>%s</strong></p>
<p>We would appreciate your feedback on the resolution of this issue.</p>
<p>To leave your feedback, please click the button below:</p>
<a href="%s" class="btn">Give Feedback</a>
</div>

<div class="footer">
Awesome Support Public Test © 2024. We’re here to help you!
</div>
</div>
</body>
</html>
""", userName, ticketId, ticketTitle, supportAgentName, feedbackLink);
}

}



