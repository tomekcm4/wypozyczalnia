package pl.moja.wypozyczalnia.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.moja.wypozyczalnia.database.models.Client;
import pl.moja.wypozyczalnia.modelFx.ClientFx;
import pl.moja.wypozyczalnia.modelFx.CarModel;
import pl.moja.wypozyczalnia.modelFx.SegmentFx;
import pl.moja.wypozyczalnia.utils.DialogsUtils;
import pl.moja.wypozyczalnia.utils.exceptions.ApplicationException;


public class CarController {

    @FXML
    private Button addButton;
    @FXML
    private ComboBox<SegmentFx> segmentComboBox;
    @FXML
    private ComboBox<ClientFx> clientComboBox;
    @FXML
    private TextArea descTextArea;
    @FXML
    private Slider ratingSlider;
    @FXML
    private TextField vinTextField;
    @FXML
    private DatePicker releaseDatePicker;
    @FXML
    private TextField titleTextField;

    private CarModel carModel;

    @FXML
    public void initialize() {
        this.carModel = new CarModel();
        try {
            this.carModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        bindings();
        validation();
    }

    private void validation() {
        this.addButton.disableProperty().bind(this.clientComboBox.valueProperty().isNull()
                .or(this.segmentComboBox.valueProperty().isNull())
                .or(this.titleTextField.textProperty().isEmpty())
                .or(this.descTextArea.textProperty().isEmpty())
                .or(this.vinTextField.textProperty().isEmpty())
                .or(this.releaseDatePicker.valueProperty().isNull()));
    }

    public void bindings() {
        this.segmentComboBox.setItems(this.carModel.getSegmentFxObservableList());
        this.clientComboBox.setItems(this.carModel.getClientFxObservableList());

        this.clientComboBox.valueProperty().bindBidirectional(this.carModel.getCarFxObjectProperty().clientFxProperty());
        this.segmentComboBox.valueProperty().bindBidirectional(this.carModel.getCarFxObjectProperty().segmentFxProperty());
        this.titleTextField.textProperty().bindBidirectional(this.carModel.getCarFxObjectProperty().titleProperty());
        this.descTextArea.textProperty().bindBidirectional(this.carModel.getCarFxObjectProperty().descriptionProperty());
        this.ratingSlider.valueProperty().bindBidirectional(this.carModel.getCarFxObjectProperty().ratingProperty());
        this.vinTextField.textProperty().bindBidirectional(this.carModel.getCarFxObjectProperty().vinProperty());
        this.releaseDatePicker.valueProperty().bindBidirectional(this.carModel.getCarFxObjectProperty().releaseDateProperty());
    }

    public void addCarOnAction() {
        try {
            this.carModel.saveCarInDataBase();
            clearFields();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    private void clearFields() {
        this.clientComboBox.getSelectionModel().clearSelection();
        this.segmentComboBox.getSelectionModel().clearSelection();
        this.titleTextField.clear();
        this.descTextArea.clear();
        this.ratingSlider.setValue(1);
        this.vinTextField.clear();
        this.releaseDatePicker.getEditor().clear();
    }

    public CarModel getCarModel() {
        return carModel;
    }
}
