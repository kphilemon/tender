package com.example.tender.model;

public class FoodWrapper {
    private String id;
    private Food food;

    public FoodWrapper(String id, Food food) {
        this.id = id;
        this.food = food;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
