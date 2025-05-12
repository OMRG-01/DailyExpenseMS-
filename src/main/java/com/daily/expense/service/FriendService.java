package com.daily.expense.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daily.expense.model.Friend;
import com.daily.expense.repository.FriendRepository;

@Service
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;

    public List<Friend> getAllByUser(Long userId) {
        return friendRepository.findByUserId(userId);
    }

    public Friend getById(Long id) {
        return friendRepository.findById(id).orElse(null);
    }

    public void save(Friend friend) {
        friendRepository.save(friend);
    }

    public void delete(Long id) {
        friendRepository.deleteById(id);
    }
}