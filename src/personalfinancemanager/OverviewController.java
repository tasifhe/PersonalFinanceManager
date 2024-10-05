package personalfinancemanager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class OverviewController implements Initializable {

    @FXML private Label labelBalance;
    @FXML private Label labelExpenses;
    @FXML private Label labelSavings;

    private ObservableList<Expense> expenseList;
    private ExpenseTrackerController expenseTrackerController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        expenseList = FXCollections.observableArrayList();
    }

    /**
     * Updates the overview labels with total balance, expenses, and savings.
     */
    public void updateOverview(double totalBalance, double totalExpenses, double totalSavings) {
        labelBalance.setText(String.format("Total Balance: $%.2f", totalBalance));
        labelExpenses.setText(String.format("Total Expenses: $%.2f", totalExpenses));
        labelSavings.setText(String.format("Total Savings: $%.2f", totalSavings));
    }

    /**
     * Calculates the total expenses from the expense list.
     */
    public double calculateTotalExpenses() {
        return expenseList.stream().mapToDouble(Expense::getAmount).sum();
    }

    /**
     * Sets the reference to the ExpenseTrackerController for interaction.
     */
    public void setExpenseTrackerController(ExpenseTrackerController controller) {
        this.expenseTrackerController = controller;
    }

    /**
     * Updates the total expenses label if the ExpenseTrackerController is available.
     */
    public void updateExpenses() {
        if (expenseTrackerController != null) {
            double totalExpenses = expenseTrackerController.calculateTotalExpenses();
            labelExpenses.setText(String.format("Total Expenses: $%.2f", totalExpenses));
        }
    }

    /**
     * Handles the back button action to navigate back to the main dashboard.
     */
    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
        loadView("main_dashboard.fxml", event);
    }

    /**
     * Helper method to load the view and switch scenes.
     */
    private void loadView(String fxmlFile, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
