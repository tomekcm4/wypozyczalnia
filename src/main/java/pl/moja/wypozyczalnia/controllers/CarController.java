package pl.moja.wypozyczalnia.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import javax.xml.ws.handler.Handler;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import pl.moja.wypozyczalnia.database.models.Client;
import pl.moja.wypozyczalnia.database.dao.CarDao;
import pl.moja.wypozyczalnia.database.models.Car;
import pl.moja.wypozyczalnia.modelFx.ClientFx;
import pl.moja.wypozyczalnia.modelFx.ListCarsModel;
import pl.moja.wypozyczalnia.modelFx.CarFx;
import pl.moja.wypozyczalnia.modelFx.CarModel;
import pl.moja.wypozyczalnia.modelFx.SegmentFx;
import pl.moja.wypozyczalnia.utils.DialogsUtils;
import pl.moja.wypozyczalnia.utils.FxmlUtils;
import pl.moja.wypozyczalnia.utils.converters.ConverterCar;
import pl.moja.wypozyczalnia.utils.exceptions.ApplicationException;

public class CarController implements InvalidationListener {

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
	private ListCarsModel listCarsModel;

	double mulitplier;

	private List<Car> cars = new ArrayList<>();
	private List<CarFx> carFxList = new ArrayList<>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	public void initialize() throws ApplicationException {

		this.carModel = new CarModel();
		try {
			this.carModel.init();
		} catch (ApplicationException e) {
			DialogsUtils.errorDialog(e.getMessage());
		}

		bindings();
		validation();
		validation2();

		daysSlider.valueProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2) {

				if (basepriceTextField.getText() != null && !basepriceTextField.getText().isEmpty())

					priceTextField.textProperty().setValue(String
							.valueOf((int) daysSlider.getValue() * Integer.parseInt(basepriceTextField.getText())));

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

	private void validation2() {
		this.releaseDatePicker.disableProperty().bind(this.vinTextField.textProperty().isEmpty());

	}

	public void bindings() throws ApplicationException {
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

		List<LocalDate> unavailableDates = new ArrayList<>();
		//unavailableDates.add(LocalDate.parse("2019-01-01"));

		CarDao carDao = new CarDao();
		List<Car> cars = carDao.queryForAll(Car.class);
		carFxList.clear();
		cars.forEach(car -> {
			this.carFxList.add(ConverterCar.convertToCarFx(car));
		});

		vinTextField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if(newValue.length() == 17) {
				
				System.out.println(" Text Changed to  " + newValue);

				for (CarFx car : carFxList) { // this part of code is not working, programs not iterating through this
												// list
					System.out.println("check if working"); // nothing happens

					System.out.println(car.getVin());

					if (car.getVin().equals(newValue)) {

						LocalDate da = car.getReleaseDate();
						da =da.minusDays(2);
						int nr = car.getDays(); // number of days for which car is reserved

						for (int i = 0; i < nr; i++) {

							da = da.plusDays(1);
							unavailableDates.add(da.plusDays(1));
						}

					}

				}

				releaseDatePicker.setDayCellFactory(picker -> new DateCell() {
					@Override
					public void updateItem(LocalDate date, boolean empty) {
						super.updateItem(date, empty);
						LocalDate today = LocalDate.now();

						setDisable(empty || date.compareTo(today) < 0); // I am also excluding all days in the past
						if (date != null && !empty) {

							// Compare date to List
							if (unavailableDates.contains(date)) {
								setDisable(true);

							}
						}
					}
				});

			}
			
			
		}

		});

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

	@Override
	public void invalidated(Observable observable) {
		// TODO Auto-generated method stub

	}
}
