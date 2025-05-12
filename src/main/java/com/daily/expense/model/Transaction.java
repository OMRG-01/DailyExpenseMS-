package com.daily.expense.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {

    public enum Type {
        SEND, RECEIVE, LOAN , LOANPAID
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private Friend friend;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String note;
    private LocalDate date = LocalDate.now();

    // Default Constructor
    public Transaction() {
    }

    // Parameterized Constructor (without id and default date)
    public Transaction(User user, Friend friend, Double amount, Type type, String note) {
        this.user = user;
        this.friend = friend;
        this.amount = amount;
        this.type = type;
        this.note = note;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Friend getFriend() {
        return friend;
    }

    public Double getAmount() {
        return amount;
    }

    public Type getType() {
        return type;
    }

    public String getNote() {
        return note;
    }

    public LocalDate getDate() {
        return date;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}