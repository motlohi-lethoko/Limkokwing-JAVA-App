package motlohi.javagroupassignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class HelloApplication extends Application {
    private DatabaseConnector DatabaseConnection;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DashBoard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Limkokwing University!");
        stage.setScene(scene);
        stage.show();

        //test connection
        testDatabaseConnection();
    }

    public static void main(String[] args) {
        launch();
    }

    // Method to test database connection
    private void testDatabaseConnection() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Connected to the database successfully!");
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            System.out.println("Database connection error:");
            e.printStackTrace();
        }
    }
}