package com.saumik.taskmanagement.service.impl;

import com.saumik.taskmanagement.dto.PaginationMetadata;
import com.saumik.taskmanagement.dto.TaskDTO;
import com.saumik.taskmanagement.entity.Task;
import com.saumik.taskmanagement.enums.TaskStatus;
import com.saumik.taskmanagement.exception.InvalidTaskParameterException;
import com.saumik.taskmanagement.exception.TaskNotFoundException;
import com.saumik.taskmanagement.exception.DuplicateTaskTitleException;
import com.saumik.taskmanagement.mapper.TaskMapper;
import com.saumik.taskmanagement.dto.ApiResponse;
import com.saumik.taskmanagement.repository.TaskRepository;
import com.saumik.taskmanagement.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public ApiResponse<TaskDTO> addTask(TaskDTO taskDTO) {
        if(taskRepository.findByTitleIgnoreCase(taskDTO.getTitle()) != null) {
            throw  new DuplicateTaskTitleException(taskDTO.getTitle()+" already exists");
        }
        Task newTask = TaskMapper.toEntity(taskDTO);
        newTask = taskRepository.save(newTask);
        return new ApiResponse<>(true, "Task created successfully", TaskMapper.toDTO(newTask), HttpStatus.CREATED);
    }

    @Override
    public ApiResponse<TaskDTO> updateTask(Long id, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
        if(!existingTask.getTitle().equalsIgnoreCase(taskDTO.getTitle()) && taskRepository.findByTitleIgnoreCase(taskDTO.getTitle()) != null) {
            throw  new DuplicateTaskTitleException(taskDTO.getTitle()+" already exists");
        }

        existingTask.setTitle(taskDTO.getTitle());
        existingTask.setDescription(taskDTO.getDescription());
        existingTask.setStatus(taskDTO.getStatus());

        Task updatedTask = taskRepository.save(existingTask);
        return new ApiResponse<>(true, "Task updated successfully", TaskMapper.toDTO(updatedTask), HttpStatus.OK);
    }

    @Override
    public ApiResponse<TaskDTO> deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
        taskRepository.delete(task);
        return new ApiResponse<>(true, "Task deleted successfully", TaskMapper.toDTO(task), HttpStatus.OK);
    }

    @Override
    public ApiResponse<TaskDTO> getTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
        return new ApiResponse<>(true, "Task fetched successfully", TaskMapper.toDTO(task), HttpStatus.OK);
    }

    @Override
    public ApiResponse<List<TaskDTO>> getTasks(String status, String title, Pageable pageable) {
        Page<Task> taskPage;

        // Validate the status if provided
        if(status != null) {
            if(!status.equalsIgnoreCase("PENDING") && !status.equalsIgnoreCase("COMPLETED")) {
                throw new InvalidTaskParameterException("Invalid task status: " + status);
            }
        }

        // Fetch tasks based on status and/or title
        if (status != null && title != null) {
            TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
            taskPage = taskRepository.findByStatusAndTitleContainingIgnoreCase(taskStatus, title, pageable);
        } else if (status != null) {
            TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
            taskPage = taskRepository.findByStatus(taskStatus, pageable);
        } else if (title != null) {
            taskPage = taskRepository.findByTitleContainingIgnoreCase(title, pageable);
        } else {
            taskPage = taskRepository.findAll(pageable);
        }

        // Map the tasks to DTOs
        List<TaskDTO> taskDTOList = taskPage.getContent()
                .stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());

        // Create pagination metadata
        PaginationMetadata paginationMetadata = new PaginationMetadata(
                taskPage.getNumber(),        // Current page number
                taskPage.getSize(),          // Page size
                taskPage.getTotalElements(), // Total elements
                taskPage.getTotalPages(),    // Total pages
                taskPage.hasNext(),          // Whether next page exists
                taskPage.hasPrevious()      // Whether previous page exists
        );

        return new ApiResponse<>(true, "Tasks fetched successfully", taskDTOList, paginationMetadata, HttpStatus.OK);
    }

}
