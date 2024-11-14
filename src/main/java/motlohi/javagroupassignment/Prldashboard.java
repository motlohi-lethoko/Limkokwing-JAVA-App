package motlohi.javagroupassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Prldashboard {

    @FXML
    private Button btnAddreport;

    @FXML
    private Button btnViewreport;

    @FXML
    private Button btnlogout;

    @FXML
    private Label lblmessage;

    @FXML
    void Addreport(ActionEvent event) throws IOException {
        // Close the current (login) stage
        Stage currentStage = (Stage) btnAddreport.getScene().getWindow();
        currentStage.close();

        // Load the dashboard scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Addreport.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Create a new stage for the dashboard and show it
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("reports");
        dashboardStage.show();
    }

    @FXML
    void Viewreport(ActionEvent event) throws IOException {
        // Close the current (login) stage
        Stage currentStage = (Stage) btnViewreport.getScene().getWindow();
        currentStage.close();

        // Load the dashboard scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("lecturer_reports.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Create a new stage for the dashboard and show it
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("reports");
        dashboardStage.show();
    }

    @FXML
    void logout(ActionEvent event) {
        Stage currentStage = (Stage) btnlogout.getScene().getWindow();
        currentStage.close();
    }

}
