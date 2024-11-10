package com.example.demo.modal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String img;
    private String nutritionType;
    private String noOfRecipes;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "food_id")
    private List<Recipe> details;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNutritionType() {
        return nutritionType;
    }

    public void setNutritionType(String nutritionType) {
        this.nutritionType = nutritionType;
    }

    public String getNoOfRecipes() {
        return noOfRecipes;
    }

    public void setNoOfRecipes(String noOfRecipes) {
        this.noOfRecipes = noOfRecipes;
    }

    public List<Recipe> getDetails() {
        return details;
    }

    public void setDetails(List<Recipe> details) {
        this.details = details;
    }

    // Getters and setters
}
