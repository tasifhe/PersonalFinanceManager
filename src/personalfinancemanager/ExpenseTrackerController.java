package personalfinancemanager;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class ExpenseTrackerController implements Initializable {

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

    private ObservableList<Expense> expenseList = FXCollections.observableArrayList();
    private Expense currentlyEditing = null;

    private OverviewController overviewController;

    public void setOverviewController(OverviewController controller) {
        this.overviewController = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryComboBox.setItems(FXCollections.observableArrayList("Food", "Transport", "Utilities", "Entertainment", "Others"));
        initializeTableColumns();
        expenseTable.setItems(expenseList);
        addActionColumn();
        addExpenseButton.setText("Add Expense");
    }

    private void initializeTableColumns() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    @FXML
    private void handleAddOrUpdateExpense() {
        if (!validateInput()) return;

        LocalDate date = datePicker.getValue();
        String category = categoryComboBox.getValue();
        double amount = Double.parseDouble(amountField.getText());
        String description = descriptionField.getText();

        if (currentlyEditing == null) {
            // Add new expense
            expenseList.add(new Expense(date, category, amount, description));
        } else {
            // Update existing expense
            currentlyEditing.setDate(date);
            currentlyEditing.setCategory(category);
            currentlyEditing.setAmount(amount);
            currentlyEditing.setDescription(description);
            expenseTable.refresh();
            resetForm();
        }

        clearInputFields();
    }

    private boolean validateInput() {
        try {
            Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid Amount", "Please enter a valid number for the amount.");
            return false;
        }

        if (datePicker.getValue() == null || categoryComboBox.getValue() == null || descriptionField.getText().isEmpty()) {
            showAlert("Incomplete Fields", "Please fill in all fields before adding the expense.");
            return false;
        }
        return true;
    }

    private void addActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = createButton("Edit", "#3498DB", event -> populateFormForEdit(getCurrentExpense()));
            private final Button deleteButton = createButton("Delete", "#E74C3C", event -> deleteExpense(getCurrentExpense()));

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    HBox buttons = new HBox(5, editButton, deleteButton);
                    setGraphic(buttons);
                } else {
                    setGraphic(null);
                }
            }

            private Expense getCurrentExpense() {
                return getTableView().getItems().get(getIndex());
            }
        });
    }

    private Button createButton(String text, String color, javafx.event.EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(text);
        button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white;", color));
        button.setOnAction(eventHandler);
        return button;
    }

    private void populateFormForEdit(Expense expense) {
        datePicker.setValue(expense.getDate());
        categoryComboBox.setValue(expense.getCategory());
        amountField.setText(String.valueOf(expense.getAmount()));
        descriptionField.setText(expense.getDescription());
        currentlyEditing = expense;
        addExpenseButton.setText("Update Expense");
    }

    private void deleteExpense(Expense expense) {
        expenseList.remove(expense);
        if (overviewController != null) {
            overviewController.updateExpenses();
        }
    }

    private void resetForm() {
        currentlyEditing = null;
        addExpenseButton.setText("Add Expense");
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
        Parent viewRoot = FXMLLoader.load(getClass().getResource("main_dashboard.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(viewRoot));
        stage.show();
    }
}
