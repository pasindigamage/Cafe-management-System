package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;

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
    public void initialize(){
        setGreetings();
    }

    private void setGreetings() {
        LocalTime currentTime = LocalTime.now();
        String greeting = (currentTime.getHour() < 12) ? "Good Morning !!!" : "Good Evening !!!";
        time.setText(greeting);
    }

    @FXML
    void employeeOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/employee.fxml"));
        Pane registerPane = fxmlLoader.load();
        subDashboard.getChildren().clear();
        subDashboard.getChildren().add(registerPane);
    }

    @FXML
    void inventoryOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/inventory.fxml"));
        Pane registerPane = fxmlLoader.load();
        subDashboard.getChildren().clear();
        subDashboard.getChildren().add(registerPane);
    }

    @FXML
    void kitchenwareOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/kitchenWare.fxml"));
        Pane registerPane = fxmlLoader.load();
        subDashboard.getChildren().clear();
        subDashboard.getChildren().add(registerPane);
    }

    @FXML
    void logoutOnAction(ActionEvent event) {
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
    }

    @FXML
    void maintainOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/maintains.fxml"));
        Pane registerPane = fxmlLoader.load();
        subDashboard.getChildren().clear();
        subDashboard.getChildren().add(registerPane);
    }

    @FXML
    void menuOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/foodItem.fxml"));
        Pane registerPane = fxmlLoader.load();
        subDashboard.getChildren().clear();
        subDashboard.getChildren().add(registerPane);
    }

    @FXML
    void orderOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/order.fxml"));
        Pane registerPane = fxmlLoader.load();
        subDashboard.getChildren().clear();
        subDashboard.getChildren().add(registerPane);
    }

    @FXML
    void supplierOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/suppliers.fxml"));
        Pane registerPane = fxmlLoader.load();
        subDashboard.getChildren().clear();
        subDashboard.getChildren().add(registerPane);
    }

    public void homeOnAction(ActionEvent actionEvent) throws IOException {
        mainDashboard.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/mainDashboard.fxml"));
        Stage stage = (Stage) mainDashboard.getScene().getWindow();
        stage.setScene(new Scene(anchorPane));
    }

}