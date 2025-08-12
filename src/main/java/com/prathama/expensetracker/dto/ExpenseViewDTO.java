package com.prathama.expensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseViewDTO {
    private Long id;
    private String title;
    private String category;
    private Double amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String comments;
}
