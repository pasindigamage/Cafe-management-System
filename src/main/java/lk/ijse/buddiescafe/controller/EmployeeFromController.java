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
import lk.ijse.buddiescafe.repository.EmployeeRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeFromController {

    @FXML
    private JFXButton addEmployee;

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
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPosition;

    @FXML
    private JFXButton deleteEmployee;

    @FXML
    private TextField eAddress;

    @FXML
    private TextField eContact;

    @FXML
    private TextField eEmail;

    @FXML
    private TextField eID;

    @FXML
    private TextField eName;

    @FXML
    private TextField ePossition;

    @FXML
    private TableView<Employee> tblEmployee;

    @FXML
    private TextField eIDSearch;

    @FXML
    private JFXButton updateEmployee;

    @FXML
    private AnchorPane rootNode;

    private List<Employee> employeeList = new ArrayList<>();

    public void initialize(){

        //this.employeeList = getAllEmployee();
        setCellValueFactory();
        loadEmployeeTable();
    }


    private void loadEmployeeTable() {
        ObservableList<Employee> obList = FXCollections.observableArrayList();

        try {
            List<Employee> employeeList = EmployeeRepo.getAll();
            for (Employee employee : employeeList) {
                Employee tm = new Employee(
                        employee.getId(),
                        employee.getName(),
                        employee.getPosition(),
                        employee.getAddress(),
                        employee.getEmail(),
                        employee.getContact()
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
        //colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }


    private List<Employee> getAllEmployee() {
        List<Employee> employeeList = null;
        try {
            employeeList = EmployeeRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeList;
    }

    @FXML
    void IdSearchOnAction(ActionEvent event) {
        String id = eIDSearch.getText();

        try {
            Employee employee = EmployeeRepo.searchById(id);

            if (employee != null) {
                eID.setText(employee.getId());
                ePossition.setText(employee.getPosition());
                eName.setText(employee.getName());
                eAddress.setText(employee.getAddress());
                eEmail.setText(employee.getEmail());
                eContact.setText(employee.getContact());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String id = eID.getText();
        String name = eName.getText();
        String address = eAddress.getText();
        String contact = eContact.getText();
        String position =ePossition.getText();
        String email = eEmail.getText();

        Employee employee = new Employee(id,name,position,address,email,contact);

        try {
            boolean isSaved = EmployeeRepo.save(employee);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

   /* @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }
*/
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        eID.setText("");
        eAddress.setText("");
        eContact.setText("");
        eEmail.setText("");
        ePossition.setText("");
        eName.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = eID.getText();

        try {
            boolean isDeleted = EmployeeRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String idText = eID.getText();
        String nameText= eName.getText();
        String possitionText = ePossition.getText();
        String addressText = eAddress.getText();
        String emailText = eEmail.getText();
        String contactText = eContact.getText();

        Employee employee = new Employee(idText,nameText,possitionText,addressText,emailText,contactText);

        try {
            boolean isUpdated = EmployeeRepo.update(employee);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}