package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.buddiescafe.model.OtherMaintains;
import lk.ijse.buddiescafe.repository.otherMaintainRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OtherMaintainsFromController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private Label lbldate;

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
    private TableView<OtherMaintains> tblOrderCart;

    @FXML
    private JFXButton updateOtherMaintains;

    public void initialize() {
        setCellValueFactory();
        loadCustomerTable();
        setDate();
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lbldate.setText(String.valueOf(now));
    }

    private void loadCustomerTable() {
        ObservableList<OtherMaintains> obList = FXCollections.observableArrayList();

        try {
            List<OtherMaintains> otherMaintainsList = otherMaintainRepo.getAll();
            for (OtherMaintains otherMaintains : otherMaintainsList) {
                OtherMaintains tm = new OtherMaintains(
                        otherMaintains.getId(),
                        otherMaintains.getDescription(),
                        otherMaintains.getDate(),
                        otherMaintains.getAmount()
                );

                obList.add(tm);
            }

            tblOrderCart.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colOtherMaintainId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String idText = omId.getText();
        String descriptionText = omDescription.getText();
        String dateText = date.getText();
        String amountText = amount.getText();

        OtherMaintains otherMaintains = new OtherMaintains(idText,descriptionText,dateText,amountText);

        try {
            boolean isSaved = otherMaintainRepo.save(otherMaintains);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Maintain is Saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/maintains.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        omId.setText("");
        omDescription.setText("");
        date.setText("");
        amount.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = omId.getText();

        try {
            boolean isDeleted = otherMaintainRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Maintain is Removed!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String idText = omId.getText();
        String descriptionText = omDescription.getText();
        String dateText= date.getText();
        String amountText = amount.getText();

        OtherMaintains otherMaintains = new OtherMaintains(idText,descriptionText,dateText,amountText);

        try {
            boolean isUpdated = otherMaintainRepo.update(otherMaintains);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Maintain is Updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

}
