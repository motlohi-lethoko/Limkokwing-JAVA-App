package motlohi.javagroupassignment;

import javafx.application.Platform;
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

public class Facultydashboard {

    @FXML
    private Button btnAddclass;

    @FXML
    private Button btnAddlecture;

    @FXML
    private Button btnAddmodule;

    @FXML
    private Button btnAddsemester;

    @FXML
    private Button btnAddstudent;

    @FXML
    private Button btnAdduser;

    @FXML
    private Button btnAddyear;

    @FXML
    private Button btnLectureRole;


    @FXML
    private Button btnViewprofile;

    @FXML
    private Label lblmessage;
    @FXML
    private Button logout;

    @FXML
    void Addclass(ActionEvent event) throws IOException
    {
        Stage currentStage = (Stage) btnAdduser.getScene().getWindow();
        currentStage.close();

        // Load the dashboard scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addclass.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Create a new stage for the dashboard and show it
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Add class");
        dashboardStage.show();
    }

    @FXML
    void Addlecture(ActionEvent event) throws IOException {
        // Close the current (login) stage
        Stage currentStage = (Stage) btnAdduser.getScene().getWindow();
        currentStage.close();

        // Load the dashboard scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Addlecture.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Create a new stage for the dashboard and show it
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Add lecture");
        dashboardStage.show();
    }

    @FXML
    void Addmodule(ActionEvent event) throws IOException {
        // Close the current (login) stage
        Stage currentStage = (Stage) btnAddmodule.getScene().getWindow();
        currentStage.close();

        // Load the dashboard scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Addmodule.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Create a new stage for the dashboard and show it
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Add module");
        dashboardStage.show();
    }

    @FXML
    void Addsemester(ActionEvent event) throws  IOException
    {
        // Close the current (login) stage
        Stage currentStage = (Stage) btnAdduser.getScene().getWindow();
        currentStage.close();

        // Load the dashboard scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Facultynewsemester.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Create a new stage for the dashboard and show it
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Add user");
        dashboardStage.show();
    }

    @FXML
    void Addstudent(ActionEvent event) throws IOException {
        // Close the current (login) stage
        Stage currentStage = (Stage) btnAddstudent.getScene().getWindow();
        currentStage.close();

        // Load the dashboard scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Addstudent.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Create a new stage for the dashboard and show it
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Add student");
        dashboardStage.show();
    }

    @FXML
    void Adduser(ActionEvent event) throws IOException {
        // Close the current (login) stage
        Stage currentStage = (Stage) btnAdduser.getScene().getWindow();
        currentStage.close();

        // Load the dashboard scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Adduser.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Create a new stage for the dashboard and show it
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Add user");
        dashboardStage.show();
    }

    @FXML
    void Addyear(ActionEvent event)
    {
        Stage stage = new Stage();
        Pane yearholder = new Pane();
        yearholder.setPrefWidth(200);
        yearholder.setPrefHeight(70);
        Label yearLB = new Label("Add academic year");
        Button saveBtn = new Button("save");

        saveBtn.setLayoutY(40);
        saveBtn.setLayoutX(150);

        TextField addyears = new TextField();
        addyears.setLayoutX(20);
        addyears.setLayoutY(10);
        yearLB.setLabelFor(addyears);
        addyears.setPromptText("2024/2025");

        yearholder.getChildren().addAll(addyears,saveBtn);
        Scene scene1 = new Scene(yearholder);
        stage.setScene(scene1);
        stage.show();
        setActionBtn(saveBtn,addyears);




    }

    @FXML
    void Assignrole(ActionEvent event) throws IOException {
        // Close the current (login) stage
        Stage currentStage = (Stage) btnLectureRole.getScene().getWindow();
        currentStage.close();

        // Load the dashboard scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("lecture_role.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Create a new stage for the dashboard and show it
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("lecture role");
        dashboardStage.show();
    }


    @FXML
    void Viewprofile(ActionEvent event) throws IOException {
        // Close the current (login) stage
        Stage currentStage = (Stage) btnLectureRole.getScene().getWindow();
        currentStage.close();

        // Load the dashboard scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Viewprofile.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Create a new stage for the dashboard and show it
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Profile View");
        dashboardStage.show();
    }
    //@FXML
    //private Button logout;
    public void logout(ActionEvent event) {
        // Get the current stage (window)
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        // Close the current stage
        currentStage.close();

        // Exit the application
        Platform.exit();
    }

    public void setActionBtn(Button btn,TextField addyears)
    {
        btn.setOnAction(event -> {

            String query = "Insert into Academicyear values ('" + addyears.getText() + "')";

            try (Connection conn = DatabaseConnector.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                //pstmt.setString(5, userId); // If needed, uncomment and add this line.

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0)
                {
                    Alert ensure = new Alert(Alert.AlertType.CONFIRMATION);
                    ensure.setContentText(" saved!");
                    ensure.showAndWait();
                }

            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        });
    }

}
