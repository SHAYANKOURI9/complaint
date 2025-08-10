package com.municipality.complaintservice.dto;

import com.municipality.complaintservice.model.ComplaintCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ComplaintRequest {
    
    @NotNull(message = "Category is required")
    private ComplaintCategory category;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    // Getters and Setters
    public ComplaintCategory getCategory() {
        return category;
    }
    
    public void setCategory(ComplaintCategory category) {
        this.category = category;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}