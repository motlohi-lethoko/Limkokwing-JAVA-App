package motlohi.javagroupassignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class lecturer_reports {

    @FXML
    private Button btncancel;

    @FXML
    private Button btnok;

    @FXML
    private TableColumn<User, String> challenges;

    @FXML
    private TableColumn<User, Integer> class_id;

    @FXML
    private TableColumn<User, String> created_at;

    @FXML
    private TableColumn<User, Integer> lecture_id;

    @FXML
    private TableColumn<User, Integer> module_id;

    @FXML
    private TableColumn<User, String> recommendations;

    @FXML
    private TableColumn<User, Integer> report_id;

    @FXML
    private TableColumn<User, Integer> week_number;

    @FXML
    private TableView<User> reportTable;

    private ObservableList<User> userData = FXCollections.observableArrayList();

    private DatabaseConnector dbConnector = new DatabaseConnector();

    // Default constructor required by FXML
    public lecturer_reports()
    {

    }

    // Method to handle Cancel button click
    @FXML
    void cancel(ActionEvent event) throws IOException {
        // Close the current (login) stage
        Stage currentStage = (Stage) btncancel.getScene().getWindow();
        currentStage.close();

        // Load the dashboard scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("prldashboard.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Create a new stage for the dashboard and show it
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("prldash board");
        dashboardStage.show();
    }

    // Method to handle OK button click
    @FXML
    void ok(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you want to stay viewing the report?");
        alert.setContentText("Click OK to stay or Cancel to return to the dashboard.");

        // Close the current (login) stage
        Stage currentStage = (Stage) btnok.getScene().getWindow();
        currentStage.close();

        // Load the dashboard scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("prldashboard.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Create a new stage for the dashboard and show it
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("prldash board");
        dashboardStage.show();
    }

    // Initialize method to set up table columns and load data
    @FXML
    public void initialize() {
        // Set cell value factories to match the property names of the User class
        report_id.setCellValueFactory(new PropertyValueFactory<>("report_id"));
        lecture_id.setCellValueFactory(new PropertyValueFactory<>("lecture_id"));
        class_id.setCellValueFactory(new PropertyValueFactory<>("class_id"));
        module_id.setCellValueFactory(new PropertyValueFactory<>("module_id"));
        week_number.setCellValueFactory(new PropertyValueFactory<>("week_number"));
        challenges.setCellValueFactory(new PropertyValueFactory<>("challenges"));
        recommendations.setCellValueFactory(new PropertyValueFactory<>("recommendations"));
        created_at.setCellValueFactory(new PropertyValueFactory<>("created_at"));

        // Load data from the database
        loadReportData();
    }

    // Load report data from database into the TableView
    private void loadReportData() {
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT * FROM lecturer_reports";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                userData.add(new User(
                        resultSet.getInt("report_id"),
                        resultSet.getInt("lecture_id"),
                        resultSet.getInt("module_id"),
                        resultSet.getInt("class_id"),
                        resultSet.getInt("week_number"),
                        resultSet.getString("challenges"),
                        resultSet.getString("recommendations"),
                        resultSet.getString("created_at")
                ));
            }
            reportTable.setItems(userData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // User class to represent data in the TableView
    public static class User {
        private Integer report_id;
        private Integer lecture_id;
        private Integer module_id;
        private Integer class_id;
        private Integer week_number;
        private String challenges;
        private String recommendations;
        private String created_at;

        public User(Integer report_id, Integer lecture_id, Integer module_id, Integer class_id, Integer week_number,
                    String challenges, String recommendations, String created_at) {
            this.report_id = report_id;
            this.lecture_id = lecture_id;
            this.module_id = module_id;
            this.class_id = class_id;
            this.week_number = week_number;
            this.challenges = challenges;
            this.recommendations = recommendations;
            this.created_at = created_at;
        }

        public Integer getReport_id() { return report_id; }
        public Integer getLecture_id() { return lecture_id; }
        public Integer getModule_id() { return module_id; }
        public Integer getClass_id() { return class_id; }
        public Integer getWeek_number() { return week_number; }
        public String getChallenges() { return challenges; }
        public String getRecommendations() { return recommendations; }
        public String getCreated_at() { return created_at; }

        public void setReport_id(Integer report_id) { this.report_id = report_id; }
        public void setLecture_id(Integer lecture_id) { this.lecture_id = lecture_id; }
        public void setModule_id(Integer module_id) { this.module_id = module_id; }
        public void setClass_id(Integer class_id) { this.class_id = class_id; }
        public void setWeek_number(Integer week_number) { this.week_number = week_number; }
        public void setChallenges(String challenges) { this.challenges = challenges; }
        public void setRecommendations(String recommendations) { this.recommendations = recommendations; }
        public void setCreated_at(String created_at) { this.created_at = created_at; }
    }
}
