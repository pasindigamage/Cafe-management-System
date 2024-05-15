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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.buddiescafe.model.Employee;
import lk.ijse.buddiescafe.model.Supplier;
import lk.ijse.buddiescafe.repository.EmployeeRepo;
import lk.ijse.buddiescafe.repository.SupplierRepo;
import lk.ijse.buddiescafe.util.Regex;

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
    private Label sID;

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
        loadNextOrderId();

            sNIC.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sName.requestFocus();
            }
        });
        sName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sAddress.requestFocus();
            }
        });

        sAddress.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sContact.requestFocus();
            }
        });

        sContact.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sEmail.requestFocus();
            }
        });

    }

    private void loadNextOrderId() {
        try {
            // Check if sID label is properly initialized
            if (sID != null) {
                String currentId = SupplierRepo.currentId();
                String nextId = nextId(currentId);

                // Update the text of the sID label
                sID.setText(nextId);
            } else {
                // Log an error or display a message if sID is null
                System.err.println("Error: sID label is null.");
            }
        } catch (SQLException e) {
            // Handle SQL exceptions appropriately
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("S00");
            int id = Integer.parseInt(split[1]);    //2
            return "S00" + ++id;

        }
        return "S001";
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
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("companyAddress"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    @FXML
    private TextField sIDSearch;

    @FXML
    void IdSearchOnAction(ActionEvent event) {
        String id = sIDSearch.getText();

        try {
            Supplier supplier = SupplierRepo.searchById(id);

            if (supplier != null) {
                sID.setText(supplier.getId());
                sNIC.setText(supplier.getNic());
                sName.setText(supplier.getName());
                sAddress.setText(supplier.getCompanyAddress());
                sEmail.setText(supplier.getEmail());
                sContact.setText(String.valueOf(supplier.getContact()));
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
                loadNextOrderId();
                clearFields();
                loadEmployeeTable();
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
        sNIC.setText("");
        sName.setText("");
        sAddress.setText("");
        sEmail.setText("");
        sContact.setText("");
        sIDSearch.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = sID.getText();

        try {
            boolean isDeleted = SupplierRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Deleted!").show();
                loadNextOrderId();
                loadEmployeeTable();
                clearFields();
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
                clearFields();
                loadEmployeeTable();
                loadNextOrderId();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtAddressOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.address,sAddress);
    }

    @FXML
    void txtContactOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.contact,sContact);
    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.email,sEmail);
    }

    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.name,sName);
    }

    @FXML
    void txtNicOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.nic,sNIC);
    }
}
