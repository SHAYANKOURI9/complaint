package com.municipality.notificationservice.service;

import com.municipality.notificationservice.dto.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    
    @Autowired
    private JavaMailSender emailSender;
    
    public void sendNotification(NotificationRequest request) {
        switch (request.getType()) {
            case EMAIL:
                sendEmail(request);
                break;
            case SMS:
                sendSMS(request);
                break;
            case BOTH:
                sendEmail(request);
                sendSMS(request);
                break;
        }
    }
    
    private void sendEmail(NotificationRequest request) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(request.getEmail());
            message.setSubject(request.getSubject());
            message.setText(request.getMessage());
            
            emailSender.send(message);
            System.out.println("Email sent successfully to: " + request.getEmail());
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
    
    private void sendSMS(NotificationRequest request) {
        // In a real application, this would integrate with an SMS service provider
        // For now, we'll just log the SMS
        System.out.println("SMS sent to: " + request.getPhoneNumber());
        System.out.println("Message: " + request.getMessage());
    }
    
    public void sendComplaintStatusUpdate(String userEmail, String complaintId, String status) {
        NotificationRequest request = new NotificationRequest();
        request.setEmail(userEmail);
        request.setSubject("Complaint Status Update");
        request.setMessage("Your complaint #" + complaintId + " status has been updated to: " + status);
        request.setType(NotificationRequest.NotificationType.EMAIL);
        
        sendNotification(request);
    }
    
    public void sendComplaintAssignment(String userEmail, String complaintId, String department) {
        NotificationRequest request = new NotificationRequest();
        request.setEmail(userEmail);
        request.setSubject("Complaint Assigned");
        request.setMessage("Your complaint #" + complaintId + " has been assigned to " + department + " department.");
        request.setType(NotificationRequest.NotificationType.EMAIL);
        
        sendNotification(request);
    }
    
    public void sendNewCommentNotification(String userEmail, String complaintId) {
        NotificationRequest request = new NotificationRequest();
        request.setEmail(userEmail);
        request.setSubject("New Comment on Complaint");
        request.setMessage("A new comment has been added to your complaint #" + complaintId);
        request.setType(NotificationRequest.NotificationType.EMAIL);
        
        sendNotification(request);
    }
}