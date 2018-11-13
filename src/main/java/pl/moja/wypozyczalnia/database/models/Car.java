package pl.moja.wypozyczalnia.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;


@DatabaseTable(tableName = "CARS")
public class Car implements BaseModel{

    public static final String CLIENT_ID = "CLIENT_ID";
    public static final String SEGMENT_ID = "SEGMENT_ID";

    public Car() {
    }

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = CLIENT_ID, foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private Client client;
    @DatabaseField(columnName = SEGMENT_ID, foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private Segment segment;

    @DatabaseField(columnName = "TITLE", canBeNull = false)
    private String title;

    @DatabaseField(columnName = "DESCRIPTION")
    private String description;

    @DatabaseField(columnName = "RELEASE_DATE")
    private Date releaseDate;

    @DatabaseField(columnName = "VIN")
    private String vin;

    @DatabaseField(columnName = "DAYS", width = 1)
    private int days;

    @DatabaseField(columnName = "ADDED_DATE")
    private Date addedDate;
    
    @DatabaseField(columnName = "PRICE")
    private int price;
   
    @DatabaseField(columnName = "BASEPRICE")
    private int baseprice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
    
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    
    public int getBasePrice() {
        return baseprice;
    }

    public void setBasePrice(int baseprice) {
        this.baseprice = baseprice;
    }
    

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

