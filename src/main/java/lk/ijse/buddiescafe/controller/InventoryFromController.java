package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.buddiescafe.model.Inventory;
import lk.ijse.buddiescafe.model.Supplier;
import lk.ijse.buddiescafe.repository.InventoryRepo;
import lk.ijse.buddiescafe.repository.SupplierRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


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
    private JFXComboBox<String> cmbISupplierId;

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
    private Label lblInventoryDetailId;

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
    private AnchorPane rootNode;

    public void initialize() {
        //setCellValueFactory();
        loadNextOrderId();
        setDate();
        getSupplierIds();
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
        String idText = lblInventoryDetailId.getText();
        String SupplierId = cmbISupplierId.getValue();
        String descriptionText = Description.getText();
        String unitPriceText = unitPrice.getText();
        String qtyText = qty.getText();
        String dateText = lblOrderDate.getText();

        Inventory inventory = new Inventory(idText,SupplierId,descriptionText,unitPriceText,qtyText,dateText);

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
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String idText = lblInventoryDetailId.getText();
        String supplierIdValue = cmbISupplierId.getValue();
        String descriptionText = Description.getText();
        String unitPriceText = unitPrice.getText();
        String qtyText = qty.getText();
        String dateText = lblOrderDate.getText();

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

    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblOrderDate.setText(String.valueOf(now));
    }

    private void loadNextOrderId() {
        try {
            String currentId = InventoryRepo.currentId();
            String nextId = nextId(currentId);

            lblInventoryDetailId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("O");
            int id = Integer.parseInt(split[1]);    //2
            return "I" + ++id;

        }
        return "I1";
    }
}
