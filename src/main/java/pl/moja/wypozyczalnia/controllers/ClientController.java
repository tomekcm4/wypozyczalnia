package pl.moja.wypozyczalnia.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import pl.moja.wypozyczalnia.database.models.Client;
import pl.moja.wypozyczalnia.modelFx.ClientFx;
import pl.moja.wypozyczalnia.modelFx.ClientModel;
import pl.moja.wypozyczalnia.modelFx.ClientFx;
import pl.moja.wypozyczalnia.modelFx.ClientModel;
import pl.moja.wypozyczalnia.utils.DialogsUtils;
import pl.moja.wypozyczalnia.utils.exceptions.ApplicationException;

import java.sql.SQLException;


public class ClientController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private Button addButton;
    @FXML
    private TableView<ClientFx> clientTableView;
    @FXML
    private TableColumn<ClientFx, String> nameColumn;
    @FXML
    private TableColumn<ClientFx, String> surnameColumn;
    @FXML
    private MenuItem deleteMenuItem;


    private ClientModel clientModel;

    public void initialize() {
        this.clientModel = new ClientModel();
        try {
            this.clientModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        bindings();
        bindingsTableView();
    }

    private void bindingsTableView() {
        this.clientTableView.setItems(this.clientModel.getClientFxObservableList());
        this.nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.surnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());

        this.nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        this.clientTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.clientModel.setClientFxObjectPropertyEdit(newValue);
        });
    }

    private void bindings() {
        this.clientModel.clientFxObjectPropertyProperty().get().nameProperty().bind(this.nameTextField.textProperty());
        this.clientModel.clientFxObjectPropertyProperty().get().surnameProperty().bind(this.surnameTextField.textProperty());
        this.addButton.disableProperty().bind(this.nameTextField.textProperty().isEmpty().or(this.surnameTextField.textProperty().isEmpty()));
        this.deleteMenuItem.disableProperty().bind(this.clientTableView.getSelectionModel().selectedItemProperty().isNull());
    }

    public void addClientOnAction() {
        try {
            this.clientModel.saveClientInDataBase();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        this.nameTextField.clear();
        this.surnameTextField.clear();
    }

    public void onEditCommitName(TableColumn.CellEditEvent<ClientFx, String> clientFxStringCellEditEvent) {
        this.clientModel.getClientFxObjectPropertyEdit().setName(clientFxStringCellEditEvent.getNewValue());
        updateInDatabase();
    }

    public void onEditCommitSurname(TableColumn.CellEditEvent<ClientFx, String> clientFxStringCellEditEvent) {
        this.clientModel.getClientFxObjectPropertyEdit().setSurname(clientFxStringCellEditEvent.getNewValue());
        updateInDatabase();
    }

    private void updateInDatabase() {
        try {
            this.clientModel.saveClientEditInDataBase();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    public void deleteClientOnAction() {
        try {
            this.clientModel.deleteClientInDataBase();
        } catch (ApplicationException | SQLException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }
}
