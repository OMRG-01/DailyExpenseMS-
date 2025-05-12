package com.daily.expense.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.daily.expense.model.Friend;
import com.daily.expense.model.Transaction;
import com.daily.expense.model.User;
import com.daily.expense.repository.FriendRepository;
import com.daily.expense.repository.*;
import com.daily.expense.service.*;


@Controller
public class TransactionController {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private FriendService friendService;
    
    @GetMapping("/transaction/{userId}")
    public String viewTransactions(@PathVariable Long userId, Model model) {
        List<Friend> friends = friendService.getAllByUser(userId);
        Map<String, Double> summary = transactionService.getUserTransactionSummary(userId);

        model.addAttribute("userId", userId);
        model.addAttribute("friends", friends);
        model.addAttribute("summary", summary);
        model.addAttribute("transactions", transactionRepository.findByUserId(userId));

        return "transaction";
    }


    @PostMapping("/transaction/add")
    public String addTransaction(@RequestParam Long userId,
                                 @RequestParam Long friendId,
                                 @RequestParam Double amount,
                                 @RequestParam String type,
                                 @RequestParam String note) {

        User user = userService.getUserById(userId);
        Friend friend = friendService.getById(friendId);
        Transaction.Type txnType = Transaction.Type.valueOf(type.toUpperCase());

        Transaction txn = new Transaction(user, friend, amount, txnType, note);
        transactionRepository.save(txn);

        return "redirect:/transaction?userId=" + userId;
    }


   
}