package pl.moja.wypozyczalnia.modelFx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import pl.moja.wypozyczalnia.database.dao.CarDao;
import pl.moja.wypozyczalnia.database.dao.SegmentDao;
import pl.moja.wypozyczalnia.database.models.Car;
import pl.moja.wypozyczalnia.database.models.Segment;
import pl.moja.wypozyczalnia.utils.converters.ConverterSegment;
import pl.moja.wypozyczalnia.utils.exceptions.ApplicationException;

import java.sql.SQLException;
import java.util.List;


public class SegmentModel {

    private ObservableList<SegmentFx> segmentList = FXCollections.observableArrayList();
    private ObjectProperty<SegmentFx> segment = new SimpleObjectProperty<>();
    private TreeItem<String> root = new TreeItem<>();


    public void init() throws ApplicationException {
        SegmentDao segmentDao = new SegmentDao();
        List<Segment> segments = segmentDao.queryForAll(Segment.class);
        initSegmentList(segments);
        initRoot(segments);
    }

    private void initRoot(List<Segment> segments) {
        this.root.getChildren().clear();
        segments.forEach(c->{
            TreeItem<String> segmentItem = new TreeItem<>(c.getName());
            c.getCars().forEach(b->{
                segmentItem.getChildren().add(new TreeItem<>(b.getTitle()));
            });
            root.getChildren().add(segmentItem);
        });
    }

    private void initSegmentList(List<Segment> segments) {
        this.segmentList.clear();
        segments.forEach(c -> {
            SegmentFx segmentFx = ConverterSegment.convertToSegmentFx(c);
            this.segmentList.add(segmentFx);
        });
    }

    public void deleteSegmentById() throws ApplicationException, SQLException {
        SegmentDao segmentDao = new SegmentDao();
        segmentDao.deleteById(Segment.class, segment.getValue().getId());
        CarDao carDao = new CarDao();
        carDao.deleteByColumnName(Car.SEGMENT_ID, segment.getValue().getId());
        init();
    }

    public void saveSegmentInDataBase(String name) throws ApplicationException {
        SegmentDao segmentDao = new SegmentDao();
        Segment segment = new Segment();
        segment.setName(name);
        segmentDao.creatOrUpdate(segment);
        init();
    }

    public void updateSegmentInDataBase() throws ApplicationException {
        SegmentDao segmentDao = new SegmentDao();
        Segment tempSegment = segmentDao.findById(Segment.class, getSegment().getId());
        tempSegment.setName(getSegment().getName());
        segmentDao.creatOrUpdate(tempSegment);
        init();
    }

    public ObservableList<SegmentFx> getSegmentList() {
        return segmentList;
    }

    public void setSegmentList(ObservableList<SegmentFx> segmentList) {
        this.segmentList = segmentList;
    }

    public SegmentFx getSegment() {
        return segment.get();
    }

    public ObjectProperty<SegmentFx> segmentProperty() {
        return segment;
    }

    public void setSegment(SegmentFx segment) {
        this.segment.set(segment);
    }

    public TreeItem<String> getRoot() {
        return root;
    }

    public void setRoot(TreeItem<String> root) {
        this.root = root;
    }
}
