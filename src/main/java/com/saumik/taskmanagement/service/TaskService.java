package com.saumik.taskmanagement.service;

import com.saumik.taskmanagement.dto.TaskDTO;
import java.util.List;

public interface TaskService {

    TaskDTO addTask(TaskDTO task);
    TaskDTO updateTask(Long id,TaskDTO task);
    TaskDTO deleteTask(Long id);
    TaskDTO getTask(Long id);
    List<TaskDTO> getTasks();
}
