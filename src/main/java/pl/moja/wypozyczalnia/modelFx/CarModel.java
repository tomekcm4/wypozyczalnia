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

import java.util.List;


public class CarModel {


    private ObjectProperty<CarFx> carFxObjectProperty = new SimpleObjectProperty<>(new CarFx());
    private ObservableList<SegmentFx> segmentFxObservableList = FXCollections.observableArrayList();
    private ObservableList<ClientFx> clientFxObservableList = FXCollections.observableArrayList();


    public void init() throws ApplicationException {
        initClientList();
        initSegmentList();
    }

    private void initSegmentList() throws ApplicationException {
        SegmentDao segmentDao = new SegmentDao();
        List<Segment> segmentList = segmentDao.queryForAll(Segment.class);
        segmentFxObservableList.clear();
        segmentList.forEach(c->{
            SegmentFx segmentFx = ConverterSegment.convertToSegmentFx(c);
            segmentFxObservableList.add(segmentFx);
        });
    }

    private void initClientList() throws ApplicationException {
        ClientDao clientDao = new ClientDao();
        List<Client> clientList = clientDao.queryForAll(Client.class);
        this.clientFxObservableList.clear();
        clientList.forEach(client -> {
            ClientFx clientFx = ConverterClient.convertToClientFx(client);
            this.clientFxObservableList.add(clientFx);
        });
    }

    public void saveCarInDataBase() throws ApplicationException {
        Car car = ConverterCar.convertToCar(this.getCarFxObjectProperty());

        SegmentDao segmentDao = new SegmentDao();
        Segment segment = segmentDao.findById(Segment.class, this.getCarFxObjectProperty().getSegmentFx().getId());

        ClientDao clientDao = new ClientDao();
        Client client = clientDao.findById(Client.class, this.getCarFxObjectProperty().getClientFx().getId());

        car.setSegment(segment);
        car.setClient(client);

        CarDao carDao = new CarDao();
        carDao.creatOrUpdate(car);

    }


    public CarFx getCarFxObjectProperty() {
        return carFxObjectProperty.get();
    }

    public ObjectProperty<CarFx> carFxObjectPropertyProperty() {
        return carFxObjectProperty;
    }

    public void setCarFxObjectProperty(CarFx carFxObjectProperty) {
        this.carFxObjectProperty.set(carFxObjectProperty);
    }

    public ObservableList<SegmentFx> getSegmentFxObservableList() {
        return segmentFxObservableList;
    }

    public void setSegmentFxObservableList(ObservableList<SegmentFx> segmentFxObservableList) {
        this.segmentFxObservableList = segmentFxObservableList;
    }

    public ObservableList<ClientFx> getClientFxObservableList() {
        return clientFxObservableList;
    }

    public void setClientFxObservableList(ObservableList<ClientFx> clientFxObservableList) {
        this.clientFxObservableList = clientFxObservableList;
    }
}
