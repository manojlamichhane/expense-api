package com.example.expensetrackerApi.entity;

import java.math.BigDecimal;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tbl_expenses")
public class Expense {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(name = "expense_name")
@NotBlank(message = "Expense name must not be null")
private String name;

@NotBlank(message = "Discription must not be null")
private String description;

@Column(name = "expense_amount")
@NotNull(message = "Amount must not be null")
private BigDecimal amount;

@NotBlank(message = "Category must not be null")
private String category;

// NotBlank annotation is for string only and for integers we use NotNull

@NotNull(message = "Date must not be null")
private Date entry;

@Column(name="created_at",nullable = false,updatable = false)
@CreationTimestamp
private Timestamp createAt;

@Column(name="updated_at")
@UpdateTimestamp
private Timestamp updatedAt;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id",nullable = false)
@OnDelete(action = OnDeleteAction.CASCADE)
@JsonIgnore
private User user;

}
