package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LayoutController {

    @FXML private AnchorPane contentArea;

    @FXML private AnchorPane overlayPane;
    @FXML private VBox popupContainer;

    private static LayoutController instance;

    public LayoutController() {
        instance = this;
    }

    public static LayoutController getInstance() {
        return instance;
    }

    // UNIVERSAL VIEW LOADER
    public void loadView(String fxmlResourcePath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlResourcePath));

            // anchor if possible (only if it's an AnchorPane)
            if (view instanceof AnchorPane ap) {
                AnchorPane.setTopAnchor(ap, 0.0);
                AnchorPane.setBottomAnchor(ap, 0.0);
                AnchorPane.setLeftAnchor(ap, 0.0);
                AnchorPane.setRightAnchor(ap, 0.0);
            }

            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            System.err.println("FAILED TO LOAD: " + fxmlResourcePath);
            e.printStackTrace();
        }
    }

    // POPUP SYSTEM
    public void showPopup(String fxmlPath) {
        try {
            Parent popup = FXMLLoader.load(getClass().getResource(fxmlPath));
            popupContainer.getChildren().setAll(popup);
            overlayPane.setVisible(true);
        } catch (IOException e) {
            System.err.println("FAILED TO LOAD POPUP: " + fxmlPath);
            e.printStackTrace();
        }
    }

    public void closePopup() {
        overlayPane.setVisible(false);
        popupContainer.getChildren().clear();
    }

    @FXML
    public void initialize() {
        overlayPane.setVisible(false);
        loadView("/Views/Dashboard.fxml");
    }
}
