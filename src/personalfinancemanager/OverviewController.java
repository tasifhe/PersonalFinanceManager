/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package personalfinancemanager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tasif
 */
public class OverviewController implements Initializable {

    @FXML
    private Label labelBalance;
    @FXML
    private Label labelExpenses;
    @FXML
    private Label labelSavings;

    private ObservableList<Expense> expenseList;
    
    private Expense_trackerController expenseTrackerController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        expenseList = FXCollections.observableArrayList();
    }    
    
    public void updateOverview(double totalBalance, double totalExpenses, double totalSavings)
    {
        labelBalance.setText("Total Balance: $" + String.format("%.2f", totalBalance));
        labelExpenses.setText("Total Expenses: $" + String.format("%.2f", totalExpenses));
        labelSavings.setText("Total Savings: $" + String.format("%.2f", totalSavings));
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

    void setExpenseTrackerController(Expense_trackerController controller) {
        this.expenseTrackerController = controller;
    }

    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
        // Load the main dashboard view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_dashboard.fxml"));
        Parent mainDashboardRoot = loader.load();

        Scene scene = new Scene(mainDashboardRoot);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
