package motlohi.javagroupassignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Viewprofile {

    @FXML
    private Button btnok;

    @FXML
    private Label lblmessage;

    @FXML
    private TableColumn<User, String> password;

    @FXML
    private TableColumn<User, String> role;

    @FXML
    private TableView<User> tbltable;

    @FXML
    private TableColumn<User, Integer> user_id;

    @FXML
    private TableColumn<User, String> username;

    private ObservableList<User> userData = FXCollections.observableArrayList();

    // Inner User class to represent the data model
    public static class User {
        private int userId;
        private String username;
        private String password;
        private String role;

        public User(int userId, String username, String password, String role) {
            this.userId = userId;
            this.username = username;
            this.password = password;
            this.role = role;
        }

        public int getUserId() { return userId; }
        public void setUserId(int userId) { this.userId = userId; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }

    // Initialize method to set up the TableView columns and load data
    @FXML
    public void initialize() {
        // Set up the columns in the table
        user_id.setCellValueFactory(new PropertyValueFactory<>("userId"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Load data from the database
        loadUserData();
    }

    private void loadUserData() {
        // Connect to the database
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT user_id, username, password, role FROM users")) {

            // Clear any existing data
            userData.clear();

            // Add data to the ObservableList
            while (rs.next()) {
                userData.add(new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"), rs.getString("role")));
            }

            // Set the data to the TableView
            tbltable.setItems(userData);

        } catch (SQLException e) {
            lblmessage.setText("Error loading data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void ok(ActionEvent event) throws IOException {
        lblmessage.setText("Profile Viewed successfully.");


        Stage currentStage = (Stage) btnok.getScene().getWindow();
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
