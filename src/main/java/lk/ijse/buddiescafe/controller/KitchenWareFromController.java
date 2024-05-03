package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.buddiescafe.model.Inventory;
import lk.ijse.buddiescafe.model.KitchenWare;
import lk.ijse.buddiescafe.model.Supplier;
import lk.ijse.buddiescafe.repository.InventoryRepo;
import lk.ijse.buddiescafe.repository.KitchenWareRepo;
import lk.ijse.buddiescafe.repository.SupplierRepo;

import java.sql.SQLException;
import java.util.List;

public class KitchenWareFromController {

    @FXML
    private TextField Description;

    @FXML
    private TextField Qty;

    @FXML
    private JFXButton addKitchenWare;

    @FXML
    private JFXButton clear;

    @FXML
    private JFXComboBox<String> cmbISupplierId;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colKitchenWareId;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private JFXButton deleteKitchenWare;

    @FXML
    private TextField kId;

    @FXML
    private Label lblNetTotal;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<?> tblOrderCart;

    @FXML
    private JFXButton updateKitchenWare;

    public void initialize() {
        getSupplierIds();
        // loadInventoryTable();
        // setCellValueFactory();
    }

    private void getSupplierIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = SupplierRepo.getIds();

            for (String id : idList) {
                obList.add(id);
            }
            cmbISupplierId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private TextField kitchenWareIdSearch;

    @FXML
    void IdSearchOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String idText = kId.getText();
        String cmbISupplierIdValue = lblsId.getText();
        String descriptionText = Description.getText();
        String qtyText = Qty.getText();
        KitchenWare kitchenWare = new KitchenWare(idText,cmbISupplierIdValue,descriptionText,qtyText);

        try {
            boolean isSaved = KitchenWareRepo.save(kitchenWare);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "KitchenWare is Saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        kId.setText("");
        Description.setText("");
        cmbISupplierId.setValue("");
        Qty.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = kId.getText();

        try {
            boolean isDeleted = KitchenWareRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "KitchenWare Deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String idText = kId.getText();
        String supplierIdValue = lblsId.getText();
        String descriptionText = Description.getText();
        String qtyText = Qty.getText();

        KitchenWare kitchenWare = new KitchenWare(idText,supplierIdValue,descriptionText,qtyText);

        try {
            boolean isUpdated = KitchenWareRepo.update(kitchenWare);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "KitchenWare is Updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private Label lblsId;

    @FXML
    void cmbItemOnAction(ActionEvent event) {
        String sid = cmbISupplierId.getValue();
        try {
            Supplier supplier = SupplierRepo.searchByCode(sid);
            if (supplier != null) {
                lblsId.setText(supplier.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
