package com.ifsc.tarefas.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

public class ApiError {
    //informações do erro
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private Map<String, String> fields;

    //construtor
    public ApiError(LocalDateTime timestamp, int status, String error, String message, String path, Map<String, String> fields) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.fields = fields;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public Map<String, String> getFields() {
        return fields;
    }
    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }
    
    
}
