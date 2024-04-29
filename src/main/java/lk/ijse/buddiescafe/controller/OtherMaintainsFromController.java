package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.buddiescafe.model.OtherMaintains;
import lk.ijse.buddiescafe.model.Supplier;
import lk.ijse.buddiescafe.repository.SupplierRepo;
import lk.ijse.buddiescafe.repository.otherMaintainRepo;

import java.io.IOException;
import java.sql.SQLException;

public class OtherMaintainsFromController {

    @FXML
    private AnchorPane rootNode;

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
    private TableView<?> tblOrderCart;

    @FXML
    private JFXButton updateOtherMaintains;

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
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Saved!").show();
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
        colOtherMaintainId.setText("");
        colDescription.setText("");
        colDate.setText("");
        colAmount.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

}
