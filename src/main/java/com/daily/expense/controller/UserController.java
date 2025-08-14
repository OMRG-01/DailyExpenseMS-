package com.daily.expense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.daily.expense.model.User;
import com.daily.expense.model.Wallet;
import com.daily.expense.repository.UserRepository;
import com.daily.expense.service.CategoryService;
import com.daily.expense.service.ExpenseService;
import com.daily.expense.service.TransactionService;
import com.daily.expense.service.UserService;
import com.daily.expense.service.WalletService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    
    @Autowired
    private WalletService walletService;
    
    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryService categoryService;
    // 1. Go to login page
  

    // 2. Handle login form
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = userRepository.findByEmailAndPassword(email, password);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            session.setAttribute("loggedInUser", user);
            return "redirect:/user/userDash";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid credentials");
            return "redirect:/user/login";
        }
    }

    @GetMapping("/userDash")
    public String userDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
        	List<Wallet> transactions = walletService.getActiveWalletsByUserId(user.getId());
        	 Double totalAmount = transactionService.getFinalWalletAmount(user.getId());
            Map<String, Double> summary = transactionService.getUserTransactionSummary(user.getId());
            
            
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("expenses", expenseService.getTodayExpenses(user.getId()));
            model.addAttribute("transactions", transactions);
            model.addAttribute("totalAmount", totalAmount);
            model.addAttribute("user", user);
            model.addAttribute("userId", user.getId());
            model.addAttribute("summary", summary);
            model.addAttribute("todayExp", expenseService.getTodayExpenseAmount(user.getId()));
            try {
                List<Map<String, Object>> stats = expenseService.getCategoryExpenseStats(user.getId());
                String statsJson = new ObjectMapper().writeValueAsString(stats);
                model.addAttribute("categoryExpenseStatsJson", statsJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace(); // You can log this
                model.addAttribute("categoryExpenseStatsJson", "[]"); // Fallback
            }
            
            return "userDash";
        } else {
            model.addAttribute("error", "Please login first.");
            return "redirect:/user/login";
        }
    }

    // 3. Go to registration page
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User()); // important!
        return "reg";  // loads reg.html
    }


    // 4. Handle registration form
    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Email already exists");
            return "reg";
        }
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        model.addAttribute("message", "Registration successful. Please login.");
        return "login";
    }

    // 5. Go to edit profile page with prefilled data
    @GetMapping("/edit/{id}")
    public String editProfile(@PathVariable Long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "editProfile";
        }
        model.addAttribute("error", "User not found");
        return "login";
    }

    // 6. Handle edit profile form
    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute User user, Model model) {
        Optional<User> existing = userRepository.findById(user.getId());
        if (existing.isPresent()) {
            User old = existing.get();
            old.setName(user.getName());
            old.setPhone(user.getPhone());
            old.setEmail(user.getEmail());
            old.setPassword(user.getPassword());
            userRepository.save(old);
            model.addAttribute("message", "Profile updated successfully");
            model.addAttribute("user", old);
        } else {
            model.addAttribute("error", "Update failed. Try again.");
        }
        return "editProfile";
    }
}