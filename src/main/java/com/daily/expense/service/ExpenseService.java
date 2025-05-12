package com.daily.expense.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daily.expense.model.Expense;
import com.daily.expense.repository.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getTodayExpenses(Long userId) {
        return expenseRepository.findTodayExpensesByUser(userId);
    }

    public void saveExpense(Expense expense) {
        expenseRepository.save(expense);
    }
    

    public Double getTotalExpenseAmount(Long userId) {
        Double total = expenseRepository.getTotalExpenseAmount(userId);
        return total != null ? total : 0.0;
    }
    
    public Double getTodayExpenseAmount(Long userId) {
        Double total = expenseRepository.getTodayExpenses(userId);
        return total != null ? total : 0.0;
    }

    public List<Map<String, Object>> getCategoryExpenseStats(Long userId) {
        List<Object[]> rawData = expenseRepository.getCategoryExpenseStats(userId);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rawData) {
            Map<String, Object> map = new HashMap<>();
            map.put("category", row[0]);
            map.put("count", row[1]);
            map.put("total", row[2]);
            result.add(map);
        }
        return result;
    }


}
