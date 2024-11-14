package motlohi.javagroupassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStudent {

    @FXML
    private Button btnadd;

    @FXML
    private Button btnclose;

    @FXML
    private Label lblclass_id;

    @FXML
    private Label lblname;

    @FXML
    private Label lblstudent_id;

    @FXML
    private TextField txtclass_id;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtstudent_id;

    @FXML
    void add(ActionEvent event) {
        String name = txtname.getText();
        String student_id = txtstudent_id.getText();
        String class_id = txtclass_id.getText();

        // Validate fields
        if (name.isEmpty() || student_id.isEmpty() || class_id.isEmpty()) {
            showAlert("Error", "All fields are required!");
            return;
        }

        // Insert user into database
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO students (name, Student_id, class_id) VALUES (?, ?, ?)")) {

            // Setting parameters
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, student_id);
            preparedStatement.setString(3, class_id);

            // Execute insert
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "User added successfully!");

                // Close the AddStudent window
                Stage stage = (Stage) btnadd.getScene().getWindow();
                stage.close();
            } else {
                showAlert("Error", "Failed to add user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Could not connect to the database.");
        }
    }

    @FXML
    void close(ActionEvent event) throws IOException
    {
        // Close the current (login) stage
        Stage currentStage = (Stage) btnclose.getScene().getWindow();
        currentStage.close();

        // Load the dashboard scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Facultydashboard.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Create a new stage for the dashboard and show it
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Add user");
        dashboardStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
