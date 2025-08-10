package com.municipality.complaintservice.controller;

import com.municipality.complaintservice.dto.CommentRequest;
import com.municipality.complaintservice.model.Complaint;
import com.municipality.complaintservice.model.ComplaintStatus;
import com.municipality.complaintservice.model.Comment;
import com.municipality.complaintservice.service.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "*")
public class ComplaintController {
    
    @Autowired
    private ComplaintService complaintService;
    
    @PostMapping
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<Complaint> createComplaint(@Valid @RequestBody Complaint complaint) {
        Complaint createdComplaint = complaintService.createComplaint(complaint);
        return ResponseEntity.ok(createdComplaint);
    }
    
    @GetMapping
    @PreAuthorize("hasRole('CITIZEN') or hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<List<Complaint>> getComplaints() {
        // In a real application, you would filter based on user role and ID
        List<Complaint> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaints);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CITIZEN') or hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<Complaint> getComplaintById(@PathVariable Long id) {
        Optional<Complaint> complaint = complaintService.getComplaintById(id);
        return complaint.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<Complaint> updateComplaintStatus(
            @PathVariable Long id,
            @RequestParam ComplaintStatus status) {
        Complaint updatedComplaint = complaintService.updateComplaintStatus(id, status);
        return ResponseEntity.ok(updatedComplaint);
    }
    
    @PutMapping("/{id}/assign")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<Complaint> assignComplaintToDepartment(
            @PathVariable Long id,
            @RequestParam String department) {
        Complaint updatedComplaint = complaintService.assignComplaintToDepartment(id, department);
        return ResponseEntity.ok(updatedComplaint);
    }
    
    @PostMapping("/{id}/comments")
    @PreAuthorize("hasRole('CITIZEN') or hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<Comment> addComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        
        Comment savedComment = complaintService.addComment(id, comment);
        return ResponseEntity.ok(savedComment);
    }
    
    @GetMapping("/{id}/comments")
    @PreAuthorize("hasRole('CITIZEN') or hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<List<Comment>> getCommentsByComplaintId(@PathVariable Long id) {
        List<Comment> comments = complaintService.getCommentsByComplaintId(id);
        return ResponseEntity.ok(comments);
    }
}