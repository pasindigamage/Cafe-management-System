package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.buddiescafe.model.KitchenWare;
import lk.ijse.buddiescafe.model.Supplier;
import lk.ijse.buddiescafe.repository.FoodItemsRepo;
import lk.ijse.buddiescafe.repository.KitchenWareRepo;
import lk.ijse.buddiescafe.repository.SupplierRepo;
import lk.ijse.buddiescafe.util.Regex;

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
    private Label kId;

    @FXML
    private Label lblNetTotal;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<KitchenWare> tblOrderCart;

    @FXML
    private JFXButton updateKitchenWare;

    public void initialize() {
        getSupplierIds();
        //     loadInventoryTable();
        setCellValueFactory();
        loadNextOrderId();

        Description.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Qty.requestFocus();
            }
        });
        tblOrderCart.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Description.setText(newSelection.getDescription());
                Qty.setText(newSelection.getQty());
                cmbISupplierId.setValue(newSelection.getSupplierId());

            }
        });
    }

    private void loadNextOrderId() {
        try {
            String currentId = KitchenWareRepo.currentId();
            String nextId = nextId(currentId);

            kId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("KW");
            int idNum = Integer.parseInt(split[1]);
            idNum++;
            return "KW" + String.format("%03d", idNum);
        }
        return "KW001";

    }

    private void setCellValueFactory() {

        colKitchenWareId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    private void loadInventoryTable() {
        ObservableList<KitchenWare> obList = FXCollections.observableArrayList();

        try {
            List<KitchenWare> kitchenWareList = KitchenWareRepo.getAll();
            for (KitchenWare kitchenWare : kitchenWareList) {
                KitchenWare tm = new KitchenWare(
                        kitchenWare.getId(),
                        kitchenWare.getSupplierId(),
                        kitchenWare.getDescription(),
                        kitchenWare.getQty()
                );

                obList.add(tm);
            }

            tblOrderCart.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        String id = kitchenWareIdSearch.getText();

        try {
            KitchenWare kitchenWare = KitchenWareRepo.searchByCode(id);

            if (kitchenWare != null) {
                kId.setText(kitchenWare.getId());
                Description.setText(kitchenWare.getDescription());
                Qty.setText(kitchenWare.getQty());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
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
                loadNextOrderId();
                clearFields();
                loadInventoryTable();
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
        Description.setText("");
        cmbISupplierId.setValue("");
        Qty.setText("");
        kitchenWareIdSearch.setText("");
        cmbISupplierId.setValue("");
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
    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.qty,Qty)) return false;
        //  if (!) return false;
        //  if (!) return false;
        //  if (!) return false;

        return true;
    }
    @FXML
    void txtQtyOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.qty,Qty);
    }
}
