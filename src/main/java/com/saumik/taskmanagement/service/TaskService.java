package com.saumik.taskmanagement.service;

import com.saumik.taskmanagement.dto.TaskDTO;
import com.saumik.taskmanagement.dto.ApiResponse;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    ApiResponse<TaskDTO> addTask(TaskDTO task);
    ApiResponse<TaskDTO> updateTask(Long id, TaskDTO task);
    ApiResponse<TaskDTO> deleteTask(Long id);
    ApiResponse<TaskDTO> getTask(Long id);
    ApiResponse getTasks(String status, String title, Pageable pageable) ;
}
