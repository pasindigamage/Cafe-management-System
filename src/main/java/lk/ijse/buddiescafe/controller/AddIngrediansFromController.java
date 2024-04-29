package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AddIngrediansFromController {

    @FXML
    private JFXButton addIngredians;

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton clear;

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
    private TextField fID;

    @FXML
    private TextField qty;

    @FXML
    private TextField qtyG;

    @FXML
    private TextField qtyMl;

    @FXML
    private TableView<?> tblMenu;

    @FXML
    private JFXButton updateIngredians;

    @FXML
    private AnchorPane rootNode;

    @FXML
    void IdSearchOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/foodItem.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
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
    void cmbIngrediansOnAction(ActionEvent event) {

    }

    @FXML
    void qtyGOnAction(ActionEvent event) {

    }

    @FXML
    void qtyMlOnAction(ActionEvent event) {

    }

    @FXML
    void qtyOnAction(ActionEvent event) {

    }

}
