package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Message {
    private String content;
    private String senderName = "defaultSender";

    // No-args constructor for JPA
    public Message() {
    }

    // Constructor with parameters
    public Message(String content, String senderName) {
        this.content = content;
        this.senderName = senderName;
    }
}
