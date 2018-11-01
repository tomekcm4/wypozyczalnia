package pl.moja.wypozyczalnia.modelFx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.moja.wypozyczalnia.database.dao.ClientDao;
import pl.moja.wypozyczalnia.database.dao.CarDao;
import pl.moja.wypozyczalnia.database.dao.SegmentDao;
import pl.moja.wypozyczalnia.database.models.Client;
import pl.moja.wypozyczalnia.database.models.Car;
import pl.moja.wypozyczalnia.database.models.Segment;
import pl.moja.wypozyczalnia.utils.converters.ConverterClient;
import pl.moja.wypozyczalnia.utils.converters.ConverterCar;
import pl.moja.wypozyczalnia.utils.converters.ConverterSegment;
import pl.moja.wypozyczalnia.utils.exceptions.ApplicationException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ListCarsModel {

    private ObservableList<CarFx> carFxObservableList = FXCollections.observableArrayList();
    private ObservableList<ClientFx> clientFxObservableList = FXCollections.observableArrayList();
    private ObservableList<SegmentFx> segmentFxObservableList = FXCollections.observableArrayList();

    private ObjectProperty<ClientFx> clientFxObjectProperty = new SimpleObjectProperty<>();
    private ObjectProperty<SegmentFx> segmentFxObjectProperty = new SimpleObjectProperty<>();

    private List<CarFx> carFxList = new ArrayList<>();

    public void init() throws ApplicationException {
        CarDao carDao = new CarDao();
        List<Car> cars = carDao.queryForAll(Car.class);
        carFxList.clear();
        cars.forEach(car -> {
            this.carFxList.add(ConverterCar.convertToCarFx(car));
        });
        this.carFxObservableList.setAll(carFxList);

        initClients();
        initSegment();
    }

    public void filterCarsList() {
        if (getClientFxObjectProperty() != null && getSegmentFxObjectProperty() != null) {
            filterPredicate(predicateClient().and(predicateCatgory()));
        } else if (getSegmentFxObjectProperty() != null) {
            filterPredicate(predicateCatgory());
        } else if (getClientFxObjectProperty() != null) {
            filterPredicate(predicateClient());
        } else {
            this.carFxObservableList.setAll(this.carFxList);
        }
    }

    public void deleteCar(CarFx carFx) throws ApplicationException {
        CarDao carDao = new CarDao();
        carDao.deleteById(Car.class, carFx.getId());
        init();
    }

    private void initClients() throws ApplicationException {
        ClientDao clientDao = new ClientDao();
        List<Client> clientList = clientDao.queryForAll(Client.class);
        this.clientFxObservableList.clear();
        clientList.forEach(client -> {
            ClientFx clientFx = ConverterClient.convertToClientFx(client);
            this.clientFxObservableList.add(clientFx);
        });
    }

    private void initSegment() throws ApplicationException {
        SegmentDao segmentDao = new SegmentDao();
        List<Segment> segments = segmentDao.queryForAll(Segment.class);
        this.segmentFxObservableList.clear();
        segments.forEach(c -> {
            SegmentFx segmentFx = ConverterSegment.convertToSegmentFx(c);
            this.segmentFxObservableList.add(segmentFx);
        });
    }

    private Predicate<CarFx> predicateCatgory() {
        return carFx -> carFx.getSegmentFx().getId() == getSegmentFxObjectProperty().getId();
    }

    private Predicate<CarFx> predicateClient() {
        return carFx -> carFx.getClientFx().getId() == getClientFxObjectProperty().getId();
    }

    private void filterPredicate(Predicate<CarFx> predicate) {
        List<CarFx> newList = carFxList.stream().filter(predicate).collect(Collectors.toList());
        this.carFxObservableList.setAll(newList);
    }

    public ObservableList<CarFx> getCarFxObservableList() {
        return carFxObservableList;
    }

    public void setCarFxObservableList(ObservableList<CarFx> carFxObservableList) {
        this.carFxObservableList = carFxObservableList;
    }

    public ObservableList<ClientFx> getClientFxObservableList() {
        return clientFxObservableList;
    }

    public void setClientFxObservableList(ObservableList<ClientFx> clientFxObservableList) {
        this.clientFxObservableList = clientFxObservableList;
    }

    public ObservableList<SegmentFx> getSegmentFxObservableList() {
        return segmentFxObservableList;
    }

    public void setSegmentFxObservableList(ObservableList<SegmentFx> segmentFxObservableList) {
        this.segmentFxObservableList = segmentFxObservableList;
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

    public SegmentFx getSegmentFxObjectProperty() {
        return segmentFxObjectProperty.get();
    }

    public ObjectProperty<SegmentFx> segmentFxObjectPropertyProperty() {
        return segmentFxObjectProperty;
    }

    public void setSegmentFxObjectProperty(SegmentFx segmentFxObjectProperty) {
        this.segmentFxObjectProperty.set(segmentFxObjectProperty);
    }
}
