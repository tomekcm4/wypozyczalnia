package pl.moja.wypozyczalnia.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.moja.wypozyczalnia.modelFx.SegmentFx;
import pl.moja.wypozyczalnia.modelFx.SegmentModel;
import pl.moja.wypozyczalnia.utils.DialogsUtils;
import pl.moja.wypozyczalnia.utils.exceptions.ApplicationException;

import java.sql.SQLException;


public class SegmentController {

    @FXML
    private Button addSegmentButton;
    @FXML
    private Button deleteSegmentButton;
    @FXML
    private Button editSegmentButton;
    @FXML
    private TextField segmentTextField;
    @FXML
    private ComboBox<SegmentFx> segmentComboBox;
    @FXML
    private TreeView<String> segmentTreeView;

    private SegmentModel segmentModel;

    @FXML
    public void initialize() {
        this.segmentModel = new SegmentModel();
        try {
            this.segmentModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        this.segmentComboBox.setItems(this.segmentModel.getSegmentList());
        this.segmentTreeView.setRoot(this.segmentModel.getRoot());
        initBindings();
    }

    private void initBindings() {
        this.addSegmentButton.disableProperty().bind(segmentTextField.textProperty().isEmpty());
        this.deleteSegmentButton.disableProperty().bind(this.segmentModel.segmentProperty().isNull());
        this.editSegmentButton.disableProperty().bind(this.segmentModel.segmentProperty().isNull());
    }


    public void addSegmentOnAction() {
        try {
            segmentModel.saveSegmentInDataBase(segmentTextField.getText());
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        segmentTextField.clear();
    }

    public void onActionDeleteButton() {
        try {
            this.segmentModel.deleteSegmentById();
        } catch (ApplicationException | SQLException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    public void onActionComboBox() {
        this.segmentModel.setSegment(this.segmentComboBox.getSelectionModel().getSelectedItem());
    }

    public void onActionEditSegment() {
        String newSegmentName = DialogsUtils.editDialog(this.segmentModel.getSegment().getName());
        if(newSegmentName!=null){
            this.segmentModel.getSegment().setName(newSegmentName);
            try {
                this.segmentModel.updateSegmentInDataBase();
            } catch (ApplicationException e) {
                DialogsUtils.errorDialog(e.getMessage());
            }
        }

    }
}
