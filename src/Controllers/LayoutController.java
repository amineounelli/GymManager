package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class LayoutController {

    private static LayoutController instance;

    @FXML private AnchorPane contentArea;
    @FXML public StackPane rootStack;
    @FXML private AnchorPane overlayPane;
    @FXML private VBox popupContainer;

    public LayoutController() {
        instance = this;
    }

    public static LayoutController getInstance() {
        return instance;
    }

    // ===============================
    // LOAD NORMAL VIEWS
    // ===============================
    public void loadView(String path) {
        try {
            Node view = FXMLLoader.load(getClass().getResource(path));

            contentArea.getChildren().setAll(view);

            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ===============================
    // SHOW POPUP WINDOW (OVERLAY)
    // ===============================
    public void showPopup(Node popupContent) {
        popupContainer.getChildren().setAll(popupContent);
        overlayPane.setVisible(true);
    }

    // ===============================
    // CLOSE POPUP
    // ===============================
    public void closePopup() {
        overlayPane.setVisible(false);
        popupContainer.getChildren().clear();
    }
    
    public void setContent(Node view) {
    contentArea.getChildren().setAll(view);
    AnchorPane.setTopAnchor(view, 0.0);
    AnchorPane.setBottomAnchor(view, 0.0);
    AnchorPane.setLeftAnchor(view, 0.0);
    AnchorPane.setRightAnchor(view, 0.0);
}

}
