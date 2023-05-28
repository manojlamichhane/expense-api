package com.example.expensetrackerApi.repository;


import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.expensetrackerApi.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	Page<Expense> findByCategory(String category, Pageable page);
//	SELECT * from tbl_expense where category=?
	
	Page<Expense> findByNameContaining(String name, Pageable page);
//	SELECT * FROM tbl_expense where name LIKE '%name%'
	
	Page<Expense> findByEntryBetween(Date startDate,Date endDate,Pageable page);
//	SELECT * FROM tbl_expense where entry between 'startDate' and 'endDate'
	
	Page<Expense> findByUserId(Long userId, Pageable page);
// SELECT * FROM tbl_expenses where user_id=?	

	Optional<Expense> findByUserIdAndId(Long userId,Long id);	
// SELECT * FROM tbl_expenses where user_id=? AND id=?	
	
	Page<Expense> findByUserIdAndCategory(Long userId, String category,Pageable page);
// SELECT * FROM tbl_expenses where user_id=? AND category=?	
	
	Page<Expense> findByUserIdAndNameContaining(Long userId, String name,Pageable page);
	// SELECT * FROM tbl_expenses where user_id=? AND name like "%name%"	
	
	Page<Expense> findByUserIdAndEntryBetween(Long userId, Date startDate,Date endDate,Pageable page);
	// SELECT * FROM tbl_expenses where user_id=? AND date BETWEEN 'startDate' AND 'endDate'
}
