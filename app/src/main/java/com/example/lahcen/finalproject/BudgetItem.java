package com.example.lahcen.finalproject;

public class BudgetItem {
    private String name;
    private double amount;
    private double amountSpent;
    private String id;


    public BudgetItem(String name, double amount, double amountSpent, String id) {
        this.name = name;
        this.amount = amount;
        this.amountSpent = amountSpent;
        this.id = id;
    }

    public BudgetItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(double amountSpent) {
        this.amountSpent = amountSpent;
    }
}

