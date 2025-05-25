package com.example.expensetracker;

import java.time.LocalDate;

public class Expense {
    private String description;
    private double amount;
    private LocalDate date;

    public Expense() {}

    public Expense(String description, double amount, LocalDate date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }

    public void setDescription(String description) { this.description = description; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setDate(LocalDate date) { this.date = date; }
}
