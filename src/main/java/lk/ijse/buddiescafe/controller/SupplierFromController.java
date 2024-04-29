package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.buddiescafe.model.Employee;
import lk.ijse.buddiescafe.model.Supplier;
import lk.ijse.buddiescafe.repository.EmployeeRepo;
import lk.ijse.buddiescafe.repository.SupplierRepo;

import java.io.IOException;
import java.sql.SQLException;

public class SupplierFromController {

    @FXML
    private JFXButton addSupplier;

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton clear;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colMail;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private JFXButton deleteSupplier;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField sAddress;

    @FXML
    private TextField sContact;

    @FXML
    private TextField sEmail;

    @FXML
    private TextField sID;

    @FXML
    private TextField sNIC;

    @FXML
    private TextField sName;

    @FXML
    private TableView<?> tblEmployee;

    @FXML
    private JFXButton updateSuppler;

    @FXML
    void IdSearchOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String idText = sID.getText();
        String nicText = sNIC.getText();
        String nameText = sName.getText();
        String addressText = sAddress.getText();
        String emailText = sEmail.getText();
        String contactText =sContact.getText();

        Supplier supplier = new Supplier(idText,nicText,nameText,addressText,emailText,contactText);

        try {
            boolean isSaved = SupplierRepo.save(supplier);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Saved!").show();
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
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        sID.setText("");
        sNIC.setText("");
        sName.setText("");
        sAddress.setText("");
        sEmail.setText("");
        sContact.setText("");

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String idText = sID.getText();
        String nicText = sNIC.getText();
        String nameText = sName.getText();
        String addressText = sAddress.getText();
        String emailText = sEmail.getText();
        String contactText = sContact.getText();

        Supplier supplier = new Supplier(idText,nicText,nameText,addressText,emailText,contactText);

        try {
            boolean isUpdated = SupplierRepo.update(supplier);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
