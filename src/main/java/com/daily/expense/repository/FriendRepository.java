package com.daily.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daily.expense.model.Friend;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByUserId(Long userId);
    
}
