package com.example.expensetrackerApi.Service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.expensetrackerApi.entity.Expense;

public interface ExpenseService {
 Page<Expense> getAllExpenses(Pageable page);
 Expense getExpenseById(Long id);
 void deleteExpenseById(Long id);
 Expense addExpense(Expense expense);
 Expense updateExpense(Long id,Expense expense);
 Page<Expense> getExpenseByCategory(String category,Pageable page);
 Page<Expense> getExpenseByName(String name, Pageable page);
 Page<Expense> getExpenseByDate(Date startDate,Date endDate,Pageable page);

}
