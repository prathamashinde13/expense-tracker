package com.prathama.expensetracker.controller;

import com.prathama.expensetracker.config.JwtUtil;
import com.prathama.expensetracker.dto.ExpenseDTO;
import com.prathama.expensetracker.model.Expense;
import com.prathama.expensetracker.service.CustomUserDetailsService;
import com.prathama.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add")
    public ResponseEntity<Expense> addExpense(@RequestBody ExpenseDTO expenseDTO) {
        Long userId = getAuthenticatedUserId();
        Expense savedExpense = expenseService.addExpense(expenseDTO, userId);
        return ResponseEntity.ok(savedExpense);
    }

    private Long getAuthenticatedUserId() {
        CustomUserDetailsService userDetailsService = (CustomUserDetailsService) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return userDetailsService.getId();
    }
}
