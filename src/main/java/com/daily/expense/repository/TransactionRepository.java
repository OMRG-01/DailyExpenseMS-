package com.daily.expense.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.daily.expense.model.Transaction;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	@Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.id = :userId AND t.type = 'SEND'")
	Double getTotalSend(@Param("userId") Long userId);

	@Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.id = :userId AND t.type = 'RECEIVE'")
	Double getTotalReceive(@Param("userId") Long userId);

	@Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.id = :userId AND t.type = 'LOAN'")
	Double getTotalLoan(@Param("userId") Long userId);

	@Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.id = :userId AND t.type = 'LOANPAID'")
	Double getTotalLoanPaid(@Param("userId") Long userId);
	
	 List<Transaction> findByUserId(Long userId);

}
