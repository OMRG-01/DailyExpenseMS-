package com.daily.expense.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daily.expense.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private ExpenseService expenseService;
	public Map<String, Double> getUserTransactionSummary(Long userId) {
	    Double totalSend = transactionRepository.getTotalSend(userId);
	    Double totalReceive = transactionRepository.getTotalReceive(userId);
	    Double totalLoan = transactionRepository.getTotalLoan(userId);
	    Double totalLoanPaid = transactionRepository.getTotalLoanPaid(userId);

	    Map<String, Double> data = new HashMap<>();
	    data.put("totalSend", totalSend != null ? totalSend : 0.0);
	    data.put("totalReceive", totalReceive != null ? totalReceive : 0.0);
	    data.put("totalSendBalance", data.get("totalSend") - data.get("totalReceive"));

	    data.put("totalLoan", totalLoan != null ? totalLoan : 0.0);
	    data.put("totalLoanPaid", totalLoanPaid != null ? totalLoanPaid : 0.0);
	    data.put("loanBalance", data.get("totalLoan") - data.get("totalLoanPaid"));

	    return data;
	}
	
	@Autowired
    private WalletService walletService;

	public Double getFinalWalletAmount(Long userId) {
	    Double topUp = walletService.getTotalAmount(userId); // From Wallet table
	    Double receive = transactionRepository.getTotalReceive(userId);
	    Double loan = transactionRepository.getTotalLoan(userId);
	    Double send = transactionRepository.getTotalSend(userId);
	    Double loanPaid = transactionRepository.getTotalLoanPaid(userId);
	    Double expenseAmount = expenseService.getTotalExpenseAmount(userId);

	    // Handle nulls
	    topUp = topUp != null ? topUp : 0.0;
	    receive = receive != null ? receive : 0.0;
	    loan = loan != null ? loan : 0.0;
	    send = send != null ? send : 0.0;
	    loanPaid = loanPaid != null ? loanPaid : 0.0;
	    expenseAmount = expenseAmount != null ? expenseAmount : 0.0;

	    return topUp + receive + loan - send - loanPaid - expenseAmount;
	}


}
