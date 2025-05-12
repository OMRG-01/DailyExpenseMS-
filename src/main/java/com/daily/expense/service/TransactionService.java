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

}
