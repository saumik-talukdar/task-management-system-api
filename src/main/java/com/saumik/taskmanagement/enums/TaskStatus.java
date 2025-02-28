package com.saumik.taskmanagement.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TaskStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED;
}
