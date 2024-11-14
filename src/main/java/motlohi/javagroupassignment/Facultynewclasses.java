package motlohi.javagroupassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Facultynewclasses {

    @FXML
    private Button btnnewclass;

    @FXML
    private Label lblacademic;

    @FXML
    private Label lblclass_id;

    @FXML
    private Label lblclass_name;

    @FXML
    private Label lblsemester_id;

    @FXML
    private TextField txtacademic_year;


    @FXML
    private Pane backPane;

    @FXML
    private TextField txtclass_id;

    @FXML
    private TextField txtclass_name;

    @FXML
    private TextField txtsemester_id;

    @FXML
    void Newclass(ActionEvent event) {

        // Get user input
        String class_id = txtclass_id.getText();
        String class_name = txtclass_name.getText();
        String semester_id = txtsemester_id.getText();
        String academic_year_id = txtacademic_year.getText();

        // Validate fields
        if (class_id.isEmpty() || class_name.isEmpty() || semester_id.isEmpty() || academic_year_id.isEmpty()) {
            showAlert("Error", "All fields are required!");
            return;
        }

        // Insert user into database using the DatabaseConnector
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO class (class_id, class_name, semester_id, academic_year_id) VALUES (?, ?, ?, ?)")) {

            // Set parameters
            preparedStatement.setString(1, class_id);
            preparedStatement.setString(2, class_name);
            preparedStatement.setString(3, semester_id);
            preparedStatement.setString(4, academic_year_id);

            // Execute insert
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "class added successfully!");

                // Close the Adduser window

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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void backAction(ActionEvent event) throws IOException
    {
        // Close the current (login) stage
        Stage currentStage = (Stage) backPane.getScene().getWindow();
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

    }


