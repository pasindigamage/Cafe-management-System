package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainDashboardFromController {

    @FXML
    private JFXButton employee;

    @FXML
    private JFXButton inventory;

    @FXML
    private JFXButton kitchenware;

    @FXML
    private JFXButton logout;

    @FXML
    private AnchorPane mainDashboard;

    @FXML
    private JFXButton maintain;

    @FXML
    private JFXButton menu;

    @FXML
    private JFXButton order;

    @FXML
    private AnchorPane subDashboard;

    @FXML
    private JFXButton supplier;

    @FXML
    private Label time;

    @FXML
    void employeeOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/employee.fxml"));
        Parent rootNode = loader.load();
        subDashboard.getChildren().clear();
        subDashboard.getChildren().add(rootNode);
    }

    @FXML
    void inventoryOnAction(ActionEvent event) {

    }

    @FXML
    void kitchenwareOnAction(ActionEvent event) {

    }

    @FXML
    void logoutOnAction(ActionEvent event) {

    }

    @FXML
    void maintainOnAction(ActionEvent event) {

    }

    @FXML
    void menuOnAction(ActionEvent event) {

    }

    @FXML
    void orderOnAction(ActionEvent event) {

    }

    @FXML
    void supplierOnAction(ActionEvent event) {

    }

}
