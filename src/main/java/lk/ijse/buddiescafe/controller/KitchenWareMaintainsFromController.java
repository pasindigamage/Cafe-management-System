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
import lk.ijse.buddiescafe.model.KitchenWare;
import lk.ijse.buddiescafe.model.KitchenWareMaintains;
import lk.ijse.buddiescafe.repository.KitchenWareMaintainRepo;
import lk.ijse.buddiescafe.repository.KitchenWareRepo;
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
    private TableView<KitchenWareMaintains> tblOrderCart;

    @FXML
    private JFXButton updateKitchenWareMaintains;

    public void initialize(){
        setDate();
        getKitchenWareIds();
        loadInventoryTable();
        setCellValueFactory();
    }

    private void setCellValueFactory() {

        colKitchenWareMaintainId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colKitchenWareId.setCellValueFactory(new PropertyValueFactory<>("kitchenWareId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    private void loadInventoryTable() {
        ObservableList<KitchenWareMaintains> obList = FXCollections.observableArrayList();

        try {
            List<KitchenWareMaintains> kitchenWareMaintainsList = KitchenWareMaintainRepo.getAll();
            for (KitchenWareMaintains kitchenWareMaintains : kitchenWareMaintainsList) {
                KitchenWareMaintains tm = new KitchenWareMaintains(
                        kitchenWareMaintains.getId(),
                        kitchenWareMaintains.getKitchenWareId(),
                        kitchenWareMaintains.getDescription(),
                        kitchenWareMaintains.getDate(),
                        kitchenWareMaintains.getAmount()
                );

                obList.add(tm);
            }

            tblOrderCart.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
