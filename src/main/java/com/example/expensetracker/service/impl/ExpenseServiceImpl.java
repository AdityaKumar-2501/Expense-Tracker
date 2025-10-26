package com.example.expensetracker.service.impl;

import com.example.expensetracker.dto.ExpenseDTO;
import com.example.expensetracker.exception.InvalidRequestException;
import com.example.expensetracker.exception.NoDataException;
import com.example.expensetracker.exception.NotFoundException;
import com.example.expensetracker.model.Category;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.model.User;
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.repository.UserRepository;
import com.example.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    UserRepository userRepository;
    ExpenseRepository expenseRepository;

    @Autowired
    ExpenseServiceImpl(UserRepository userRepository, ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }


    @Override
    public List<Expense> getAllExpenses() throws NoDataException {
        List<Expense> expenses = expenseRepository.findAll();
        if(expenses.isEmpty()) throw new NoDataException("No Expense Present");
        return expenses;
    }

    @Override
    public Expense addExpense(ExpenseDTO expense) throws InvalidRequestException {
        if (expense.getAmount() < 1) {
            throw new InvalidRequestException("Amount cannot be less than or equal to 0");
        }
        Expense newExpense = convertFromDtoToEntity(expense);
        return expenseRepository.save(newExpense);
    }

    @Override
    public List<Expense> getAllExpenseByUserId(Long userId) throws NoDataException {
        List<Expense> expenses = expenseRepository.findByUser_Id(userId);
        if (expenses.isEmpty()) throw new NoDataException("No Expense for user with id " + userId);
        return expenses;
    }

    @Override
    public Expense getExpenseById(Long id) throws NotFoundException {
        return expenseRepository.findById(id).orElseThrow(() -> new NotFoundException("Expense not found with id " + id));
    }

    @Override
    public Expense updateExpenseById(Long id, ExpenseDTO expenseDTO) throws NotFoundException {
        Expense existing = expenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Expense not found with id " + id));

        // All fields from DTO replace existing ones
        existing.setAmount(expenseDTO.getAmount());
        existing.setCategory(expenseDTO.getCategory());
        existing.setDescription(expenseDTO.getDescription());

        // Update user if needed
        User user = userRepository.findById(expenseDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with id " + expenseDTO.getUserId()));
        existing.setUser(user);

        return expenseRepository.save(existing);
    }

    @Override
    public void deleteExpenseById(Long id) throws NotFoundException {
        expenseRepository.findById(id).orElseThrow(() -> new NotFoundException("Expense not found with id " + id));
        expenseRepository.deleteById(id);
    }

    @Override
    public List<Expense> getExpenseByCategory(Category category) throws NoDataException {
        List<Expense> expenses = expenseRepository.findByCategory(category);
        if (expenses.isEmpty()) throw new NoDataException("No Expense in category " + category);
        return expenses;
    }

    @Override
    public List<Expense> getMonthlyExpense(int month, int year) throws InvalidRequestException, NoDataException {
        if (month > 12 || month < 1) {
            throw new InvalidRequestException("Month should be between 1 to 12");
        }
        if (year < 0) throw new InvalidRequestException("Year cannot be negative");

        LocalDateTime start = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime end = start.plusMonths(1).minusDays(1);

        List<Expense> expenses = expenseRepository.findByDateBetween(start, end);
        if (expenses.isEmpty()) throw new NoDataException("No Expense in month " + month + " of year " + year);
        return expenses;
    }

    // ****************** HELPER FUNCTION *****************
    private Expense convertFromDtoToEntity(ExpenseDTO expenseDTO) throws NotFoundException {
        Expense expense = new Expense();
        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expenseDTO.getCategory());
        expense.setDescription(expenseDTO.getDescription());
        User user = userRepository.findById(expenseDTO.getUserId()).orElseThrow(() -> new NotFoundException("User not found with id " + expenseDTO.getUserId()));
        expense.setUser(user);
        return expense;
    }
}
