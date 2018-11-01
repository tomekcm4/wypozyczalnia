package pl.moja.wypozyczalnia.utils.converters;

import pl.moja.wypozyczalnia.database.models.Car;
import pl.moja.wypozyczalnia.modelFx.CarFx;
import pl.moja.wypozyczalnia.utils.Utils;


public class ConverterCar {

    public static Car convertToCar(CarFx carFx){
        Car car = new Car();
        car.setId(carFx.getId());
        car.setTitle(carFx.getTitle());
        car.setDescription(carFx.getDescription());
        car.setRating(carFx.getRating());
        car.setVin(carFx.getVin());
        car.setReleaseDate(Utils.convertToDate(carFx.getReleaseDate()));
        car.setAddedDate(Utils.convertToDate(carFx.getAddedDate()));
        return  car;
    }

    public static CarFx convertToCarFx(Car car){
        CarFx carFx = new CarFx();
        carFx.setId(car.getId());
        carFx.setTitle(car.getTitle());
        carFx.setDescription(car.getDescription());
        carFx.setRating(car.getRating());
        carFx.setVin(car.getVin());
        carFx.setReleaseDate(Utils.convertToLocalDate(car.getReleaseDate()));
        carFx.setClientFx(ConverterClient.convertToClientFx(car.getClient()));
        carFx.setSegmentFx(ConverterSegment.convertToSegmentFx(car.getSegment()));
        return carFx;
    }

}
