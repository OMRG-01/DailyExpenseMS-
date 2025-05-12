package com.daily.expense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.daily.expense.model.Friend;
import com.daily.expense.model.User;
import com.daily.expense.repository.FriendRepository;
import com.daily.expense.repository.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    // 1) Go on addviewFriend.html
    @GetMapping("/{userId}")
    public String viewFriends(@PathVariable Long userId, Model model) {
        List<Friend> friends = friendRepository.findByUserId(userId);
        model.addAttribute("friends", friends);
        model.addAttribute("userId", userId);
        return "addviewFriend";
    }

    // 2) Add friend form submission
    @PostMapping("/add")
    public String addFriend(@RequestParam Long userId,
                            @RequestParam String name,
                            @RequestParam String phone,
                            @RequestParam String email,
                            @RequestParam String note,
                            Model model) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Friend friend = new Friend();
            friend.setUser(user);
            friend.setName(name);
            friend.setPhone(phone);
            friend.setEmail(email);
            friend.setNote(note);
            friendRepository.save(friend);
            model.addAttribute("message", "Friend added successfully");
        } else {
            model.addAttribute("error", "User not found");
        }
        List<Friend> friends = friendRepository.findByUserId(userId);
        model.addAttribute("friends", friends);
        model.addAttribute("userId", userId);
        return "addviewFriend";
    }

    // 3) Delete friend
    @GetMapping("/delete/{id}/{userId}")
    public String deleteFriend(@PathVariable Long id, @PathVariable Long userId, Model model) {
        friendRepository.deleteById(id);
        model.addAttribute("message", "Friend deleted successfully");
        List<Friend> friends = friendRepository.findByUserId(userId);
        model.addAttribute("friends", friends);
        model.addAttribute("userId", userId);
        return "addviewFriend";
    }

    // 4) Edit friend form submission (via popup on the addviewFriend.html )
    @PostMapping("/update")
    public String updateFriend(@RequestParam Long friendId,
                               @RequestParam Long userId,
                               @RequestParam String name,
                               @RequestParam String phone,
                               @RequestParam String email,
                               @RequestParam String note,
                               Model model) {
        Friend friend = friendRepository.findById(friendId).orElse(null);
        if (friend != null) {
            friend.setName(name);
            friend.setPhone(phone);
            friend.setEmail(email);
            friend.setNote(note);
            friendRepository.save(friend);
            model.addAttribute("message", "Friend updated successfully");
        } else {
            model.addAttribute("error", "Friend not found");
        }
        List<Friend> friends = friendRepository.findByUserId(userId);
        model.addAttribute("friends", friends);
        model.addAttribute("userId", userId);
        return "addviewFriend";
    }
}