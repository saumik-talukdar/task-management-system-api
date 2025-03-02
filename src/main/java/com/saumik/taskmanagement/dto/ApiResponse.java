package com.saumik.taskmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private PaginationMetadata paginationMetadata;
    private HttpStatus status;
    private LocalDateTime timestamp;

    public ApiResponse(boolean success, String message, T data, PaginationMetadata paginationMetadata, HttpStatus status) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.paginationMetadata = paginationMetadata;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(boolean success, String message, T data, HttpStatus status) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.paginationMetadata = null;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PaginationMetadata getPaginationMetadata() {
        return paginationMetadata;
    }

    public void setPaginationMetadata(PaginationMetadata paginationMetadata) {
        this.paginationMetadata = paginationMetadata;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}

