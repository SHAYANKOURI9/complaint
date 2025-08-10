package com.municipality.complaintservice.service;

import com.municipality.complaintservice.model.Complaint;
import com.municipality.complaintservice.model.Comment;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    
    public void sendComplaintCreatedNotification(Complaint complaint) {
        // In a real application, this would integrate with the notification service
        System.out.println("Notification: Complaint #" + complaint.getId() + " has been created");
    }
    
    public void sendStatusUpdateNotification(Complaint complaint) {
        // In a real application, this would integrate with the notification service
        System.out.println("Notification: Complaint #" + complaint.getId() + " status updated to " + complaint.getStatus());
    }
    
    public void sendAssignmentNotification(Complaint complaint) {
        // In a real application, this would integrate with the notification service
        System.out.println("Notification: Complaint #" + complaint.getId() + " assigned to " + complaint.getAssignedDepartment());
    }
    
    public void sendCommentNotification(Complaint complaint, Comment comment) {
        // In a real application, this would integrate with the notification service
        System.out.println("Notification: New comment added to complaint #" + complaint.getId());
    }
}