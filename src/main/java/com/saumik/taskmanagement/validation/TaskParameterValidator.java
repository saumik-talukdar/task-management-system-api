package com.saumik.taskmanagement.validation;

import com.saumik.taskmanagement.exception.InvalidTaskParameterException;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

@Component
public class TaskParameterValidator {
    // List of valid fields for sorting
    private static final List<String> ALLOWED_SORT_FIELDS = Arrays.asList("id", "title", "status", "createdAt");

    // Validate page, size, and sortBy parameters
    public static void validatePaginationParams(int page, int size, String sortDir, String sortBy) {
        if (page < 0) {
            throw new InvalidTaskParameterException("Page index cannot be negative.");
        }
        if (size <= 0) {
            throw new InvalidTaskParameterException("Page size must be greater than zero.");
        }
        if (sortDir!=null && !(sortDir.equalsIgnoreCase("asc") || sortDir.equalsIgnoreCase("desc"))) {
            throw new InvalidTaskParameterException("Invalid sort direction. Allowed values are 'asc' or 'desc'.");
        }
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new InvalidTaskParameterException("Invalid sortBy field. Allowed values are: " + ALLOWED_SORT_FIELDS);
        }
    }
}
