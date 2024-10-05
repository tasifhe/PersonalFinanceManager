package personalfinancemanager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class MainDashboardController implements Initializable {

    @FXML private Button btnOverview;
    @FXML private Button btnExpenseTracker;
    @FXML private Button btnSavingsGoals;
    @FXML private Button btnLogout;
    @FXML private Label userInfoLabel;
    @FXML private TextField balanceInputField;

    private double totalBalance = 0.0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize if needed
    }

    public void setUserInfo(String fullName, String email) {
        userInfoLabel.setText(String.format("User: %s\nEmail: %s", fullName, email));
    }

    @FXML
    private void handleOverview(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("overview.fxml"));
        Parent viewRoot = loader.load();

        OverviewController overviewController = loader.getController();

        // Load the Expense Tracker view and controller
        FXMLLoader expenseLoader = new FXMLLoader(getClass().getResource("expense_tracker.fxml"));
        Parent expenseRoot = expenseLoader.load();
        ExpenseTrackerController expenseTrackerController = expenseLoader.getController();

        // Set controllers in each other for communication
        overviewController.setExpenseTrackerController(expenseTrackerController);
        expenseTrackerController.setOverviewController(overviewController);

        // Calculate and update overview
        double totalExpenses = expenseTrackerController.calculateTotalExpenses();
        double totalSavings = totalBalance - totalExpenses;
        overviewController.updateOverview(totalBalance, totalExpenses, totalSavings);

        loadScene(event, viewRoot);
    }

    @FXML
    private void handleExpenseTracker(ActionEvent event) throws IOException {
        loadView("expense_tracker.fxml", event);
    }

    @FXML
    private void handleSavingsGoals(ActionEvent event) throws IOException {
        loadView("savings_goals.fxml", event);
    }

    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        loadView("login.fxml", event);
    }

    @FXML
    private void handleSetTotalBalance(ActionEvent event) {
        try {
            totalBalance = Double.parseDouble(balanceInputField.getText());
        } catch (NumberFormatException e) {
            balanceInputField.setText("Invalid amount!");
        }
    }

    // Helper method to load views
    private void loadView(String fxmlFile, ActionEvent event) throws IOException {
        Parent viewRoot = FXMLLoader.load(getClass().getResource(fxmlFile));
        loadScene(event, viewRoot);
    }

    // Helper method to load scenes
    private void loadScene(ActionEvent event, Parent viewRoot) {
        Scene scene = new Scene(viewRoot);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
