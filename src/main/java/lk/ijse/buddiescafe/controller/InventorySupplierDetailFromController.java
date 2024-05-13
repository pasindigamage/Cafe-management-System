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
import lk.ijse.buddiescafe.model.*;
import lk.ijse.buddiescafe.model.TM.InventorySupplierDetailTM;
import lk.ijse.buddiescafe.repository.*;
import lk.ijse.buddiescafe.util.Regex;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class InventorySupplierDetailFromController {

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
    private Label inventoryId;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private TextField qty;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<InventorySupplierDetailTM> tblOrderCart;

    @FXML
    private TextField unitPrice;

    @FXML
    private JFXButton updateInventroySupplier;

    public void initialize() {
        setDate();
        getSupplierIds();
        getInventoryIds();
        loadInventoryTable();
        setCellValueFactory();
        loadNextOrderId();


        unitPrice.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                qty.requestFocus();
            }
        });
    }

    private void getInventoryIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = FoodItemsRepo.getIds();

            for (String id : idList) {
                obList.add(id);
            }
            cmbIInventoryId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private JFXComboBox<String> cmbIInventoryId;

    @FXML
    private Label lblsId;

    @FXML
    private Label lblInventoryId;

    @FXML
    void cmbInventoryOnAction(ActionEvent event) {
        String sid = cmbIInventoryId.getValue();
        try {
            FoodItems foodItems = FoodItemsRepo.searchByCode(sid);
            if (foodItems != null) {
                lblInventoryId.setText(foodItems.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadNextOrderId() {
        try {
            String currentId = InventorySupplierDetailRepo.currentId();
            String nextId = nextId(currentId);

            inventoryId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("IS00");
            int id = Integer.parseInt(split[1]);    //2
            return "IS00" + ++id;

        }
        return "IS001";
    }


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
        ObservableList<InventorySupplierDetailTM> obList = FXCollections.observableArrayList();

        try {
            List<InventorySupplierDetailTM> inventoryTMList = InventorySupplierDetailRepo.getAll();
            for (InventorySupplierDetailTM inventoryTM : inventoryTMList) {
                InventorySupplierDetailTM tm = new InventorySupplierDetailTM(
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
            String ingrediansValue = lblInventoryId.getText();
            //  String descriptionText = Description.getText();
            String dateText = lblOrderDate.getText();
            Double unitPriceText = parseDouble(unitPrice.getText()); // Parse unitPrice
            int qtyText = Integer.parseInt(qty.getText());

            // Check if unitPrice is valid
            if (unitPriceText == null || unitPriceText <= 0) {
                throw new IllegalArgumentException("Invalid unit price.");
            }

            InventorySupplierDetail inventoryDetail = new InventorySupplierDetail(idText, supplierIdValue, ingrediansValue, dateText, unitPriceText, qtyText);

            boolean isSaved = InventorySupplierDetailRepo.save(inventoryDetail);
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
        unitPrice.setText("");
        qty.setText("");
        inventoryIdSearch.setText("");
        cmbISupplierId.setValue("");
        cmbIInventoryId.setValue("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = inventoryId.getText();

        try {
            boolean isDeleted = InventorySupplierDetailRepo.delete(id);
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
            String ingrediansValue = lblInventoryId.getText();
            //String descriptionText = Description.getText();
            Double unitPriceText = parseDouble(unitPrice.getText()); // Parse unitPrice
            int qtyText = Integer.parseInt(qty.getText());
            String dateText = lblOrderDate.getText();

            // Check if unitPrice is valid
            if (unitPriceText == null || unitPriceText <= 0) {
                throw new IllegalArgumentException("Invalid unit price.");
            }

            InventorySupplierDetail inventoryDetail = new InventorySupplierDetail(idText, supplierIdValue, ingrediansValue, dateText, unitPriceText, qtyText);

            boolean isUpdated = InventorySupplierDetailRepo.update(inventoryDetail);
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
            InventorySupplierDetail inventoryDetail = InventorySupplierDetailRepo.searchByID(id);

            if (inventoryIdSearch != null) {
                inventoryId.setText(inventoryDetail.getId());
                //Description.setText(inventoryDetail.getInventoryId());
                unitPrice.setText(String.valueOf(inventoryDetail.getUnitPrice()));
                qty.setText(String.valueOf(inventoryDetail.getQty()));
                lblOrderDate.setText(String.valueOf(inventoryDetail.getDate()));
                lblsId.setText(inventoryDetail.getSupplierId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtQtyOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.qty, qty);
    }

    @FXML
    void txtUnitPriceOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.amount, unitPrice);
    }
}