package com.prathama.expensetracker.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryExpenseDTO {
    private String category;
    private Double totalAmount;
}
