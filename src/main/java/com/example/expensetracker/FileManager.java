package com.example.expensetracker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();

    public static List<User> readUsers() {
        try (Reader reader = new FileReader("users.json")) {
            Type listType = new TypeToken<List<User>>() {}.getType();
            List<User> users = gson.fromJson(reader, listType);
            return users != null ? users : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static void writeUsers(List<User> users) {
        try (Writer writer = new FileWriter("users.json")) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Expense> readExpenses(String username) {
        try (Reader reader = new FileReader(username + "_expenses.json")) {
            Type listType = new TypeToken<List<Expense>>() {}.getType();
            List<Expense> expenses = gson.fromJson(reader, listType);
            return expenses != null ? expenses : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static void writeExpenses(String username, List<Expense> expenses) {
        try (Writer writer = new FileWriter(username + "_expenses.json")) {
            gson.toJson(expenses, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
