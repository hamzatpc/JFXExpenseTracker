package com.example.expensetracker;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.*;

import java.time.LocalDate;
import java.util.List;

public class DashboardController {
    @FXML private TextField descriptionField;
    @FXML private TextField amountField;
    @FXML private ListView<String> expenseList;
    @FXML private Label totalLabel;
    @FXML private ComboBox<String> monthFilter;
    @FXML private DatePicker datePicker;



    private ExpenseService expenseService = new ExpenseService();

    @FXML
    private void onMonthFilterChanged() {
        refreshList();
    }

    @FXML
    public void initialize() {
        updateExpenseList();
        monthFilter.setValue("All"); // Başlangıçta "All" seçili olsun
        monthFilter.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> refreshList());
        refreshList();
    }

    private void updateExpenseList() {
        List<Expense> expenses = expenseService.getExpenses(MainApp.currentUsername);

        // Toplamı hesapla
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }

        // Label'a yaz
        totalLabel.setText(String.format("Total: ₺%.2f", total));
    }

    private void refreshList() {
        String selectedMonth = monthFilter != null ? monthFilter.getValue() : "All";

        var expenses = expenseService.getExpenses(MainApp.currentUsername);
        ObservableList<String> list = FXCollections.observableArrayList();
        double total = 0.0;

        for (Expense exp : expenses) {
            boolean matchesMonth = selectedMonth.equals("All") ||
                    exp.getDate().getMonth().name().equalsIgnoreCase(selectedMonth.toUpperCase());

            if (matchesMonth) {
                list.add(exp.getDate() + " - " + exp.getDescription() + ": " + exp.getAmount() + " TL");
                total += exp.getAmount();
            }
        }

        expenseList.setItems(list);
        totalLabel.setText(String.format("Total: ₺%.2f", total));
    }
    @FXML
    private void onAddExpense() {
        String desc = descriptionField.getText();
        String amountText = amountField.getText();
        LocalDate date = datePicker.getValue();  // Kullanıcının seçtiği tarih

        if (desc.isEmpty() || amountText.isEmpty() || date == null) {
            // Basit doğrulama
            System.out.println("Lütfen tüm alanları doldurun.");
            return;
        }

        double amount = Double.parseDouble(amountText);

        Expense expense = new Expense(desc, amount, date);
        expenseService.addExpense(MainApp.currentUsername, expense);

        descriptionField.clear();
        amountField.clear();
        datePicker.setValue(null); // DatePicker sıfırlansın

        refreshList();
    }

    @FXML
    private void onDeleteExpense() {
        // Seçili gideri al
        String selectedItem = expenseList.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            // Eğer bir seçim yoksa kullanıcıyı uyarabilirsin
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an expense to delete.");
            alert.showAndWait();
            return;
        }

        // Kullanıcının onayını al
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected expense?");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Expense listesini al
                List<Expense> expenses = expenseService.getExpenses(MainApp.currentUsername);

                // expenseList içindeki string formatına göre seçilen öğe ile eşleşen Expense objesini bul
                Expense toRemove = null;
                for (Expense e : expenses) {
                    String formatted = e.getDate() + " - " + e.getDescription() + ": " + e.getAmount() + " TL";
                    if (formatted.equals(selectedItem)) {
                        toRemove = e;
                        break;
                    }
                }

                if (toRemove != null) {
                    expenses.remove(toRemove);
                    // Gider listesini kaydet
                    FileManager.writeExpenses(MainApp.currentUsername, expenses);
                    // Listeyi yenile
                    refreshList();
                    // Toplamı güncelle
                    updateExpenseList();
                }
            }
        });
    }



}
