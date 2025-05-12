package com.daily.expense.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;

import com.daily.expense.model.Wallet;
import com.daily.expense.repository.UserRepository;
import com.daily.expense.service.TransactionService;
import com.daily.expense.service.WalletService;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{userId}")
    public String viewWallet(@PathVariable Long userId, Model model) {
        List<Wallet> transactions = walletService.getActiveWalletsByUserId(userId);
        Double finalAmount = transactionService.getFinalWalletAmount(userId);
        model.addAttribute("transactions", transactions);
        model.addAttribute("totalAmount", finalAmount);
        model.addAttribute("userId", userId);
        return "wallet";
    }

    @PostMapping("/addMoneyDash")
    public String addMoneyDash(@RequestParam Long userId,
                           @RequestParam Double amount,
                           @RequestParam String note,
                           Model model) {
        walletService.addWalletEntry(userId, amount, note);
        return "redirect:/user/userDash";
    }
    
    @PostMapping("/addMoney")
    public String addMoney(@RequestParam Long userId,
                           @RequestParam Double amount,
                           @RequestParam String note,
                           Model model) {
        walletService.addWalletEntry(userId, amount, note);
        return "redirect:/wallet/" + userId;
    }
    
    @PostMapping("/delete/{walletId}")
    public String deleteWalletEntry(@PathVariable Long walletId, @RequestParam Long userId) {
        walletService.softDeleteWalletEntry(walletId);
        return "redirect:/wallet/" + userId;
    }

}

