package com.saumik.taskmanagement.exception;

public class DuplicateTaskTitleException extends RuntimeException {
    public DuplicateTaskTitleException(String message) {
        super(message);
    }
}
