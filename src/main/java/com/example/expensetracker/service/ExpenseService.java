package com.example.expensetracker.service;

import com.example.expensetracker.dto.ExpenseDTO;
import com.example.expensetracker.exception.InvalidRequestException;
import com.example.expensetracker.exception.NoDataException;
import com.example.expensetracker.exception.NotFoundException;
import com.example.expensetracker.model.Category;
import com.example.expensetracker.model.Expense;

import java.util.List;

public interface ExpenseService {
    List<Expense> getAllExpenses() throws NoDataException;
    Expense addExpense(ExpenseDTO expense) throws InvalidRequestException;
    List<Expense> getAllExpenseByUserId(Long userId) throws NoDataException;
    Expense getExpenseById(Long id) throws NotFoundException;
    Expense updateExpenseById(Long id, ExpenseDTO expense) throws NotFoundException;
    void deleteExpenseById(Long id) throws NotFoundException;
    List<Expense> getExpenseByCategory(Category category) throws NoDataException;
    List<Expense> getMonthlyExpense(int month, int year) throws InvalidRequestException, NoDataException;
}
