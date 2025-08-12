package com.prathama.expensetracker.controller;

import com.prathama.expensetracker.config.CustomerUserDetails;
import com.prathama.expensetracker.config.JwtUtil;
import com.prathama.expensetracker.dto.ExpenseDTO;
import com.prathama.expensetracker.dto.ExpenseViewDTO;
import com.prathama.expensetracker.model.Expense;
import com.prathama.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
        CustomerUserDetails userDetails = (CustomerUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return userDetails.getId();
    }

    @GetMapping("/view")
    public ResponseEntity<List<ExpenseViewDTO>> viewExpenses() {
        CustomerUserDetails userDetails = (CustomerUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication().getPrincipal();
        Long userId = userDetails.getId();
        List<ExpenseViewDTO> expenses = expenseService.getExpensesForUser(userId);
        return ResponseEntity.ok(expenses);
    }

    @PutMapping("/edit")
    public ResponseEntity<Expense> editExpense(@RequestBody ExpenseDTO expenseDTO) {
        CustomerUserDetails userDetails = (CustomerUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Long userId = userDetails.getId();
        Expense updateExpense = expenseService.editExpense(expenseDTO, userId);
        return ResponseEntity.ok(updateExpense);
    }

    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long expenseId) {
        CustomerUserDetails userDetails = (CustomerUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Long userId = userDetails.getId();
        expenseService.deleteExpense(expenseId, userId);
        return ResponseEntity.ok("Expense deleted successfully");
    }

    @GetMapping(value = "/distribution/image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getChartImage() throws IOException {
        CustomerUserDetails userDetails = (CustomerUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Long userId = userDetails.getId();
        byte[] imageBytes = expenseService.generateChartImage(userId);
        return ResponseEntity.ok(imageBytes);
    }
}
