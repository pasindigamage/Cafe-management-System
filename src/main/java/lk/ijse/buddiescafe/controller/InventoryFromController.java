package lk.ijse.buddiescafe.controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.buddiescafe.model.FoodItems;
import lk.ijse.buddiescafe.model.Inventory;
import lk.ijse.buddiescafe.model.Supplier;
import lk.ijse.buddiescafe.repository.FoodItemsRepo;
import lk.ijse.buddiescafe.repository.InventoryRepo;
import lk.ijse.buddiescafe.repository.SupplierRepo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class InventoryFromController {

    @FXML
    private TextField Description;

    @FXML
    private JFXButton addInventorySupplier;

    @FXML
    private JFXButton clear;

    @FXML
    private JFXComboBox<String> cmbISupplierId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colInventroyCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private JFXButton deleteInventorySupplier;

    @FXML
    private TextField inventoryId;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private TextField qty;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<Inventory> tblOrderCart;

    @FXML
    private TextField unitPrice;

    @FXML
    private JFXButton updateInventroySupplier;

    public void initialize() {
        setDate();
        getSupplierIds();
       // loadInventoryTable();
       // setCellValueFactory();
    }
    @FXML
    private Label lblsId;

    /*private void loadInventoryTable() {
        ObservableList<Inventory> obList = FXCollections.observableArrayList();

        try {
            List<Inventory> inventoryList = InventoryRepo.getAll();
            for (Inventory inventory : inventoryList) {
                Inventory tm = new Inventory(
                        inventory.getId(),
                        inventory.getSupplierId(),
                        inventory.getDescription(),
                        inventory.getUnitPrice(),
                        inventory.getQty(),
                        inventory.getDate()
                );

                obList.add(tm);
            }

            tblOrderCart.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colInventroyCode.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }*/

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblOrderDate.setText(String.valueOf(now));
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
    void btnAddOnAction(ActionEvent event) {
        String idText = inventoryId.getText();
        String supplierIdValue = lblsId.getText();
        String descriptionText = Description.getText();
        String unitPriceText = unitPrice.getText();
        String qtyText = qty.getText();
        String dateText =lblOrderDate.getText();

        Inventory inventory = new Inventory(idText,supplierIdValue,descriptionText,unitPriceText,qtyText,dateText);

        try {
            boolean isSaved = InventoryRepo.save(inventory);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Inventory Saved!").show();
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
        inventoryId.setText("");
        Description.setText("");
        unitPrice.setText("");
        qty.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = lblsId.getText();

        try {
            boolean isDeleted = InventoryRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Inventory Deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String idText = lblsId.getText();
        String supplierIdValue = cmbISupplierId.getValue();
        String descriptionText = Description.getText();
        String unitPriceText = unitPrice.getText();
        String qtyText = qty.getText();
        String dateText =lblOrderDate.getText();

        Inventory inventory = new Inventory(idText,supplierIdValue,descriptionText,unitPriceText,qtyText,dateText);

        try {
            boolean isUpdated = InventoryRepo.update(inventory);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Inventory Updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void cmbSupOnAction(ActionEvent event) {
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

    @FXML
    private TextField inventoryIdSearch;

    public void IdSearchOnAction(ActionEvent actionEvent) {
        String id = inventoryIdSearch.getText();

        try {
            Inventory inventory = InventoryRepo.searchByID(id);

            if (inventoryIdSearch != null) {
                inventoryId.setText(inventory.getId());
                cmbISupplierId.setValue(inventory.getSupplierId());
                Description.setText(inventory.getDescription());
                unitPrice.setText(inventory.getUnitPrice());
                qty.setText(inventory.getQty());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
