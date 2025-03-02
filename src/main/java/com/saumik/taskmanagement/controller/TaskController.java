package com.saumik.taskmanagement.controller;

import com.saumik.taskmanagement.validation.TaskParameterValidator;
import jakarta.validation.Valid;
import com.saumik.taskmanagement.dto.TaskDTO;
import com.saumik.taskmanagement.dto.ApiResponse;
import com.saumik.taskmanagement.service.TaskService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TaskDTO>> addTask(@Valid @RequestBody TaskDTO taskDTO) {
        ApiResponse<TaskDTO> response = taskService.addTask(taskDTO);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDTO>> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDTO taskDTO) {
        ApiResponse<TaskDTO> response = taskService.updateTask(id, taskDTO);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDTO>> deleteTask(@PathVariable Long id) {
        ApiResponse<TaskDTO> response = taskService.deleteTask(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDTO>> getTaskById(@PathVariable Long id) {
        ApiResponse<TaskDTO> response = taskService.getTask(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskDTO>>> getAllTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        System.out.println("Status : " + status + " title : " + title + "sortBy : " + sortBy + " sortDir : " + sortDir + " page " + page + " size : " + size);

        TaskParameterValidator.validatePaginationParams(page, size, sortDir, sortBy);
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDir.equalsIgnoreCase("asc") ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy)));
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        ApiResponse<List<TaskDTO>> apiResponse = taskService.getTasks(status, title, pageable);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
