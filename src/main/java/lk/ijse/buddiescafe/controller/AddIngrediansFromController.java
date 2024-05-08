package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.buddiescafe.model.AddIngredians;
import lk.ijse.buddiescafe.model.FoodItems;
import lk.ijse.buddiescafe.model.Inventory;
import lk.ijse.buddiescafe.model.KitchenWare;
import lk.ijse.buddiescafe.repository.AddIngrediansRepo;
import lk.ijse.buddiescafe.repository.FoodItemsRepo;
import lk.ijse.buddiescafe.repository.InventoryRepo;
import lk.ijse.buddiescafe.repository.KitchenWareRepo;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AddIngrediansFromController {

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
    private Label lblFoodItemID;

    @FXML
    private Label lblInvenID;

    @FXML
    private TableView<AddIngredians> tblMenu;

    @FXML
    private JFXButton updateIngredians;

    private ObservableList<AddIngredians> addList = FXCollections.observableArrayList();

    public void initialize() {
        getInventoryIds();
        getFoodItemIds();
        setCellValueFactory();
        loadInventoryTable();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFoodItem.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
        colSupplement.setCellValueFactory(new PropertyValueFactory<>("foodItemId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    private void loadInventoryTable() {
        ObservableList<AddIngredians> obList = FXCollections.observableArrayList();

        try {
            List<AddIngredians> addIngrediansList = AddIngrediansRepo.getAll();
            for (AddIngredians addIngredians : addIngrediansList) {
                AddIngredians tm = new AddIngredians(
                        addIngredians.getId(),
                        addIngredians.getFoodItemId(),
                        addIngredians.getInventoryId(),
                        addIngredians.getQty()
                );
                obList.add(tm);
            }
            tblMenu.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnAddOnAction(ActionEvent event) {
        String foodItemDValue = lblFoodItemID.getText();
        String ingrediansIDValue = lblInvenID.getText();
        String qtyText = qty.getText();
        String id = lblId.getText();

        if (foodItemDValue == null || ingrediansIDValue == null || qtyText.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields!").show();
            return;
        }

        AddIngredians addIngredians = new AddIngredians(id,foodItemDValue, ingrediansIDValue, qtyText);

        try {
            boolean isSaved = AddIngrediansRepo.save(addIngredians);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplement is Saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

   @FXML
    void btnUpdateOnAction(ActionEvent event) {
       String foodItemDValue = lblFoodItemID.getText();
       String ingrediansIDValue = lblInvenID.getText();
       String qtyText = qty.getText();
       String id = lblId.getText();

       if (foodItemDValue == null || ingrediansIDValue == null || qtyText.isEmpty()) {
           new Alert(Alert.AlertType.ERROR, "Please fill all the fields!").show();
           return;
       }

       AddIngredians addIngredians = new AddIngredians(id,foodItemDValue, ingrediansIDValue, qtyText);

       try {
           boolean isUpdated = AddIngrediansRepo.update(addIngredians);
           if (isUpdated) {
               new Alert(Alert.AlertType.CONFIRMATION, "Supplement is Updated!").show();
               clearFields();
           }
       } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
       }
    }

    @FXML
    private TextField lblId;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    void cmbFoodItemOnAction(ActionEvent event) {
        String foodItemDValue = cmbFoodItemD.getValue();
        try {
            FoodItems foodItems = FoodItemsRepo.searchByCode(foodItemDValue);
            if (foodItems != null) {
                lblFoodItemID.setText(foodItems.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbIngrediansOnAction(ActionEvent event) {
        String ingrediansIDValue = cmbIngrediansID.getValue();
        try {
            Inventory inventory = InventoryRepo.searchByCode(ingrediansIDValue);
            if (inventory != null) {
                lblInvenID.setText(inventory.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = lblId.getText();

        try {
            boolean isDeleted = AddIngrediansRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplement is Deleted!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
