package com.municipality.notificationservice.controller;

import com.municipality.notificationservice.dto.NotificationRequest;
import com.municipality.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;
    
    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendNotification(@Valid @RequestBody NotificationRequest request) {
        try {
            notificationService.sendNotification(request);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Notification sent successfully");
            response.put("type", request.getType().toString());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to send notification: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/complaint-status")
    public ResponseEntity<Map<String, String>> sendComplaintStatusUpdate(
            @RequestParam String userEmail,
            @RequestParam String complaintId,
            @RequestParam String status) {
        try {
            notificationService.sendComplaintStatusUpdate(userEmail, complaintId, status);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Status update notification sent successfully");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to send status update notification: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/complaint-assignment")
    public ResponseEntity<Map<String, String>> sendComplaintAssignment(
            @RequestParam String userEmail,
            @RequestParam String complaintId,
            @RequestParam String department) {
        try {
            notificationService.sendComplaintAssignment(userEmail, complaintId, department);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Assignment notification sent successfully");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to send assignment notification: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
}