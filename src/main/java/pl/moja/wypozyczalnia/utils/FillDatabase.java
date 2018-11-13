package pl.moja.wypozyczalnia.utils;

import pl.moja.wypozyczalnia.database.dao.CarDao;
import pl.moja.wypozyczalnia.database.dao.SegmentDao;
import pl.moja.wypozyczalnia.database.dbuitls.DbManager;
import pl.moja.wypozyczalnia.database.models.Car;
import pl.moja.wypozyczalnia.database.models.Client;
import pl.moja.wypozyczalnia.database.models.Segment;
import pl.moja.wypozyczalnia.utils.exceptions.ApplicationException;

import java.util.Date;


public class FillDatabase {
    public static  void fillDatabase(){
        Segment segment1 = new Segment();
        segment1.setName("Sedan");
        Client client1 = new Client();
        client1.setName("Adam");
        client1.setSurname("Wisniewski");
        Car car1 = new Car();
        car1.setSegment(segment1);
        car1.setClient(client1);
        car1.setTitle("BMW 5 520");
        car1.setVin("K3322KF234SD88");
        car1.setPrice(200);
        car1.setBasePrice(30);
        car1.setDays(4);
        car1.setReleaseDate(new Date());
        car1.setAddedDate(new Date());
        car1.setDescription("rocznik 2015, 2.5 290 KM benzyna");


        Segment segment2 = new Segment();
        segment2.setName("Hatchback");
        SegmentDao segmentDao = new SegmentDao();
        try {
            segmentDao.creatOrUpdate(segment2);
            DbManager.closeConnectionSource();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }


        Segment segment3 = new Segment();
        segment3.setName("Kombi");
        Client client2 = new Client();
        client2.setName("Mariusz");
        client2.setSurname("Kowalski");
        Car car2 = new Car();
        car2.setSegment(segment3);
        car2.setClient(client2);
        car2.setTitle("Opel Insignia");
        car2.setVin("13322DK334SD88");
        car2.setPrice(300);
        car2.setBasePrice(300);
        car2.setDays(5);
        car2.setReleaseDate(new Date());
        car2.setAddedDate(new Date());
        car2.setDescription("Rocznik 2016, 2.0 190 KM diesel");

        Segment segment4 = new Segment();
        segment4.setName("Kabriolet");
        Client client3 = new Client();
        client3.setName("Michal");
        client3.setSurname("Brzoza");
        Car car3 = new Car();
        car3.setSegment(segment4);
        car3.setClient(client3);
        car3.setTitle("Ford Focus");
        car3.setVin("93822KF23K7S78");
        car3.setPrice(400);
        car3.setBasePrice(300);
        car3.setDays(5);
        car3.setReleaseDate(new Date());
        car3.setAddedDate(new Date());
        car3.setDescription("Rocznik 2018, 1.8 200 KM benzyna");

        Client client4 = new Client();
        client4.setName("Kamil ");
        client4.setSurname("Kot");
        Car car4 = new Car();
        car4.setSegment(segment4);
        car4.setClient(client4);
        car4.setTitle("Renault Megane");
        car4.setVin("1L32KF234SD30");
        car4.setPrice(600);
        car4.setBasePrice(300);
        car4.setDays(3);
        car4.setReleaseDate(new Date());
        car4.setAddedDate(new Date());
        car4.setDescription("Rocznika 2013, 2.0 150 KM benzyna");

        CarDao carDao = new CarDao();
        try {
            carDao.creatOrUpdate(car1);
            carDao.creatOrUpdate(car2);
            carDao.creatOrUpdate(car3);
            carDao.creatOrUpdate(car4);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        DbManager.closeConnectionSource();
    }
}
