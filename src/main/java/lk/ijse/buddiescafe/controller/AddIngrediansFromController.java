package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import lk.ijse.buddiescafe.model.AddIngredians;
import lk.ijse.buddiescafe.repository.AddIngrediansRepo;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colFoodItem;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupplement;

    @FXML
    private TextField qty;

    @FXML
    private TableView<AddIngredians> tblMenu;

    @FXML
    private JFXButton updateIngredians;

    private ObservableList<AddIngredians> addList = FXCollections.observableArrayList();

    public void initialize() {
    getInventoryIds();
    getFoodItemIds();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String ingrediansIDValue = cmbIngrediansID.getValue();
        String foodItemDValue = cmbFoodItemD.getValue();
        int qtyText = Integer.parseInt(qty.getText());

        JFXButton btnRemove = new JFXButton("REMOVE");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblMenu.getSelectionModel().getSelectedIndex();
                addList.remove(selectedIndex);

                tblMenu.refresh();
            }
        });
        for (int i = 0; i < tblMenu.getItems().size(); i++) {
            if (ingrediansIDValue.equals(colSupplement.getCellData(i))) {
               /* qty += cartList.get(i).getQty();
                total = unitPrice * qty;

                cartList.get(i).setQty(qty);
                cartList.get(i).setTotal(total);

                tblOrderCart.refresh();
                calculateNetTotal();
                txtQty.setText("");
                return;
            */}
        }
        AddIngredians addIngrediansText = new AddIngredians(ingrediansIDValue,foodItemDValue,qtyText,btnRemove);

        addList.add(addIngrediansText);

        tblMenu.setItems(addList);

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

    private void clearFields() {
        qty.setText("");
        cmbIngrediansID.setValue("");
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

}
