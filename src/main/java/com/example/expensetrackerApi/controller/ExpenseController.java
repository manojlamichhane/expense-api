package com.example.expensetrackerApi.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.expensetrackerApi.Service.ExpenseService;
import com.example.expensetrackerApi.entity.Expense;

import jakarta.validation.Valid;

@RestController

public class ExpenseController {
	
	@Autowired
	private ExpenseService expenseService;
	
	@GetMapping("/expenses")
	public Page<Expense> getAllExpenses(Pageable page) {		
		return expenseService.getAllExpenses(page);
	}
	
	@GetMapping("/expenses/category")
	public Page<Expense> getExpensesByCategory(@RequestParam(name="category") String category, Pageable page) {		
		return expenseService.getExpenseByCategory(category,page);
	}
	
	@GetMapping("/expenses/name")
	public Page<Expense> getExpensesByName(@RequestParam String name, Pageable page) {		
		return expenseService.getExpenseByName(name,page);
	}
	
	@GetMapping("/expenses/date")
	public Page<Expense> getExpensesByDate(@RequestParam(required = false) Date startDate,@RequestParam(required = false) Date endDate, Pageable page) {		
		return expenseService.getExpenseByDate(startDate,endDate,page);
	}
	
	@GetMapping("/expenses/{id}")
	public Expense getExpenseById(@PathVariable(name = "id")  Long id) {
		return expenseService.getExpenseById(id);
	}
	
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/expenses")
	public void deleteExpenseById (@RequestParam(name = "id") Long id) {
		expenseService.deleteExpenseById(id);
	}
	
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping("/expenses")
	public Expense addExpense(@Valid @RequestBody Expense expense) {
		
		return expenseService.addExpense(expense);
	}
	
	@PutMapping("/expenses/{id}")
	public Expense updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
		return expenseService.updateExpense(id, expense);		
	}
	
}
