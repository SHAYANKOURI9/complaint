package com.municipality.complaintservice.dto;

import jakarta.validation.constraints.NotBlank;

public class CommentRequest {
    
    @NotBlank(message = "Comment content is required")
    private String content;
    
    // Getters and Setters
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
}