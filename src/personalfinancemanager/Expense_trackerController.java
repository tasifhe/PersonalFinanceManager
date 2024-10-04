/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package personalfinancemanager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tasif
 */
public class Expense_trackerController implements Initializable {

    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private TextField amountField;
    @FXML
    private TextField descriptionField;
    @FXML
    private Button addExpenseButton;
    @FXML
    private TableView<Expense> expenseTable;
    @FXML
    private TableColumn<Expense, LocalDate> dateColumn;
    @FXML
    private TableColumn<Expense, String> categoryColumn;
    @FXML
    private TableColumn<Expense, Double> amountColumn;
    @FXML
    private TableColumn<Expense, String> descriptionColumn;
    @FXML
    private TableColumn<Expense, String> actionColumn;
    
    // Observable list to hold the expenses
    private ObservableList<Expense> expenseList;
    @FXML
    private Button backButton;
    @FXML
    private VBox expenseTrackerController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the ComboBox with categories
        categoryComboBox.setItems(FXCollections.observableArrayList("Food", "Transport", "Utilities", "Entertainment", "Others"));
        
        // Initialize the TableView columns
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        // Initialize the list to hold expenses
        expenseList = FXCollections.observableArrayList();
        expenseTable.setItems(expenseList);
        
        // Add button action handlers in the actionColumn (like delete)
        addActionColumn();
    }    
    
    //Adds a new expense entry to the TableView when the Add button is clicked.
    @FXML
    private void handleAddExpense() {
        LocalDate date = datePicker.getValue();
        String category = categoryComboBox.getValue();
        String description = descriptionField.getText();

        double amount;
        try {
            amount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid Amount", "Please enter a valid number for the amount.");
            return;
        }

        if (date == null || category == null || description.isEmpty()) {
            showAlert("Incomplete Fields", "Please fill in all fields before adding the expense.");
            return;
        }

        // Create a new expense object and add it to the list
        Expense expense = new Expense(date, category, amount, description);
        expenseList.add(expense);

        // Clear the input fields after adding
        datePicker.setValue(null);
        categoryComboBox.setValue(null);
        amountField.clear();
        descriptionField.clear();
    }
    
    // Adds a delete button to each row in the action column.
    private void addActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white;");
                deleteButton.setOnAction(event -> {
                    Expense expense = getTableView().getItems().get(getIndex());
                    expenseList.remove(expense);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    // Shows an alert dialog with the given title and message.
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public double calculateTotalExpenses()
    {
        double total = 0.0;
        for (Expense expense : expenseList)
        {
            total += expense.getAmount();
        }
        return total;
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_dashboard.fxml"));
        Parent viewRoot = loader.load();

        Scene scene = new Scene(viewRoot);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
