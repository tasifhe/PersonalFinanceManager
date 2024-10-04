/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package personalfinancemanager;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author tasif
 */
public class Main_dashboardController implements Initializable {

    @FXML
    private Button btnOverview;
    @FXML
    private Button btnExpenseTracker;
    @FXML
    private Button btnSavingsGoals;
    @FXML
    private Button btnLogout;
    @FXML
    private Label userInfoLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
//    public void initialize() {
//        // Default title for the dashboard
//        dashboardTitle.setText("Main Dashboard");
//    }
    
    public void setUserInfo(String fullName, String email) {
        userInfoLabel.setText("User: " + fullName + "\nEmail: " + email);
    }

    @FXML
    private void handleOverview(ActionEvent event) throws IOException {
        //loadView("overview.fxml", event);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("overview.fxml"));
        Parent viewRoot = loader.load();

        OverviewController overviewController = loader.getController();

        // Load the expense tracker view and get its controller
        FXMLLoader expenseLoader = new FXMLLoader(getClass().getResource("expense_tracker.fxml"));
        Parent expenseRoot = expenseLoader.load();
        Expense_trackerController expenseTrackerController = expenseLoader.getController();

        overviewController.setExpenseTrackerController(expenseTrackerController);

        double totalBalance = 5000.00;
        double totalExpenses = expenseTrackerController.calculateTotalExpenses(); // Fixed the variable name
        double totalSavings = 3500.00;

        overviewController.updateOverview(totalBalance, totalExpenses, totalSavings);

        Scene scene = new Scene(viewRoot);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleExpenseTracker(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("expense_tracker.fxml"));
        Parent viewRoot = loader.load();

        Scene scene = new Scene(viewRoot);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleSavingsGoals(ActionEvent event) throws IOException {
        loadView("savings_goals.fxml", event);
    }

    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        Parent loginRoot = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene loginScene = new Scene(loginRoot);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(loginScene);
        stage.show();
    }

    private void loadView(String fxmlFile, ActionEvent event) throws IOException {
        Parent viewRoot = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene scene = new Scene(viewRoot);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
