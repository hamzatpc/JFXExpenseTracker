package com.example.expensetracker;

import java.util.List;

public class ExpenseService {
    public void addExpense(String username, Expense expense) {
        List<Expense> expenses = FileManager.readExpenses(username);
        expenses.add(expense);
        FileManager.writeExpenses(username, expenses);
    }

    public static List<Expense> getExpenses(String username) {
        return FileManager.readExpenses(username);
    }
}
