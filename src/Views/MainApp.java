package Views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principale de l'application de gestion de salle de gym
 * Gère la navigation entre l'écran de login et le menu principal
 *
 * @author DELL
 */
public class MainApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        // Charger l'écran de login au démarrage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("🏋️ Gym Management - Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Affiche le menu principal après une connexion réussie
     */
    public static void showMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("Menu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            primaryStage.setTitle("🏋️ Système de Gestion de Salle de Gym");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement du menu principal: " + e.getMessage());
        }
    }

    /**
     * Retour à l'écran de login (déconnexion)
     */
    public static void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            primaryStage.setTitle("🏋️ Gym Management - Login");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de l'écran de login: " + e.getMessage());
        }
    }

    /**
     * Déconnexion - retour à l'écran de login
     */
    public static void logout() {
        showLoginScreen();
    }

    /**
     * Obtenir le Stage principal
     * @return Stage principal de l'application
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}