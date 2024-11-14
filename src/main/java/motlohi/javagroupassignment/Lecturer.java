package motlohi.javagroupassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Lecturer {

    @FXML
    private ChoiceBox<String> ChoiceStu;

    @FXML
    private Button markPresentButton;

    @FXML
    private CheckBox AbsentID;

    @FXML
    private TextField studentname;
    @FXML
    private CheckBox presentID;

    private String[] food={"pizza","pie","sushi"};


    //private ObservableList<Student> studentsList = FXCollections.observableArrayList();

    private static final String CLASS_ID = "class_1"; // Example class ID, replace with actual
    List<String> students = new ArrayList<>();

    @FXML
    public void initialize() {


        //loadStudentsWithoutAttendance(CLASS_ID);
        String Query = "select name from students";


        try (Connection connection = DatabaseConnector.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(Query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                String realName = resultSet.getString("name");
                students.add(realName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ChoiceStu.getItems().addAll(students);
        ChoiceStu.setOnAction(this::getFood);

    }

    public void getFood(ActionEvent event)
    {
        String myFood= ChoiceStu.getValue();

        studentname.setText(myFood);
    }

    @FXML
    void updatePresentId(ActionEvent event)
    {
        String  query = "update students set presentabsent = '" + "Present" +"' where name = '"+ studentname.getText() + "'";

            if(studentname.getText() == null)
            {
                Alert co = new Alert(Alert.AlertType.CONFIRMATION);
                co.setContentText("select student");
                co.showAndWait();
                return;
            }
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query))
        {

            int rowsAffected = preparedStatement.executeUpdate();
            AbsentID.setSelected(false);
            if (rowsAffected > 0) {
                // If save is successful, ask if they want to add another role
                //showConfirmation("Success", "Lecture role saved successfully! Do you want to add another role?");
                Alert co = new Alert(Alert.AlertType.CONFIRMATION);
                co.setContentText("marked as present");
                co.showAndWait();
            } else {
                //showAlert("Error", "Failed to save lecture role.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
           // showAlert("Database Error", "Could not connect to the database.");
        }

    }

    @FXML
    void upadateAbsent(ActionEvent event)
    {
        String  query = "update students set presentabsent = '" + "Absent" +"' where name = '"+ studentname.getText() + "'";

        presentID.setSelected(false);

        if(studentname.getText() == null)
        {
            Alert co = new Alert(Alert.AlertType.CONFIRMATION);
            co.setContentText("select student");
            co.showAndWait();
            return;
        }
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query))
        {

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                // If save is successful, ask if they want to add another role
                //showConfirmation("Success", "Lecture role saved successfully! Do you want to add another role?");
                Alert co = new Alert(Alert.AlertType.CONFIRMATION);
                co.setContentText("marked as absent");
                co.showAndWait();
            } else {
                //showAlert("Error", "Failed to save lecture role.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // showAlert("Database Error", "Could not connect to the database.");
        }
    }

    @FXML
    void DASHBOARD(ActionEvent event) throws IOException
    {
        Stage currentStage = (Stage) presentID.getScene().getWindow();
        currentStage.close();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("DashBoard.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Create a new stage for the dashboard and show it
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Add user");
        dashboardStage.show();
    }


    /*private void loadStudentsWithoutAttendance(String classId) {
        String query = "SELECT student_id, class_id FROM students WHERE class_id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, classId);
            ResultSet rs = stmt.executeQuery();

            // Clear the list before loading data
            studentsList.clear();

            // Fetch data and populate the list
            while (rs.next()) {
                String studentId = rs.getString("student_id");
                String retrievedClassId = rs.getString("class_id");

                // Debugging print statements to ensure data retrieval
                System.out.println("Retrieved Student ID: " + studentId + ", Class ID: " + retrievedClassId);

                studentsList.add(new Student(studentId, retrievedClassId, false));
            }

            // Confirm data was added to the list
            System.out.println("Total students loaded: " + studentsList.size());

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not load students.");
            e.printStackTrace();
        }
    }

    @FXML
    private void markSelectedAsPresent(ActionEvent event) {
        // Confirmation alert before saving
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Attendance");
        confirmAlert.setHeaderText("Save Attendance?");
        confirmAlert.setContentText("Do you want to mark the selected students as present?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Proceed with saving attendance if confirmed
            try (Connection connection = DatabaseConnector.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(
                         "INSERT INTO attendance (student_id, class_id, present) VALUES (?, ?, true)")) {

                connection.setAutoCommit(false);

                for (Student student : studentsList) {
                    if (student.isPresent()) {
                        stmt.setString(1, student.getStudentId());
                        stmt.setString(2, student.getClassId());
                        stmt.addBatch();
                    }
                }

                stmt.executeBatch();
                connection.commit();

                showAlert(Alert.AlertType.INFORMATION, "Success", "Attendance marked successfully!");

                // Close the window (simulating going back to dashboard)
                Stage stage = (Stage) markPresentButton.getScene().getWindow();
                stage.close();

            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to mark attendance.");
                e.printStackTrace();
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class Student {
        private final StringProperty studentId;
        private final StringProperty classId;
        private final BooleanProperty present;

        public Student(String studentId, String classId, Boolean present) {
            this.studentId = new SimpleStringProperty(studentId);
            this.classId = new SimpleStringProperty(classId);
            this.present = new SimpleBooleanProperty(present);
        }

        public String getStudentId() {
            return studentId.get();
        }

        public StringProperty studentIdProperty() {
            return studentId;
        }

        public String getClassId() {
            return classId.get();
        }

        public StringProperty classIdProperty() {
            return classId;
        }

        public Boolean isPresent() {
            return present.get();
        }

        public BooleanProperty presentProperty() {
            return present;
        }

        public void setPresent(Boolean present) {
            this.present.set(present);
        }
    }*/
}
