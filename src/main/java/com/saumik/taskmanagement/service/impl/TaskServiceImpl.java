package com.saumik.taskmanagement.service.impl;

import com.saumik.taskmanagement.dto.TaskDTO;
import com.saumik.taskmanagement.entity.Task;
import com.saumik.taskmanagement.exception.TaskNotFoundException;
import com.saumik.taskmanagement.mapper.TaskMapper;
import com.saumik.taskmanagement.repository.TaskRepository;
import com.saumik.taskmanagement.service.TaskService;
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
    public TaskDTO addTask(TaskDTO taskDTO) {
        Task newTask = TaskMapper.toEntity(taskDTO);
        newTask = taskRepository.save(newTask);
        return TaskMapper.toDTO(newTask);
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        // Update only fields from DTO
        existingTask.setTitle(taskDTO.getTitle());
        existingTask.setDescription(taskDTO.getDescription());
        existingTask.setStatus(taskDTO.getStatus());

        Task updatedTask = taskRepository.save(existingTask);
        return TaskMapper.toDTO(updatedTask);
    }

    @Override
    public TaskDTO deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
        taskRepository.delete(task);
        return TaskMapper.toDTO(task);
    }

    @Override
    public TaskDTO getTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
        return TaskMapper.toDTO(task);
    }

    @Override
    public List<TaskDTO> getTasks() {
        return taskRepository.findAll()
                .stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }
}
