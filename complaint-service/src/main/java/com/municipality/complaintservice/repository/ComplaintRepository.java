package com.municipality.complaintservice.repository;

import com.municipality.complaintservice.model.Complaint;
import com.municipality.complaintservice.model.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByUserId(Long userId);
    List<Complaint> findByStatus(ComplaintStatus status);
    List<Complaint> findByAssignedDepartment(String department);
    List<Complaint> findByCategory(String category);
}