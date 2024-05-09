package lk.ijse.buddiescafe.controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.buddiescafe.model.*;
import lk.ijse.buddiescafe.model.TM.InventoryTM;
import lk.ijse.buddiescafe.repository.InventoryRepo;
import lk.ijse.buddiescafe.repository.SupplierRepo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
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
    private TableColumn<?, ?> colInventoryId;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupplier;

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
    private TableView<InventoryTM> tblOrderCart;

    @FXML
    private TextField unitPrice;

    @FXML
    private JFXButton updateInventroySupplier;

    public void initialize() {
        setDate();
        getSupplierIds();
        loadInventoryTable();
        setCellValueFactory();
    }

    @FXML
    private Label lblsId;

    @FXML
    private TableColumn<?, ?> colSupName;

    private void setCellValueFactory() {
        colInventoryId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSupName.setCellValueFactory(new PropertyValueFactory<>("supName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }


    private void loadInventoryTable() {
        ObservableList<InventoryTM> obList = FXCollections.observableArrayList();

        try {
            List<InventoryTM> inventoryTMList = InventoryRepo.getAll();
            for (InventoryTM inventoryTM : inventoryTMList) {
                InventoryTM tm = new InventoryTM(
                        inventoryTM.getId(),
                        inventoryTM.getDescription(),
                        inventoryTM.getSupName(),
                        inventoryTM.getDate(),
                        inventoryTM.getUnitPrice(),
                        inventoryTM.getQty()
                );
                obList.add(tm);
            }
            tblOrderCart.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
    void btnAddOnAction(ActionEvent event) throws NumberFormatException, IllegalArgumentException {
        try {
            String idText = inventoryId.getText();
            String supplierIdValue = lblsId.getText();
            String descriptionText = Description.getText();
            Double unitPriceText = parseDouble(unitPrice.getText()); // Parse unitPrice
            int qtyText = Integer.parseInt(qty.getText());
            String dateText = lblOrderDate.getText();

            // Check if unitPrice is valid
            if (unitPriceText == null || unitPriceText <= 0) {
                throw new IllegalArgumentException("Invalid unit price.");
            }

            Inventory inventory = new Inventory(idText, supplierIdValue, descriptionText, unitPriceText, qtyText, dateText);

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
        inventoryIdSearch.setText("");
        cmbISupplierId.setValue("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = inventoryId.getText();

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
    void btnUpdateOnAction(ActionEvent event) throws NumberFormatException, IllegalArgumentException {
        try {
            String idText = inventoryId.getText();
            String supplierIdValue = lblsId.getText();
            String descriptionText = Description.getText();
            Double unitPriceText = parseDouble(unitPrice.getText()); // Parse unitPrice
            int qtyText = Integer.parseInt(qty.getText());
            String dateText = lblOrderDate.getText();

            // Check if unitPrice is valid
            if (unitPriceText == null || unitPriceText <= 0) {
                throw new IllegalArgumentException("Invalid unit price.");
            }

            Inventory inventory = new Inventory(idText, supplierIdValue, descriptionText, unitPriceText, qtyText, dateText);

            boolean isUpdated = InventoryRepo.update(inventory);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Inventory Updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private Double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null; // Return null if parsing fails
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
               // cmbISupplierId.setValue(inventory.getSupplierId());
                Description.setText(inventory.getDescription());
                unitPrice.setText(String.valueOf(inventory.getUnitPrice()));
                qty.setText(String.valueOf(inventory.getQty()));
                lblOrderDate.setText(String.valueOf(inventory.getDate()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}