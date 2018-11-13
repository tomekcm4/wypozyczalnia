package pl.moja.wypozyczalnia.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import pl.moja.wypozyczalnia.controllers.MainController;

import java.util.ResourceBundle;


public class FxmlUtils {

    public static <T> Pair<Pane, T>  fxmlLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));
        loader.setResources(getResourceBundle());
        try {
            return new Pair<>(loader.load(),loader.getController());
        } catch (Exception e) {
           DialogsUtils.errorDialog(e.getMessage());
        }
        return new Pair<>(null,null);
    }

    public static FXMLLoader getLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));
        loader.setResources(getResourceBundle());
        return loader;
    }


    public static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("bundles.messages");
    }

}