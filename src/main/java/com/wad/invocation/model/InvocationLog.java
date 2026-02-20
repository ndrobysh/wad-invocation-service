package com.wad.invocation.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "invocation_logs")
public class InvocationLog {

    @Id
    private String id;
    
    private String username;
    private String baseMonsterId;
    private String generatedMonsterId; // Nullable at start
    
    private InvocationStatus status;
    private LocalDateTime timestamp;
    private String errorMessage;

    public InvocationLog() {}

    public InvocationLog(String id, String username, String baseMonsterId, String generatedMonsterId, InvocationStatus status, LocalDateTime timestamp, String errorMessage) {
        this.id = id;
        this.username = username;
        this.baseMonsterId = baseMonsterId;
        this.generatedMonsterId = generatedMonsterId;
        this.status = status;
        this.timestamp = timestamp;
        this.errorMessage = errorMessage;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getBaseMonsterId() { return baseMonsterId; }
    public void setBaseMonsterId(String baseMonsterId) { this.baseMonsterId = baseMonsterId; }
    public String getGeneratedMonsterId() { return generatedMonsterId; }
    public void setGeneratedMonsterId(String generatedMonsterId) { this.generatedMonsterId = generatedMonsterId; }
    public InvocationStatus getStatus() { return status; }
    public void setStatus(InvocationStatus status) { this.status = status; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
