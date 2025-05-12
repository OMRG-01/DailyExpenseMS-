package com.daily.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daily.expense.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserId(Long userId);
}
