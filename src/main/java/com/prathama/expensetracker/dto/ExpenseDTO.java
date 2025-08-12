package com.prathama.expensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private String title;
    private Double amount;
    private String category;
    private LocalDate date;
}
