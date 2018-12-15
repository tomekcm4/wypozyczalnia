package pl.moja.wypozyczalnia.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import pl.moja.wypozyczalnia.modelFx.ClientFx;
import pl.moja.wypozyczalnia.modelFx.CarFx;
import pl.moja.wypozyczalnia.modelFx.SegmentFx;
import pl.moja.wypozyczalnia.modelFx.ListCarsModel;
import pl.moja.wypozyczalnia.utils.DialogsUtils;
import pl.moja.wypozyczalnia.utils.FxmlUtils;
import pl.moja.wypozyczalnia.utils.exceptions.ApplicationException;

import java.io.IOException;
import java.time.LocalDate;

public class ListCarsController {

	@FXML
	private ComboBox segmentComboBox;
	@FXML
	private ComboBox clientComboBox;
	@FXML
	private TableView<CarFx> carsTableView;
	@FXML
	private TableColumn<CarFx, String> titleColumn;
	@FXML
	private TableColumn<CarFx, String> descColumn;
	@FXML
	private TableColumn<CarFx, ClientFx> clientColumn;
	@FXML
	private TableColumn<CarFx, SegmentFx> segmentColumn;
	@FXML
	private TableColumn<CarFx, Number> daysColumn;
	@FXML
	private TableColumn<CarFx, String> vinColumn;
	@FXML
	private TableColumn<CarFx, Number> priceColumn;
	@FXML
	private TableColumn<CarFx, Number> basepriceColumn;

	@FXML
	private TableColumn<CarFx, LocalDate> releaseColumn;
	@FXML
	private TableColumn<CarFx, CarFx> deleteColumn;
	@FXML
	private TableColumn<CarFx, CarFx> editColumn;

	private ListCarsModel listCarsModel;

	@FXML
	void initialize() {
		this.listCarsModel = new ListCarsModel();
		try {
			this.listCarsModel.init();
		} catch (ApplicationException e) {
			DialogsUtils.errorDialog(e.getMessage());
		}

		this.segmentComboBox.setItems(this.listCarsModel.getSegmentFxObservableList());
		
		
		
		
		
		
		
		this.clientComboBox.setItems(this.listCarsModel.getClientFxObservableList());
		this.listCarsModel.segmentFxObjectPropertyProperty().bind(this.segmentComboBox.valueProperty());
		this.listCarsModel.clientFxObjectPropertyProperty().bind(this.clientComboBox.valueProperty());

		this.carsTableView.setItems(this.listCarsModel.getCarFxObservableList());

		this.titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
		this.descColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		this.daysColumn.setCellValueFactory(cellData -> cellData.getValue().daysProperty());
		this.vinColumn.setCellValueFactory(cellData -> cellData.getValue().vinProperty());
		this.priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
		this.basepriceColumn.setCellValueFactory(cellData -> cellData.getValue().basepriceProperty());
		this.releaseColumn.setCellValueFactory(cellData -> cellData.getValue().releaseDateProperty());
		this.clientColumn.setCellValueFactory(cellData -> cellData.getValue().clientFxProperty());
		this.segmentColumn.setCellValueFactory(cellData -> cellData.getValue().segmentFxProperty());
		this.deleteColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
		this.editColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));

	//	fillTableView();

		this.deleteColumn.setCellFactory(param -> new TableCell<CarFx, CarFx>() {
			Button button = createButton("/icons/delete.png");

			@Override
			protected void updateItem(CarFx item, boolean empty) {
				super.updateItem(item, empty);

				if (empty) {
					
					setGraphic(null);
					return;
				}

				if (!empty) {
					setGraphic(button);
					button.setOnAction(event -> {
						try {
							listCarsModel.deleteCar(item);
						} catch (ApplicationException e) {
							DialogsUtils.errorDialog(e.getMessage());
						}
					});
				}
			}
		});

		this.editColumn.setCellFactory(param -> new TableCell<CarFx, CarFx>() {
			Button button = createButton("/icons/edit.png");

			@Override
			protected void updateItem(CarFx item, boolean empty) {
				super.updateItem(item, empty);

				if (empty) {
					setGraphic(null);
					return;
				}

				if (!empty) {
					setGraphic(button);
					button.setOnAction(event -> {
						FXMLLoader loader = FxmlUtils.getLoader("/fxml/AddCar.fxml");
						Scene scene = null;
						try {
							scene = new Scene(loader.load());
						} catch (IOException e) {
							DialogsUtils.errorDialog(e.getMessage());
						}
						CarController controller = loader.getController();
						controller.getCarModel().setCarFxObjectProperty(item);
						controller.bindings();
						
						Stage stage = new Stage();
						stage.setScene(scene);
						stage.initModality(Modality.APPLICATION_MODAL);
						stage.showAndWait();
					});
				}
			}
		});

		
		clientColumn.setCellFactory(new Callback<TableColumn<CarFx, ClientFx>, TableCell<CarFx, ClientFx>>() {
			public TableCell<CarFx, ClientFx> call(TableColumn<CarFx, ClientFx> carStringTableColumn) {
				return new TableCell<CarFx, ClientFx>() {
					@Override
					protected void updateItem(ClientFx item, boolean empty) {
						super.updateItem(item, empty);

						setText(empty ? "" : getItem().toString());
						setGraphic(null);

						TableRow<CarFx> currentRow = getTableRow();

						if (!isEmpty()) {

							if (item.getName().equals("EMPTY") || item.getSurname().equals("EMPTY")) {
								currentRow.setStyle("-fx-background-color:#9ACD32");
							}

							else {
								currentRow.setStyle("-fx-background-color:#CD5C5C");
							}

						}
					};

				};

			}
		}

		);
		
		
		
	}
	
	
	
	
	

//	private TableCell<CarFx,String> fillTableView() {
//
//		clientColumn.setCellFactory(column -> {
//			
//			
//			return new TableCell<CarFx, String>() {
//				@Override
//				protected void updateItem(String item, boolean empty) {
//					super.updateItem(item, empty);
//
//					setText(empty ? "" : getItem().toString());
//					setGraphic(null);
//
//					TableRow<CarFx> currentRow = getTableRow();
//
//					if (!isEmpty()) {
//
//						if (item.equals("EMPTY EMPTY"))
//							currentRow.setStyle("-fx-background-color:green");
//						else
//							currentRow.setStyle("-fx-background-color:blue");
//					}
//				}
//			};
//		});
//
//	}

	private Button createButton(String path) {
		Button button = new Button();
		Image image = new Image(this.getClass().getResource(path).toString());
		ImageView imageView = new ImageView(image);
		button.setGraphic(imageView);
		return button;
	}

	public void filterOnActionComboBox() {
		this.listCarsModel.filterCarsList();
	}

	public void clearSegmentComboBox() {
		this.segmentComboBox.getSelectionModel().clearSelection();
	}

	public void clearClientComboBox() {
		this.clientComboBox.getSelectionModel().clearSelection();
	}
}
