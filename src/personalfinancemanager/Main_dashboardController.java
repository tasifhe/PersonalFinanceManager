/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package personalfinancemanager;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author tasif
 */
public class Main_dashboardController implements Initializable {

    @FXML
    private Button logoutButton;
    @FXML
    private Button dashboardButton;
    @FXML
    private Button expenseTrackerButton;
    @FXML
    private Button savingsGoalsButton;
    @FXML
    private Button budgetSummaryButton;
    @FXML
    private TableView<?> expenseTable;
    @FXML
    private TextField expenseAmount;
    @FXML
    private TextField expenseCategory;
    @FXML
    private TextField expenseNotes;
    @FXML
    private Button addExpenseButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleLogout(ActionEvent event) {
    }

    @FXML
    private void showDashboard(ActionEvent event) {
    }

    @FXML
    private void showExpenseTracker(ActionEvent event) {
    }

    @FXML
    private void showSavingsGoals(ActionEvent event) {
    }

    @FXML
    private void showBudgetSummary(ActionEvent event) {
    }

    @FXML
    private void addExpense(ActionEvent event) {
    }
    
}
