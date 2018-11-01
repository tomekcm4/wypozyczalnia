package pl.moja.wypozyczalnia.database.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import pl.moja.wypozyczalnia.database.models.Car;
import pl.moja.wypozyczalnia.utils.exceptions.ApplicationException;

import java.sql.SQLException;


public class CarDao extends CommonDao {

    public CarDao() {
        super();
    }

    public void deleteByColumnName(String columName, int id) throws ApplicationException, SQLException {
        Dao<Car, Object> dao = getDao(Car.class);
        DeleteBuilder<Car, Object> deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq(columName, id);
        dao.delete(deleteBuilder.prepare());
    }

}
