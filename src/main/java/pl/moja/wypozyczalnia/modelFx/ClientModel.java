package pl.moja.wypozyczalnia.modelFx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.moja.wypozyczalnia.database.dao.ClientDao;
import pl.moja.wypozyczalnia.database.dao.CarDao;
import pl.moja.wypozyczalnia.database.models.Client;
import pl.moja.wypozyczalnia.database.models.Car;
import pl.moja.wypozyczalnia.database.models.Client;
import pl.moja.wypozyczalnia.utils.converters.ConverterClient;
import pl.moja.wypozyczalnia.utils.exceptions.ApplicationException;

import java.sql.SQLException;
import java.util.List;


public class ClientModel {

    private ObjectProperty<ClientFx> clientFxObjectProperty = new SimpleObjectProperty<>(new ClientFx());
    private ObjectProperty<ClientFx> clientFxObjectPropertyEdit = new SimpleObjectProperty<>(new ClientFx());

    private ObservableList<ClientFx> clientFxObservableList = FXCollections.observableArrayList();


    public void init() throws ApplicationException {
        ClientDao clientDao = new ClientDao();
        List<Client> clientList = clientDao.queryForAll(Client.class);
        this.clientFxObservableList.clear();
        clientList.forEach(client -> {
            ClientFx clientFx = ConverterClient.convertToClientFx(client);
            this.clientFxObservableList.add(clientFx);
        });

    }

    public void saveClientEditInDataBase() throws ApplicationException {
        saveOrUpdate(this.getClientFxObjectPropertyEdit());
    }
    public void saveClientInDataBase() throws ApplicationException {
        saveOrUpdate(this.getClientFxObjectProperty());
    }

    public void deleteClientInDataBase() throws ApplicationException, SQLException {
        ClientDao clientDao = new ClientDao();
        clientDao.deleteById(Client.class, this.getClientFxObjectPropertyEdit().getId());
        CarDao carDao = new CarDao();
        carDao.deleteByColumnName(Car.CLIENT_ID, this.getClientFxObjectPropertyEdit().getId());
        this.init();
    }

    private void saveOrUpdate(ClientFx clientFxObjectPropertyEdit) throws ApplicationException {
        ClientDao clientDao = new ClientDao();
        Client client = ConverterClient.converToClient(clientFxObjectPropertyEdit);
        clientDao.creatOrUpdate(client);
        this.init();
    }





    public ClientFx getClientFxObjectProperty() {
        return clientFxObjectProperty.get();
    }

    public ObjectProperty<ClientFx> clientFxObjectPropertyProperty() {
        return clientFxObjectProperty;
    }

    public void setClientFxObjectProperty(ClientFx clientFxObjectProperty) {
        this.clientFxObjectProperty.set(clientFxObjectProperty);
    }

    public ObservableList<ClientFx> getClientFxObservableList() {
        return clientFxObservableList;
    }

    public void setClientFxObservableList(ObservableList<ClientFx> clientFxObservableList) {
        this.clientFxObservableList = clientFxObservableList;
    }

    public ClientFx getClientFxObjectPropertyEdit() {
        return clientFxObjectPropertyEdit.get();
    }

    public ObjectProperty<ClientFx> clientFxObjectPropertyEditProperty() {
        return clientFxObjectPropertyEdit;
    }

    public void setClientFxObjectPropertyEdit(ClientFx clientFxObjectPropertyEdit) {
        this.clientFxObjectPropertyEdit.set(clientFxObjectPropertyEdit);
    }
}
