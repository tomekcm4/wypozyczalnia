package pl.moja.wypozyczalnia.controllers;

import java.text.NumberFormat;
import java.util.ArrayList;

import javax.xml.ws.handler.Handler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.NumberStringConverter;
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
	private Slider daysSlider;
	@FXML
	private TextField vinTextField;
	@FXML
	private TextField priceTextField;
	@FXML

	private TextField basepriceTextField;
	@FXML
	private DatePicker releaseDatePicker;
	@FXML
	private TextField titleTextField;

	private CarModel carModel;

	double mulitplier;

	@SuppressWarnings({ "unchecked", "rawtypes" })
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

		daysSlider.valueProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2) {

				if (basepriceTextField.getText() != null && !basepriceTextField.getText().isEmpty()) 
				
				priceTextField.textProperty().setValue(
						String.valueOf((int) daysSlider.getValue() * Integer.parseInt(basepriceTextField.getText())));
				
				
			}

		});

	}

	private void validation() {
		this.addButton.disableProperty()
				.bind(this.clientComboBox.valueProperty().isNull().or(this.segmentComboBox.valueProperty().isNull())
						.or(this.titleTextField.textProperty().isEmpty()).or(this.descTextArea.textProperty().isEmpty())
						.or(this.vinTextField.textProperty().isEmpty())
						.or(this.releaseDatePicker.valueProperty().isNull()));
	}

	public void bindings() {
		this.segmentComboBox.setItems(this.carModel.getSegmentFxObservableList());
		this.clientComboBox.setItems(this.carModel.getClientFxObservableList());

		this.clientComboBox.valueProperty()
				.bindBidirectional(this.carModel.getCarFxObjectProperty().clientFxProperty());
		this.segmentComboBox.valueProperty()
				.bindBidirectional(this.carModel.getCarFxObjectProperty().segmentFxProperty());
		this.titleTextField.textProperty().bindBidirectional(this.carModel.getCarFxObjectProperty().titleProperty());
		this.descTextArea.textProperty()
				.bindBidirectional(this.carModel.getCarFxObjectProperty().descriptionProperty());
		
		this.vinTextField.textProperty().bindBidirectional(this.carModel.getCarFxObjectProperty().vinProperty());

		this.priceTextField.textProperty().bindBidirectional(this.carModel.getCarFxObjectProperty().priceProperty(),
				new NumberStringConverter());
		this.basepriceTextField.textProperty().bindBidirectional(
				this.carModel.getCarFxObjectProperty().basepriceProperty(), new NumberStringConverter());
		this.daysSlider.valueProperty().bindBidirectional(this.carModel.getCarFxObjectProperty().daysProperty());
		
		this.releaseDatePicker.valueProperty()
				.bindBidirectional(this.carModel.getCarFxObjectProperty().releaseDateProperty());
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
		this.releaseDatePicker.getEditor().clear();
		this.titleTextField.clear();
		this.priceTextField.clear();
		this.basepriceTextField.clear();
		this.descTextArea.clear();
		this.vinTextField.clear();
		this.daysSlider.setValue(1);
		
		
	}

	public CarModel getCarModel() {
		return carModel;
	}
}
