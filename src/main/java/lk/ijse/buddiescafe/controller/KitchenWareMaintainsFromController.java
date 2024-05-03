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
import lk.ijse.buddiescafe.model.KitchenWare;
import lk.ijse.buddiescafe.model.KitchenWareMaintains;
import lk.ijse.buddiescafe.model.Supplier;
import lk.ijse.buddiescafe.repository.InventoryRepo;
import lk.ijse.buddiescafe.repository.KitchenWareMaintainRepo;
import lk.ijse.buddiescafe.repository.KitchenWareRepo;
import lk.ijse.buddiescafe.repository.SupplierRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class KitchenWareMaintainsFromController {

    @FXML
    private Label lblDate;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private JFXButton addKitchenWareMaintains;

    @FXML
    private TextField amount;

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton clear;

    @FXML
    private JFXComboBox<String> cmbIKitchenWareId;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colKitchenWareId;

    @FXML
    private TableColumn<?, ?> colKitchenWareMaintainId;

    @FXML
    private TextField date;

    @FXML
    private JFXButton deleteKitchenWareMaintains;

    @FXML
    private TextField kmDescription;

    @FXML
    private TextField kmId;

    @FXML
    private Label lblNetTotal;

    @FXML
    private TableView<?> tblOrderCart;

    @FXML
    private JFXButton updateKitchenWareMaintains;

    public void initialize(){
        setDate();
        getKitchenWareIds();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String idText = kmId.getText();
        String kitchenWareIdValue = lblKId.getText();
        String descriptionText = kmDescription.getText();
        String dateText = lblDate.getText();
        String amountText = amount.getText();

        KitchenWareMaintains kitchenWareMaintains = new KitchenWareMaintains(idText,kitchenWareIdValue,descriptionText,dateText,amountText);

        try {
            boolean isSaved = KitchenWareMaintainRepo.save(kitchenWareMaintains);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "KitchenWare Maintain is Saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        kmId.setText("");
        kmDescription.setText("");
        amount.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = kmId.getText();

        try {
            boolean isDeleted = KitchenWareMaintainRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "KitchenWare Maintain is Deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private Label lblKId;

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String idText = kmId.getText();
        String kitchenWareIdValue = lblKId.getText();
        String descriptionText = kmDescription.getText();
        String dateText = lblDate.getText();
        String amountText = amount.getText();

        KitchenWareMaintains kitchenWareMaintains = new KitchenWareMaintains(idText,kitchenWareIdValue,descriptionText,dateText,amountText);

        try {
            boolean isUpdated = KitchenWareMaintainRepo.update(kitchenWareMaintains);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "KitchenWare Maintain is Updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void getKitchenWareIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = KitchenWareRepo.getIds();

            for (String id : idList) {
                obList.add(id);
            }
            cmbIKitchenWareId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) {
        String kid = cmbIKitchenWareId.getValue();
        try {
            KitchenWare kitchenWare = KitchenWareRepo.searchByCode(kid);
            if (kitchenWare != null) {
                lblKId.setText(kitchenWare.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
