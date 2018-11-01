package pl.moja.wypozyczalnia.database.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "SEGMENTS")
public class Segment implements BaseModel {
    public Segment() {
    }

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "NAME", canBeNull = false, unique = true)
    private String name;

    @ForeignCollectionField(columnName = "CAR_ID")
    private ForeignCollection<Car> cars;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ForeignCollection<Car> getCars() {
        return cars;
    }

    public void setCars(ForeignCollection<Car> cars) {
        this.cars = cars;
    }
}
