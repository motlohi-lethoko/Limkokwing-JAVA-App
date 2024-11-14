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

public class Addmodule {

    @FXML
    private Button btnsave;

    @FXML
    private Button btnxt;

    @FXML
    private Label lblcrdts;

    @FXML
    private Label lblmodulecode;

    @FXML
    private Label lblmodulename;

    @FXML
    private TextField txtcrdts;

    @FXML
    private TextField txtmodule_id;

    @FXML
    private TextField txtmodulename;


    // Method to save module details to the database
    @FXML
    void save(ActionEvent event) {
        String module_name = txtmodulename.getText();
        String module_id = txtmodule_id.getText();
        String credits = txtcrdts.getText();

        // Validate fields
        if (module_id.isEmpty() || module_name.isEmpty() || credits.isEmpty()) {
            showAlert("Error", "All fields are required!");
            return;
        }

        // Inserting user into database using the DatabaseConnector
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO modules (module_name, module_code, credits) VALUES (?, ?, ?)")) {

            // Setting parameters
            preparedStatement.setString(1, module_name);
            preparedStatement.setString(2, module_id);
            preparedStatement.setString(3, credits);


            // Execute insert
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "User added successfully!");

                // Close the Adduser window
                Stage stage = (Stage) btnxt.getScene().getWindow();
                stage.close();

                // Optionally, navigate to the dashboard
                // DashBoard.show(); // Implement a show() method if needed

            } else {
                showAlert("Error", "Failed to add user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Could not connect to the database.");
        }
    }

    // Method to exit the application
    @FXML
    void out(ActionEvent event) throws IOException
    {

        // Close the current (login) stage
        Stage currentStage = (Stage) btnxt.getScene().getWindow();
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