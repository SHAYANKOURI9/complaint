package com.municipality.complaintservice.service;

import com.municipality.complaintservice.model.Complaint;
import com.municipality.complaintservice.model.ComplaintStatus;
import com.municipality.complaintservice.model.Comment;
import com.municipality.complaintservice.repository.ComplaintRepository;
import com.municipality.complaintservice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {
    
    @Autowired
    private ComplaintRepository complaintRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    public Complaint createComplaint(Complaint complaint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // In a real application, you would get the user ID from the JWT token
        // For now, we'll use a default value
        complaint.setUserId(1L); // This should come from the authenticated user
        complaint.setStatus(ComplaintStatus.PENDING);
        
        Complaint savedComplaint = complaintRepository.save(complaint);
        
        // Send notification
        notificationService.sendComplaintCreatedNotification(savedComplaint);
        
        return savedComplaint;
    }
    
    public List<Complaint> getComplaintsByUser(Long userId) {
        return complaintRepository.findByUserId(userId);
    }
    
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }
    
    public Optional<Complaint> getComplaintById(Long id) {
        return complaintRepository.findById(id);
    }
    
    public Complaint updateComplaintStatus(Long complaintId, ComplaintStatus status) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        
        complaint.setStatus(status);
        Complaint updatedComplaint = complaintRepository.save(complaint);
        
        // Send notification
        notificationService.sendStatusUpdateNotification(updatedComplaint);
        
        return updatedComplaint;
    }
    
    public Complaint assignComplaintToDepartment(Long complaintId, String department) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        
        complaint.setAssignedDepartment(department);
        complaint.setStatus(ComplaintStatus.ASSIGNED);
        
        Complaint updatedComplaint = complaintRepository.save(complaint);
        
        // Send notification
        notificationService.sendAssignmentNotification(updatedComplaint);
        
        return updatedComplaint;
    }
    
    public Comment addComment(Long complaintId, Comment comment) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        
        comment.setComplaint(complaint);
        // In a real application, you would get the user ID from the JWT token
        comment.setUserId(1L); // This should come from the authenticated user
        
        Comment savedComment = commentRepository.save(comment);
        
        // Send notification
        notificationService.sendCommentNotification(complaint, savedComment);
        
        return savedComment;
    }
    
    public List<Comment> getCommentsByComplaintId(Long complaintId) {
        return commentRepository.findByComplaintIdOrderByCreatedAtAsc(complaintId);
    }
    
    public boolean canAccessComplaint(Long complaintId, Long userId, String role) {
        if ("ADMIN".equals(role) || "STAFF".equals(role)) {
            return true;
        }
        
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        
        return complaint.getUserId().equals(userId);
    }
}