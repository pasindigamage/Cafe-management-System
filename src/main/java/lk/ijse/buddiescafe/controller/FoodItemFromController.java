package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.buddiescafe.model.FoodItems;
import lk.ijse.buddiescafe.repository.FoodItemsRepo;
import lk.ijse.buddiescafe.util.Regex;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
    private TextField fooditemSearch;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private JFXButton deleteMenu;

    @FXML
    private TextField fAmount;

    @FXML
    private TextField fDescription;

    @FXML
    private Label fID;

    @FXML
    private AnchorPane rootNode1;

    @FXML
    private TableView<FoodItems> tblMenu;

    @FXML
    private JFXButton updateMenu;

    public void initialize(){
        setCellValueFactory();
        loadFoodTable();
        loadNextOrderId();

        fDescription.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                fAmount.requestFocus();
            }
        });

        fAmount.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                fQty.requestFocus();
            }
        });
        tblMenu.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fAmount.setText(String.valueOf(newSelection.getUnitPrice()));
                fDescription.setText(newSelection.getDescription());
                fQty.setText(String.valueOf(newSelection.getQtyOnHand()));

            }
        });
    }

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TextField fQty;

    private void loadNextOrderId() {
        try {
            String currentId = FoodItemsRepo.currentId();
            String nextId = nextId(currentId);

            fID.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("F");
            int idNum = Integer.parseInt(split[1]);
            idNum++;
            return "F" + String.format("%03d", idNum);
        }
        return "F001";
    }

    private void loadFoodTable() {
        ObservableList<FoodItems> obList = FXCollections.observableArrayList();

        try {
            List<FoodItems> foodItemsList = FoodItemsRepo.getAll();
            for (FoodItems foodItems : foodItemsList) {
                FoodItems tm = new FoodItems(
                        foodItems.getId(),
                        foodItems.getDescription(),
                        foodItems.getUnitPrice(),
                        foodItems.getQtyOnHand()
                );

                obList.add(tm);
            }

            tblMenu.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
    }



    @FXML
    void IdSearchOnAction(ActionEvent event) {
        String description = fooditemSearch.getText();

        try {
            FoodItems foodItems = FoodItemsRepo.searchByDescription(description);

            if (foodItems != null) {
                fID.setText(foodItems.getId());
                fDescription.setText(foodItems.getDescription());
                fAmount.setText(String.valueOf(foodItems.getUnitPrice()));
                fQty.setText(String.valueOf(foodItems.getQtyOnHand()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String idText = fID.getText();
        String descriptionText = fDescription.getText();
        double amountText = Double.parseDouble(fAmount.getText());
        int qtyText = Integer.parseInt(fQty.getText());

        FoodItems foodItems = new FoodItems(idText,descriptionText,amountText,qtyText);

        try {
            boolean isSaved = FoodItemsRepo.save(foodItems);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Menu Item is Saved!").show();
                clearFields();
                loadNextOrderId();
                loadFoodTable();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnAnchorpaneChnageOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/buddiescafe/Removed/addIngredians.fxml"));
        Parent rootNode = loader.load();
        rootNode1.getChildren().clear();
        rootNode1.getChildren().add(rootNode);

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        fDescription.setText("");
        fAmount.setText("");
        fQty.setText("");
        loadNextOrderId();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = fID.getText();

        try {
            boolean isDeleted = FoodItemsRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Menu Item is Deleted!").show();
                loadFoodTable();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String idText = fID.getText();
        String descriptionText = fDescription.getText();
        double amountText = Double.parseDouble(fAmount.getText());
        int qtyText = Integer.parseInt(fQty.getText());

        FoodItems foodItems = new FoodItems(idText,descriptionText,amountText,qtyText);

        try {
            boolean isUpdated = FoodItemsRepo.update(foodItems);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Menu Item is Updated!").show();
                loadFoodTable();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.amount,fAmount)) return false;


        return true;
    }
    @FXML
    void txtQtyOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.amount,fAmount);
    }

}