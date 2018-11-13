package pl.moja.wypozyczalnia.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;


public class TopMenuButtonsController {

    private static final String LIBRARY_FXML = "/fxml/Library.fxml";
    private static final String LIST_CARS_FXML = "/fxml/ListCars.fxml";
    private static final String HISTORY_FXML = "/fxml/History.fxml";
    private static final String ADD_CAR_FXML = "/fxml/AddCar.fxml";
    private static final String ADD_SEGMENT_FXML = "/fxml/AddSegment.fxml";
    private static final String ADD_CLIENT_FXML = "/fxml/AddClient.fxml";

    private MainController mainController;
    
    

    @FXML
    private ToggleGroup toggleButtons;

    @FXML
    public void openLibrary() {
        mainController.setCenter(LIBRARY_FXML);
    }

    @FXML
    public void openListCars() {
        mainController.setCenter(LIST_CARS_FXML);
    }

    @FXML
    public void openStatistics() {
        mainController.setCenter(HISTORY_FXML);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void addCar() {
        resetToggleButtons();
        mainController.setCenter(ADD_CAR_FXML);
    }

    @FXML
    public void addSegment() {
        resetToggleButtons();
        mainController.setCenter(ADD_SEGMENT_FXML);
    }

    @FXML
    public void addClient() {
        resetToggleButtons();
        mainController.setCenter(ADD_CLIENT_FXML);
    }

    private void resetToggleButtons() {
        if(toggleButtons.getSelectedToggle()!=null){
            toggleButtons.getSelectedToggle().setSelected(false);
        }
    }


}
