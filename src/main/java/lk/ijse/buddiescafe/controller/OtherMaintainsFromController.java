package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class OtherMaintainsFromController {

    @FXML
    private JFXButton addOtherMaintains;

    @FXML
    private TextField amount;

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton clear;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colOtherMaintainId;

    @FXML
    private TextField date;

    @FXML
    private JFXButton deleteOtherMaintains;

    @FXML
    private Label lblNetTotal;

    @FXML
    private TextField omDescription;

    @FXML
    private TextField omId;

    @FXML
    private TableView<?> tblOrderCart;

    @FXML
    private JFXButton updateOtherMaintains;

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

}
