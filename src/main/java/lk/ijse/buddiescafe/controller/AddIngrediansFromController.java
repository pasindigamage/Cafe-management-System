package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lk.ijse.buddiescafe.repository.AddIngrediansRepo;
import lk.ijse.buddiescafe.repository.KitchenWareRepo;

import java.sql.SQLException;
import java.util.List;

public class AddIngrediansFromController {

    @FXML
    private JFXButton addIngredians;

    @FXML
    private JFXButton clear;

    @FXML
    private JFXComboBox<String> cmbFoodItemD;

    @FXML
    private JFXComboBox<String> cmbIngrediansID;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private JFXButton deleteIngredians;

    @FXML
    private TextField fDescription;

    @FXML
    private Label menuItem;

    @FXML
    private TextField qty;

    @FXML
    private TableView<?> tblMenu;

    @FXML
    private JFXButton updateIngredians;

    public void initialize() {
    getFoodItemIds();
    getInventoryIds();
    }

    private void getFoodItemIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = AddIngrediansRepo.getIds();

            for (String id : idList) {
                obList.add(id);
            }
            cmbFoodItemD.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getInventoryIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = AddIngrediansRepo.getInvenIds();

            for (String id : idList) {
                obList.add(id);
            }
            cmbIngrediansID.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    private void clearFields() {
        qty.setText("");
        cmbIngrediansID.setValue("");
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void cmbFoodItemOnAction(ActionEvent event) {

    }

    @FXML
    void cmbIngrediansOnAction(ActionEvent event) {

    }

    @FXML
    void qtyOnAction(ActionEvent event) {

    }

}
