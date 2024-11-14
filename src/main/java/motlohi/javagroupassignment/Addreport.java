package motlohi.javagroupassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

public class Addreport {

    @FXML
    private Button btncancel;

    @FXML
    private Button btnsave;

    @FXML
    private Label lblchallenges;

    @FXML
    private Label lbllecture_id;

    @FXML
    private Label lblmodule_id;

    @FXML
    private Label lblrecommendations;

    @FXML
    private Label lblreport_id;

    @FXML
    private TextField txtweek_number;

    @FXML
    private TextField txtclass_id;

    @FXML
    private TextField txtchallenges;

    @FXML
    private TextField txtlecture_id;

    @FXML
    private TextField txtmodule_id;

    @FXML
    private TextField txtrecommandations;

    @FXML
    private TextField txtreport_id;

    // SQL Database connection using DatabaseConnector
    private Connection connection;

    public void setTxtreport_id(String value) {
        txtreport_id.setText(value);
    }

    public Addreport() throws SQLException
    {
        connection = DatabaseConnector.getConnection(); // Assuming you have a static method getConnection in DatabaseConnector class
    }

    @FXML
    void save(ActionEvent event) {
        String reportId = txtreport_id.getText();
        String classId = txtclass_id.getText();
        String weekNumber = txtweek_number.getText();
        String challenges = txtchallenges.getText();
        String lectureId = txtlecture_id.getText();
        String moduleId = txtmodule_id.getText();
        String recommendations = txtrecommandations.getText();

        // Change table name to lecturer_reports
        String sql = "INSERT INTO lecturer_reports (report_id, class_id, week_number, challenges, lecture_id, module_id, recommendations) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, reportId);
            statement.setString(2, classId);
            statement.setString(3, weekNumber);
            statement.setString(4, challenges);
            statement.setString(5, lectureId);
            statement.setString(6, moduleId);
            statement.setString(7, recommendations);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                Alert com = new Alert(Alert.AlertType.CONFIRMATION);
                com.setContentText("data saved ");
                com.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        try {
            // Load Prldashboard FXML to navigate back
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Prldashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btncancel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
