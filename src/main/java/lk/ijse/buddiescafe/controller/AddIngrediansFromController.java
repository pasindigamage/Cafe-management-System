package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AddIngrediansFromController {

    @FXML
    private JFXButton addIngredians;

    @FXML
    private JFXButton clear;

    @FXML
    private JFXComboBox<?> cmbFoodItemD;

    @FXML
    private JFXComboBox<?> cmbIngrediansID;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQty;

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

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

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
