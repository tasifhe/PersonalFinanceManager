package personalfinancemanager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import javafx.scene.layout.HBox;

public class Expense_trackerController implements Initializable {

    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private TextField amountField;
    @FXML private TextField descriptionField;
    @FXML private Button addExpenseButton;
    @FXML private TableView<Expense> expenseTable;
    @FXML private TableColumn<Expense, LocalDate> dateColumn;
    @FXML private TableColumn<Expense, String> categoryColumn;
    @FXML private TableColumn<Expense, Double> amountColumn;
    @FXML private TableColumn<Expense, String> descriptionColumn;
    @FXML private TableColumn<Expense, Void> actionColumn;
    @FXML private Button backButton;
    @FXML private VBox expenseTrackerController;

    private ObservableList<Expense> expenseList;
    private Expense currentlyEditing = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryComboBox.setItems(FXCollections.observableArrayList("Food", "Transport", "Utilities", "Entertainment", "Others"));
        
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        expenseList = FXCollections.observableArrayList();
        expenseTable.setItems(expenseList);
        
        addActionColumn();
        
        addExpenseButton.setText("Add Expense");
    }    
    
    @FXML
    private void handleAddOrUpdateExpense() {
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

        if (currentlyEditing == null) {
            // Add new expense
            Expense expense = new Expense(date, category, amount, description);
            expenseList.add(expense);
        } else {
            // Update existing expense
            currentlyEditing.setDate(date);
            currentlyEditing.setCategory(category);
            currentlyEditing.setAmount(amount);
            currentlyEditing.setDescription(description);
            expenseTable.refresh();
            currentlyEditing = null;
            addExpenseButton.setText("Add Expense");
        }

        clearInputFields();
    }
    
    private void addActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                editButton.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white;");
                deleteButton.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white;");
                
                editButton.setOnAction(event -> {
                    Expense expense = getTableView().getItems().get(getIndex());
                    populateFormForEdit(expense);
                });
                
                deleteButton.setOnAction(event -> {
                    Expense expense = getTableView().getItems().get(getIndex());
                    expenseList.remove(expense);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(5, editButton, deleteButton);
                    setGraphic(buttons);
                }
            }
        });
    }

    private void populateFormForEdit(Expense expense) {
        datePicker.setValue(expense.getDate());
        categoryComboBox.setValue(expense.getCategory());
        amountField.setText(String.valueOf(expense.getAmount()));
        descriptionField.setText(expense.getDescription());
        currentlyEditing = expense;
        addExpenseButton.setText("Update Expense");
    }

    private void clearInputFields() {
        datePicker.setValue(null);
        categoryComboBox.setValue(null);
        amountField.clear();
        descriptionField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public double calculateTotalExpenses() {
        return expenseList.stream().mapToDouble(Expense::getAmount).sum();
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