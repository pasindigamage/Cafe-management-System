package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class InventoryFromController {

    @FXML
    private TextField Description;

    @FXML
    private JFXButton addInventorySupplier;

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton clear;

    @FXML
    private JFXComboBox<?> cmbISupplierId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colInventroyCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private JFXButton deleteInventorySupplier;

    @FXML
    private Label lblInventorySupplierId;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private TextField qty;

    @FXML
    private TableView<?> tblOrderCart;

    @FXML
    private TextField unitPrice;

    @FXML
    private JFXButton updateInventroySupplier;

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

    @FXML
    void cmbSupOnAction(ActionEvent event) {

    }

}
