package com.example.expensetracker.dto;

import com.example.expensetracker.model.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private String description;

    @Min(1)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Category category;

    @NotNull
    @Min(1)
    private Long userId;
}
