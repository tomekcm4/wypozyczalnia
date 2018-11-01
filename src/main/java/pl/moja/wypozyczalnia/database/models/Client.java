package pl.moja.wypozyczalnia.database.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "CLIENTS")
public class Client implements BaseModel{
    public Client() {
    }

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "NAME", canBeNull = false)
    private String name;

    @DatabaseField(columnName = "SURNAME", canBeNull = false)
    private String surname;

    @ForeignCollectionField(eager = true)
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public ForeignCollection<Car> getCars() {
        return cars;
    }

    public void setBooks(ForeignCollection<Car> cars) {
        this.cars = cars;
    }
}
