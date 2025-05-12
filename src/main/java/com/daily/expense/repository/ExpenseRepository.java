package com.daily.expense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.daily.expense.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId AND e.date = CURRENT_DATE")
    List<Expense> findTodayExpensesByUser(@Param("userId") Long userId);
 // Count today's expenses for a user
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user.id = :userId AND e.date = CURRENT_DATE")
    Double getTodayExpenses(@Param("userId") Long userId);
    
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user.id = :userId")
    Double getTotalExpenseAmount(@Param("userId") Long userId);
    
    @Query("SELECT e.category.name, COUNT(e), SUM(e.amount) FROM Expense e WHERE e.user.id = :userId GROUP BY e.category.name")
    List<Object[]> getCategoryExpenseStats(@Param("userId") Long userId);




}
