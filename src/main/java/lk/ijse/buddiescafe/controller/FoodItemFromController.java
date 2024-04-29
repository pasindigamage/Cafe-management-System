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
import lk.ijse.buddiescafe.model.FoodItems;
import lk.ijse.buddiescafe.model.Supplier;
import lk.ijse.buddiescafe.repository.EmployeeRepo;
import lk.ijse.buddiescafe.repository.FoodItemsRepo;
import lk.ijse.buddiescafe.repository.SupplierRepo;

import java.io.IOException;
import java.sql.SQLException;

public class FoodItemFromController {

    @FXML
    private JFXButton addIngredians;

    @FXML
    private JFXButton addMenu;

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton clear;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private JFXButton deleteMenu;

    @FXML
    private TextField fAmount;

    @FXML
    private TextField fDescription;

    @FXML
    private TextField fID;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<?> tblMenu;

    @FXML
    private JFXButton updateMenu;

    @FXML
    void IdSearchOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String idText = fID.getText();
        String descriptionText = fDescription.getText();
        String amountText = fAmount.getText();

       FoodItems foodItems = new FoodItems(idText,descriptionText,amountText);

        try {
            boolean isSaved = FoodItemsRepo.save(foodItems);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Menu Item is Saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnAnchorpaneChnageOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/addIngredians.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
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
        fID.setText("");
        fDescription.setText("");
        fAmount.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = fID.getText();

        try {
            boolean isDeleted = FoodItemsRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Menu Item is Deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String idText = fID.getText();
        String descriptionText = fDescription.getText();
        String amountText = fAmount.getText();

        FoodItems foodItems = new FoodItems(idText,descriptionText,amountText);

        try {
            boolean isUpdated = FoodItemsRepo.update(foodItems);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Menu Item is Updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
