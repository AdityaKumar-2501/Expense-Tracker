package com.example.expensetracker.controller;

import com.example.expensetracker.dto.ExpenseDTO;
import com.example.expensetracker.model.Category;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
    ExpenseService expenseService;

    @Autowired
    ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Expense>> getAllExpenses(){
        List<Expense> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody ExpenseDTO expenseDTO){
        Expense expense = expenseService.addExpense(expenseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(expense);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<Expense>> getExpensesByUserId(@PathVariable Long userId){
        List<Expense> expenses = expenseService.getAllExpenseByUserId(userId);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/expenseId/{expenseId}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long expenseId){
        Expense expense = expenseService.getExpenseById(expenseId);
        return ResponseEntity.ok(expense);
    }

    @PutMapping("/expenseId/{expenseId}")
    public ResponseEntity<Expense> updateExpenseById(@PathVariable Long expenseId,@Valid @RequestBody ExpenseDTO expenseDTO){
        Expense expense = expenseService.updateExpenseById(expenseId, expenseDTO);
        return ResponseEntity.ok(expense);
    }

    @DeleteMapping("/expenseId/{expenseId}")
    public ResponseEntity<String> deleteExpenseById(@PathVariable Long expenseId){
        expenseService.deleteExpenseById(expenseId);
        return ResponseEntity.ok("Expense deleted successfully");
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Expense>> getExpenseByCategory(@PathVariable Category category){
        List<Expense> expenses = expenseService.getExpenseByCategory(category);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/month/{month}/year/{year}")
    public ResponseEntity<List<Expense>> getExpenseOfMonth(@PathVariable Integer month, @PathVariable Integer year){
        List<Expense> expenses = expenseService.getMonthlyExpense(month,year);
        return ResponseEntity.ok(expenses);
    }
}
