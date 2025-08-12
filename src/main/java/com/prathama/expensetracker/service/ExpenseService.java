package com.prathama.expensetracker.service;

import com.prathama.expensetracker.dto.ExpenseDTO;
import com.prathama.expensetracker.model.Expense;
import com.prathama.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense addExpense(ExpenseDTO expenseDTO, Long userId) {
        Expense expense = new Expense();
        expense.setTitle(expenseDTO.getTitle());
        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expenseDTO.getCategory());
        expense.setDate(expenseDTO.getDate());
        expense.setUserId(userId);
        return expenseRepository.save(expense);
    }
}
