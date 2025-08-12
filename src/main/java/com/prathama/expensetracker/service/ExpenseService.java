package com.prathama.expensetracker.service;

import com.prathama.expensetracker.dto.CategoryExpenseDTO;
import com.prathama.expensetracker.dto.ExpenseDTO;
import com.prathama.expensetracker.dto.ExpenseViewDTO;
import com.prathama.expensetracker.model.Expense;
import com.prathama.expensetracker.repository.ExpenseRepository;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense addExpense(ExpenseDTO expenseDTO, Long userId) {
        Expense expense = new Expense();
        expense.setTitle(expenseDTO.getTitle());
        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expenseDTO.getCategory());
        expense.setDate(LocalDate.now());
        expense.setUserId(userId);
        expense.setComments(expenseDTO.getComments());
        return expenseRepository.save(expense);
    }

    public List<ExpenseViewDTO> getExpensesForUser(Long userId) {
        List<Expense> expenses = expenseRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return expenses.stream()
                .map(expense -> new ExpenseViewDTO(
                        expense.getId(),
                        expense.getTitle(),
                        expense.getCategory(),
                        expense.getAmount(),
                        expense.getCreatedAt(),
                        expense.getUpdatedAt(),
                        expense.getComments()
                ))
                .toList();
    }

    public Expense editExpense(ExpenseDTO expenseDTO, Long userId) {
        Expense expense = expenseRepository.findByIdAndUserId(expenseDTO.getId(), userId)
                .orElseThrow(() -> new RuntimeException("Expense not found or unauthorized"));

        if (expenseDTO.getTitle() != null) expense.setTitle(expenseDTO.getTitle());
        if (expenseDTO.getComments() != null) expense.setComments(expenseDTO.getComments());
        if (expenseDTO.getAmount() != null) expense.setAmount(expenseDTO.getAmount());
        if (expenseDTO.getCategory() != null) expense.setCategory(expenseDTO.getCategory());

        expense.setUpdatedAt(LocalDateTime.now());

        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long expenseId, Long userId) {
        Expense expense = expenseRepository.findByIdAndUserId(expenseId, userId)
                .orElseThrow(() -> new RuntimeException("Expense not found or unauthorized"));
        expenseRepository.delete(expense);
    }

    public byte[] generateChartImage(Long userId) throws IOException {
        List<CategoryExpenseDTO> data = expenseRepository.getCategoryWiseExpense(userId);

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (CategoryExpenseDTO dto : data) {
            dataset.setValue(dto.getCategory(), dto.getTotalAmount());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Category Wise Expense",
                dataset,
                true, // include legend
                true, // tooltips
                false // URLs
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: Rs.{1}"));

        BufferedImage image = chart.createBufferedImage(600, 400);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
