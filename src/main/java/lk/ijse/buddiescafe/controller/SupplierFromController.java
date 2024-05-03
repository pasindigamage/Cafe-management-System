package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.buddiescafe.model.Employee;
import lk.ijse.buddiescafe.model.Supplier;
import lk.ijse.buddiescafe.repository.SupplierRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
    private TableView<Supplier> tblEmployee;

    @FXML
    private JFXButton updateSuppler;

    public void initialize(){
        setCellValueFactory();
        loadEmployeeTable();
    }

    private void loadEmployeeTable() {
        ObservableList<Supplier> obList = FXCollections.observableArrayList();

        try {
            List<Supplier> supplierList = SupplierRepo.getAll();
            for (Supplier supplier : supplierList) {
                Supplier tm = new Supplier(
                        supplier.getId(),
                        supplier.getNic(),
                        supplier.getName(),
                        supplier.getCompanyAddress(),
                        supplier.getEmail(),
                        supplier.getContact()
                );

                obList.add(tm);
            }

            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("companyAddress"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    @FXML
    void IdSearchOnAction(ActionEvent event) {
        String id = sID.getText();

        try {
            Supplier supplier = SupplierRepo.searchById(id);

            if (supplier != null) {
                sID.setText(supplier.getId());
                sNIC.setText(supplier.getNic());
                sName.setText(supplier.getName());
                sAddress.setText(supplier.getCompanyAddress());
                sEmail.setText(supplier.getEmail());
                sEmail.setText(supplier.getContact());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
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
        String id = sID.getText();

        try {
            boolean isDeleted = SupplierRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
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
