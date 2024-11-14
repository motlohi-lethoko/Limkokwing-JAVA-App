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

public class LectureRole {

    @FXML
    private Button btnSave;

    @FXML
    private Button btncancel;

    @FXML
    private Label lblLectureid;

    @FXML
    private Label lblRoleid;

    @FXML
    private Label lblRolename;

    @FXML
    private Label lblmessage;

    @FXML
    private TextField txtLectureid;

    @FXML
    private TextField txtRoleid;

    @FXML
    private TextField txtRolename;

    @FXML
    void Save(ActionEvent event) {
        // Get values from text fields
        String lectureId = txtLectureid.getText();
        String roleId = txtRoleid.getText();
        String rolename = txtRolename.getText();

        // Basic validation
        if (lectureId.isEmpty() || roleId.isEmpty() || rolename.isEmpty()) {
            showAlert("Error", "All fields are required!");
            return;
        }

        // Insert data into database
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO lecturer_roles (lecture_id, role_id, role_name) VALUES (?, ?, ?)")) {

            preparedStatement.setString(1, lectureId);
            preparedStatement.setString(2, roleId);
            preparedStatement.setString(3, rolename);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                // If save is successful, ask if they want to add another role
                showConfirmation("Success", "Lecture role saved successfully! Do you want to add another role?");
            } else {
                showAlert("Error", "Failed to save lecture role.");
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
                // Clear input fields to allow adding a new lecture role
                txtLectureid.clear();
                txtRoleid.clear();
                txtRolename.clear();
            } else if (response == goBack) {
                // Close current stage and open the Faculty Dashboard
                openFacultyDashboard();
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

    private void openFacultyDashboard() {
        try {
            // Close the current stage
            Stage currentStage = (Stage) btnSave.getScene().getWindow();
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
