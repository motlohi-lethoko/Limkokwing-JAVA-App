package motlohi.javagroupassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoard {

    @FXML
    private Button btnFacultyadmin;

    @FXML
    private Button btnLecturer;

    @FXML
    private Button btnPrincipallecturer;

    @FXML
    private Label lblmessage;

    @FXML
    void Facultyadmin(ActionEvent event) throws IOException {
        // Close the current (login) stage
        Stage currentStage = (Stage) btnFacultyadmin.getScene().getWindow();
        currentStage.close();

        // Load the dashboard scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Create a new stage for the dashboard and show it
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Login");
        dashboardStage.show();

    }

    @FXML
    void Lecturer(ActionEvent event) throws IOException {
        // Close the current (login) stage
        Stage currentStage = (Stage) btnLecturer.getScene().getWindow();
        currentStage.close();

        // Load the dashboard scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Lecturer.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Create a new stage for the dashboard and show it
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Principal lecture");
        dashboardStage.show();
    }

    @FXML
    void Principallecturer(ActionEvent event) throws IOException {
        // Close the current (login) stage
        Stage currentStage = (Stage) btnPrincipallecturer.getScene().getWindow();
        currentStage.close();

        // Load the dashboard scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("prldashboard.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Create a new stage for the dashboard and show it
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Principal lecture");
        dashboardStage.show();
    }

}
