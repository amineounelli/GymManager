package Views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // FIRST load the Login screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Login.fxml"));

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Gym Management - Login");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
