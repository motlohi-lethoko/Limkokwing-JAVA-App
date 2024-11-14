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
import java.sql.ResultSet;
import java.sql.SQLException;

public class Home {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnsignup;

    @FXML
    private Label lblLimkos;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblUsername;

    @FXML
    private PasswordField psdPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtUser_id;  // New TextField for user_id

    @FXML
    void Login(ActionEvent event) throws IOException {
        String enteredUsername = txtUsername.getText();
        String enteredPassword = psdPassword.getText();

        if (authenticateUser(enteredUsername, enteredPassword)) {
            showAlert("Login Successful", "Welcome, " + enteredUsername + "!");
            showDashboardChoice(); // Ask user which dashboard to open
        } else {
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    @FXML
    void signup(ActionEvent event) {
        String newUsername = txtUsername.getText();
        String newPassword = psdPassword.getText();
        int newUserId = Integer.parseInt(txtUser_id.getText());  // Capture user_id from the TextField

        if (registerUser(newUserId, newUsername, newPassword)) {
            showAlert("Sign-Up Successful", "You can now log in with your new account.");
        } else {
            showAlert("Sign-Up Failed", "This username is already taken or user_id is invalid.");
        }
    }

    private boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if a matching user is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean registerUser(int userId, String username, String password) {
        String checkUserQuery = "SELECT * FROM users WHERE username = ? OR user_id = ?";
        String insertUserQuery = "INSERT INTO users (user_id, username, password) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkUserQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertUserQuery)) {

            // Check if username or user_id already exists
            checkStmt.setString(1, username);
            checkStmt.setInt(2, userId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return false; // Username or user_id already taken
            }

            // Insert new user
            insertStmt.setInt(1, userId);
            insertStmt.setString(2, username);
            insertStmt.setString(3, password);
            insertStmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showDashboardChoice() {
        // Create an alert to prompt user for their dashboard choice
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choose Dashboard");
        alert.setHeaderText("Please choose which dashboard to open:");

        // Set the options for the user to choose
        ButtonType facultyDashboardButton = new ButtonType("Faculty Dashboard");
        ButtonType prlDashboardButton = new ButtonType("PRL Dashboard");
        ButtonType lecturerDashboardButton = new ButtonType("Lecturer Dashboard");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Add the buttons to the alert
        alert.getButtonTypes().setAll(facultyDashboardButton, prlDashboardButton, lecturerDashboardButton, cancelButton);

        // Show the alert and capture the user's choice
        alert.showAndWait().ifPresent(response -> {
            if (response == facultyDashboardButton) {
                openFacultyDashboard();
            } else if (response == prlDashboardButton) {
                openPrlDashboard();
            } else if (response == lecturerDashboardButton) {
                openLecturerDashboard();
            }
        });
    }

    private void openFacultyDashboard() {
        openDashboard("Facultydashboard.fxml", "Faculty Dashboard");
    }

    private void openPrlDashboard() {
        openDashboard("Prldashboard.fxml", "PRL Dashboard");
    }

    private void openLecturerDashboard() {
        openDashboard("LecturerDashboard.fxml", "Lecturer Dashboard");
    }

    private void openDashboard(String fxmlFile, String title) {
        try {
            // Close the current login/signup stage
            Stage currentStage = (Stage) btnLogin.getScene().getWindow();
            currentStage.close();

            // Load the appropriate dashboard scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene dashboardScene = new Scene(loader.load());

            // Create a new stage for the selected dashboard and show it
            Stage dashboardStage = new Stage();
            dashboardStage.setScene(dashboardScene);
            dashboardStage.setTitle(title);
            dashboardStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open the dashboard.");
        }
    }
}
