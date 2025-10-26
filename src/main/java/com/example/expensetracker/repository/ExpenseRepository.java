package com.example.expensetracker.repository;

import com.example.expensetracker.model.Category;
import com.example.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser_Id(Long userId);
    List<Expense> findByCategory(Category category);
    @Query("""
            Select e FROM Expense e Where createdAt Between :start and :end
            """)
    List<Expense> findByDateBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
