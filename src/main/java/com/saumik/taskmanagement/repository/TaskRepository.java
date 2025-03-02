package com.saumik.taskmanagement.repository;

import com.saumik.taskmanagement.entity.Task;
import com.saumik.taskmanagement.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Task findByTitleIgnoreCase(String title);

    Page<Task> findByStatus(TaskStatus status, Pageable pageable);

    Page<Task> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Task> findByStatusAndTitleContainingIgnoreCase(TaskStatus status, String title, Pageable pageable);
}
