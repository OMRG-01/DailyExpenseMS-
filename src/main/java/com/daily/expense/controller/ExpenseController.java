package com.daily.expense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.daily.expense.model.*;
import com.daily.expense.service.*;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    
    @PostMapping("/expense/add")
    public String addExpense(@RequestParam Long userId,
                             @RequestParam Long categoryId,
                             @RequestParam Double amount,
                             @RequestParam String note) {
        User user = userService.getUserById(userId);
        Category category = categoryService.getCategoryById(categoryId);

        Expense expense = new Expense(user, amount, category, note);
        expenseService.saveExpense(expense);
        return "redirect:/user/userDash" ;
    }
}
