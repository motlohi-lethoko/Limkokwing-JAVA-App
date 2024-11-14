package motlohi.javagroupassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Adduser {

    @FXML
    private Button btnOk;

    @FXML
    private Button btncancel;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblRole;

    @FXML
    private Label lblUserid;

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblmessage;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtRole;

    @FXML
    private TextField txtUserid;

    @FXML
    private TextField txtUsername;

    @FXML
    void Ok(ActionEvent event) {
        // Get user input
        String userId = txtUserid.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String role = txtRole.getText();

        // Validate fields
        if (userId.isEmpty() || username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            showAlert("Error", "All fields are required!");
            return;
        }

        // Insert user into the database
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO users (user_id, username, password, role) VALUES (?, ?, ?, ?)")) {

            // Set parameters
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, role);

            // Execute insert
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showConfirmation("Success", "User added successfully! Do you want to add another user?");
            } else {
                showAlert("Error", "Failed to add user.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Could not connect to the database.");
        }
    }

    private void showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType addMore = new ButtonType("Add More");
        ButtonType goBack = new ButtonType("Go to Dashboard", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(addMore, goBack);

        alert.showAndWait().ifPresent(response -> {
            if (response == addMore) {
                // Clear fields to allow adding another user
                txtUserid.clear();
                txtUsername.clear();
                txtPassword.clear();
                txtRole.clear();
            } else if (response == goBack) {
                // Close current stage and open the dashboard
                openDashboard();
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openDashboard() {
        try {
            // Close the current stage
            Stage currentStage = (Stage) btnOk.getScene().getWindow();
            currentStage.close();

            // Load and show the Faculty Dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Facultydashboard.fxml"));
            Scene dashboardScene = new Scene(loader.load());

            Stage dashboardStage = new Stage();
            dashboardStage.setScene(dashboardScene);
            dashboardStage.setTitle("Faculty Dashboard");
            dashboardStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the Faculty Dashboard.");
        }
    }

    @FXML
    void cancel(ActionEvent event) throws IOException {

        Stage currentStage = (Stage) btncancel.getScene().getWindow();
        currentStage.close();

        // Load the dashboard scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Facultydashboard.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Create a new stage for the dashboard and show it
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Faculty dashboard");
        dashboardStage.show();
    }
}
