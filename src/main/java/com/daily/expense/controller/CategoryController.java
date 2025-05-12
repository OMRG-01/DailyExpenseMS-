package com.daily.expense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.daily.expense.model.Category;
import com.daily.expense.model.User;
import com.daily.expense.repository.CategoryRepository;
import com.daily.expense.repository.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    // 1) Go on the Category page (category.html)
    @GetMapping("/{userId}")
    public String categoryPage(@PathVariable Long userId, Model model) {
        List<Category> categories = categoryRepository.findByUserId(userId);
        model.addAttribute("categories", categories);
        model.addAttribute("userId", userId);
        return "category";
    }

    // 2) Add category form submission
    @PostMapping("/add")
    public String addCategory(@RequestParam String name, @RequestParam Long userId, Model model) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Category category = new Category();
            category.setName(name);
            category.setUser(user);
            categoryRepository.save(category);
            model.addAttribute("message", "Category added successfully");
        } else {
            model.addAttribute("error", "User not found");
        }
        List<Category> categories = categoryRepository.findByUserId(userId);
        model.addAttribute("categories", categories);
        model.addAttribute("userId", userId);
        return "category";
    }

    // 3) Delete category
    @GetMapping("/delete/{id}/{userId}")
    public String deleteCategory(@PathVariable Long id, @PathVariable Long userId, Model model) {
        categoryRepository.deleteById(id);
        model.addAttribute("message", "Category deleted successfully");
        List<Category> categories = categoryRepository.findByUserId(userId);
        model.addAttribute("categories", categories);
        model.addAttribute("userId", userId);
        return "category";
    }
}
