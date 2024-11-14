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

public class Addlecture {

    @FXML
    private Label lecture_id;

    @FXML
    private Button btncancel;

    @FXML
    private Button btnsave;

    @FXML
    private Label lbldepartment;

    @FXML
    private Button BackId;

    @FXML
    private Label lblemployee_number;

    @FXML
    private Label lblname;

    //@FXML
    //private Label lbluser_id;

    @FXML
    private TextField txtlecture_id;

    @FXML
    private TextField txtdepartment;

    @FXML
    private TextField txtemployee_number;

    @FXML
    private TextField txtname;

    @FXML
    private Pane AddlectureId;

    //@FXML
    //private TextField txtuser_id;

    // Method to clear fields after saving or canceling
    private void clearFields() {
        txtlecture_id.clear();
        txtdepartment.clear();
        txtemployee_number.clear();
        txtname.clear();
        //txtuser_id.clear();
    }

    // Cancel button: closes the current window and returns to the facultydashboard
    @FXML
    void cancel(ActionEvent event) throws IOException {
        Stage stage = (Stage) btncancel.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Facultydashboard.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Create a new stage for the dashboard and show it
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Faculty dashboard");
        dashboardStage.show();
    }

    // Save button: saves information to the database
    @FXML
    void save(ActionEvent event) {
        String lectureId = txtlecture_id.getText();
        String department = txtdepartment.getText();
        String employeeNumber = txtemployee_number.getText();
        String name = txtname.getText();
        //String userId = txtuser_id.getText();

        // Check if any field is empty
        if (lectureId.isEmpty() || department.isEmpty() || employeeNumber.isEmpty() || name.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }

        // Database connection and insertion
        // Fixed the SQL query by removing the trailing comma after the last column
        String sql = "INSERT INTO lecturers (lecture_id, department, employee_number, name) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, lectureId);
            pstmt.setString(2, department);
            pstmt.setString(3, employeeNumber);
            pstmt.setString(4, name);
            //pstmt.setString(5, userId); // If needed, uncomment and add this line.

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0)
            {
                Alert ensure = new Alert(Alert.AlertType.CONFIRMATION);
                ensure.setContentText("Record saved successfully!");
                ensure.showAndWait();
                clearFields(); // Clears fields after successful save
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    @FXML
    void toFaculty(ActionEvent event) throws IOException
    {
        Stage currentStage = (Stage) AddlectureId.getScene().getWindow();
        currentStage.close();

        // Load the dashboard scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Facultydashboard.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Create a new stage for the dashboard and show it
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Add lecture");
        dashboardStage.show();
    }
}
