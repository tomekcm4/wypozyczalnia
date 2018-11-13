package pl.moja.wypozyczalnia.modelFx;

import javafx.beans.property.*;

import java.time.LocalDate;


public class CarFx {

    private IntegerProperty id = new SimpleIntegerProperty();
    private ObjectProperty<SegmentFx> segmentFx = new SimpleObjectProperty<>();
    private ObjectProperty<ClientFx> clientFx = new SimpleObjectProperty<>();
    private SimpleStringProperty title = new SimpleStringProperty();
    private SimpleStringProperty description = new SimpleStringProperty();
    private ObjectProperty<LocalDate> releaseDate = new SimpleObjectProperty<>();
    private SimpleStringProperty vin = new SimpleStringProperty();
    private IntegerProperty days = new SimpleIntegerProperty();
    private IntegerProperty price = new SimpleIntegerProperty();
    private IntegerProperty baseprice = new SimpleIntegerProperty();
    private ObjectProperty<LocalDate> addedDate = new SimpleObjectProperty(LocalDate.now());

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public SegmentFx getSegmentFx() {
        return segmentFx.get();
    }

    public ObjectProperty<SegmentFx> segmentFxProperty() {
        return segmentFx;
    }

    public void setSegmentFx(SegmentFx segmentFx) {
        this.segmentFx.set(segmentFx);
    }

    public ClientFx getClientFx() {
        return clientFx.get();
    }

    public ObjectProperty<ClientFx> clientFxProperty() {
        return clientFx;
    }

    public void setClientFx(ClientFx clientFx) {
        this.clientFx.set(clientFx);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getVin() {
        return vin.get();
    }

    public SimpleStringProperty vinProperty() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin.set(vin);
    }

    public int getDays() {
        return days.get();
    }

    public IntegerProperty daysProperty() {
        return days;
    }

    public void setDays(int days) {
        this.days.set(days);
    }
    
    
    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }
    
    
    public int getBasePrice() {
        return baseprice.get();
    }

    public IntegerProperty basepriceProperty() {
        return baseprice;
    }

    public void setBasePrice(int baseprice) {
        this.baseprice.set(baseprice);
    }
    
    
    

    public LocalDate getReleaseDate() {
        return releaseDate.get();
    }

    public ObjectProperty<LocalDate> releaseDateProperty() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate.set(releaseDate);
    }

    public LocalDate getAddedDate() {
        return addedDate.get();
    }

    public ObjectProperty<LocalDate> addedDateProperty() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate.set(addedDate);
    }

    @Override
    public String toString() {
        return "CarFx{" +
                "id=" + id.get() +
                ", segmentFx=" + segmentFx.get() +
                ", clientFx=" + clientFx.get() +
                ", title=" + title.get() +
                ", description=" + description.get() +
                ", releaseDate=" + releaseDate.get() +
                ", vin=" + vin.get() +
                ", days=" + days.get() +
                ", addedDate=" + addedDate.get() +
                '}';
    }
}
