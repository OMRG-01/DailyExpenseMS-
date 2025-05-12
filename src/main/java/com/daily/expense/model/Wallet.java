package com.daily.expense.model;

import java.time.LocalDate;

import jakarta.persistence.*;

import jakarta.persistence.*;

@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) // Multiple wallet entries can belong to one user
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Double amount = 0.0;

    private String note;

    private String status = "ACTIVE"; // Default is ACTIVE

    private LocalDate addedAt;

    // Set addedAt automatically before persisting
    @PrePersist
    public void prePersist() {
        this.addedAt = LocalDate.now();
    }

    // Getters and Setters
    public LocalDate getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDate addedAt) {
        this.addedAt = addedAt;
    }
    // Default Constructor
    public Wallet() {
    }

    // Parameterized Constructor
    public Wallet(User user) {
        this.user = user;
    }

    // Getter for id
    public Long getId() {
        return id;
    }

    // Setter for id
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for user
    public User getUser() {
        return user;
    }

    // Setter for user
    public void setUser(User user) {
        this.user = user;
    }

    // Getter for amount
    public Double getAmount() {
        return amount;
    }

    // Setter for amount
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}