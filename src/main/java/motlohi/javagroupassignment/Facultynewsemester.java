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

public class Facultynewsemester {

    @FXML
    private Button btnsemester;

    @FXML
    private Label lblnewsemeter1;

    @FXML
    private Label lblsemesterID;

    @FXML
    private Label lblyearID;

    @FXML
    private TextField txtnewsemester;

    @FXML
    private TextField txtsemesterID;

    @FXML
    private TextField txtyearID;

    @FXML
    void newsemester(ActionEvent event)

    {
        // Get user input

        String semester_id = txtsemesterID.getText();
        String semester_name = txtnewsemester.getText();
        String academic_year_id = txtyearID.getText();

        // Validate fields
        if (semester_id.isEmpty() || semester_name.isEmpty() || academic_year_id.isEmpty() ) {
            showAlert("Error", "All fields are required!");
            return;
        }

        // Insert user into database using the DatabaseConnector
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO semester (semester_id,semester_name, academic_year_id) VALUES (?, ?, ?)")) {

            // Set parameters
            preparedStatement.setString(1,semester_id );
            preparedStatement.setString(2, semester_name);
            preparedStatement.setString(3, academic_year_id);


            // Execute insert
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "new semester added successfully!");

                // Close the Adduser window
                Stage stage = (Stage) btnsemester.getScene().getWindow();
                stage.close();

                // Optionally, navigate to the dashboard
                // DashBoard.show(); // Implement a show() method if needed

            } else {
                showAlert("Error", "Failed to add semester.");
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
    void backBtn(ActionEvent event) throws IOException
    {
        // Close the current (login) stage
        Stage currentStage = (Stage) txtyearID.getScene().getWindow();
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
