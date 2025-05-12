package com.daily.expense.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.daily.expense.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
	List<Wallet> findByUserIdAndStatus(Long userId, String status);

	@Query("SELECT SUM(w.amount) FROM Wallet w WHERE w.user.id = :userId AND w.status = 'ACTIVE'")
	Double getTotalAmountByUserId(@Param("userId") Long userId);
    
    @Modifying
    @Query("UPDATE Wallet w SET w.status = 'DELETE' WHERE w.id = :walletId")
    void softDeleteById(@Param("walletId") Long walletId);

}
