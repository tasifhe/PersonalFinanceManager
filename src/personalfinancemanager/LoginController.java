/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package personalfinancemanager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author tasif
 */
public class LoginController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Hyperlink signupLink;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Dummy authentication (replace with real authentication logic)
        if (username.equals("user") && password.equals("pass")) {
            // If login successful, load the main dashboard
            try {
                Parent dashboardRoot = FXMLLoader.load(getClass().getResource("main_dashboard.fxml"));
                Scene dashboardScene = new Scene(dashboardRoot);
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(dashboardScene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            // Show alert if login fails
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void handleSignupLink(ActionEvent event) {
        // Load signup page when user clicks on the signup link
        try {
            Parent signupRoot = FXMLLoader.load(getClass().getResource("signup.fxml"));
            Scene signupScene = new Scene(signupRoot);
            Stage stage = (Stage) signupLink.getScene().getWindow();
            stage.setScene(signupScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
