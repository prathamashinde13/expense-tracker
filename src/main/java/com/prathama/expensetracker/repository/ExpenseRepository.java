package com.prathama.expensetracker.repository;

import com.prathama.expensetracker.dto.CategoryExpenseDTO;
import com.prathama.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<Expense> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT new com.prathama.expensetracker.dto.CategoryExpenseDTO(e.category, SUM(e.amount)) " +
           "FROM Expense e WHERE e.userId = :userId GROUP BY e.category")
    List<CategoryExpenseDTO> getCategoryWiseExpense(@Param("userId") Long userId);
}
