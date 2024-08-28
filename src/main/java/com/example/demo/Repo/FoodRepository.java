package com.example.demo.Repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modal.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
