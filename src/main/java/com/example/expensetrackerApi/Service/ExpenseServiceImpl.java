package com.example.expensetrackerApi.Service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.expensetrackerApi.entity.Expense;
import com.example.expensetrackerApi.exception.ResourceNotFoundException;
import com.example.expensetrackerApi.repository.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService{

	@Autowired
	private UserService userService;
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Override
	public Page<Expense> getAllExpenses(Pageable page) {
		return expenseRepository.findByUserId(userService.getLoggedInUser().getId(), page);
	}

	@Override
	public Expense getExpenseById(Long id) {
		Optional<Expense> expense = expenseRepository.findByUserIdAndId(userService.getLoggedInUser().getId(), id);
		if(expense.isPresent()) {
			return expense.get();
		}
		throw new ResourceNotFoundException("No expense with id "+id);
	}

	@Override
	public  void deleteExpenseById(Long id) {
		Expense expense = getExpenseById(id);
		expenseRepository.delete(expense);	
	}

	@Override
	public Expense addExpense(Expense expense) {
		expense.setUser(userService.getLoggedInUser());
		return	expenseRepository.save(expense);
	}

	@Override
	public Expense updateExpense(Long id,Expense expense) {
		
		Expense existingExpense = getExpenseById(id);
		existingExpense.setName(expense.getName());
		existingExpense.setDescription(expense.getDescription());
		existingExpense.setCategory(expense.getCategory());
		existingExpense.setAmount(expense.getAmount());
		existingExpense.setEntry(expense.getEntry());
		return expenseRepository.save(existingExpense);	
		
	}

	@Override
	public Page<Expense> getExpenseByCategory(String category, Pageable page) {		
		return expenseRepository.findByUserIdAndCategory(userService.getLoggedInUser().getId(), category, page);
	}

	@Override
	public Page<Expense> getExpenseByName(String name, Pageable page) {
		return expenseRepository.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(), name, page);
	}

	@Override
	public Page<Expense> getExpenseByDate(Date startDate, Date endDate, Pageable page) {
		if(startDate == null) {
			startDate=new Date(0);
		}
		if(endDate == null) {
			endDate = new Date(System.currentTimeMillis());
		}
		return expenseRepository.findByUserIdAndEntryBetween(userService.getLoggedInUser().getId(), startDate, endDate, page);
	}

}
