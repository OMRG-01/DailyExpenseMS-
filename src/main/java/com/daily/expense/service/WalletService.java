package com.daily.expense.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daily.expense.model.User;
import com.daily.expense.model.Wallet;
import com.daily.expense.repository.UserRepository;
import com.daily.expense.repository.WalletRepository;

import jakarta.transaction.Transactional;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Wallet> getActiveWalletsByUserId(Long userId) {
        return walletRepository.findByUserIdAndStatus(userId, "ACTIVE");
    }

    public Double getTotalAmount(Long userId) {
        return walletRepository.getTotalAmountByUserId(userId) != null ? walletRepository.getTotalAmountByUserId(userId) : 0.0;
    }

    public void addWalletEntry(Long userId, Double amount, String note) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Wallet wallet = new Wallet();
            wallet.setUser(user);
            wallet.setAmount(amount);
            wallet.setNote(note);
            wallet.setStatus("ACTIVE");
            walletRepository.save(wallet);
        }
    }
    
    @Transactional
    public void softDeleteWalletEntry(Long walletId) {
        walletRepository.softDeleteById(walletId);
    }

}

