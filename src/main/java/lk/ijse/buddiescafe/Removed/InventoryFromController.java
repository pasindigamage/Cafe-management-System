package lk.ijse.buddiescafe.Removed;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class InventoryFromController {

    @FXML
    private JFXButton addInventory;

    @FXML
    private JFXButton addMenu;

    @FXML
    private JFXButton clear;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private JFXButton deleteMenu;

    @FXML
    private TextField fDescription;

    @FXML
    private Label fID;

    @FXML
    private TextField fooditemSearch;

    @FXML
    private AnchorPane rootNode1;

    @FXML
    private TableView<Inventory> tblInventory;

    @FXML
    private JFXButton updateMenu;

    public void initialize() {
        setCellValueFactory();
        loadEmployeeTable();
        loadNextOrderId();
    }

    private void loadNextOrderId() {
        try {
            String currentId = InventoryRepo.currentId();
            String nextId = nextId(currentId);

            fID.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("I00");
            int id = Integer.parseInt(split[1]);    //2
            return "I00" + ++id;

        }
        return "I001";

    }

    private void loadEmployeeTable() {
        ObservableList<Inventory> obList = FXCollections.observableArrayList();

        try {
            List<Inventory> inventoryList = InventoryRepo.getAll();
            for (Inventory inventory : inventoryList) {
                Inventory tm = new Inventory(
                        inventory.getId(),
                        inventory.getDescription()
                );

                obList.add(tm);
            }

            tblInventory.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String idText = fID.getText();
        String descriptionText = fDescription.getText();

        Inventory inventory = new Inventory(idText,descriptionText);

        try {
            boolean isSaved = InventoryRepo.save(inventory);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Inventory is Saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnAnchorpaneChnageOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/inventorySupplierDetail.fxml"));
        Parent rootNode = loader.load();
        rootNode1.getChildren().clear();
        rootNode1.getChildren().add(rootNode);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        fDescription.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = fID.getText();

        try {
            boolean isDeleted = InventoryRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Inventory is Deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String idText = fID.getText();
        String descriptionText = fDescription.getText();

        Inventory inventory = new Inventory(idText,descriptionText);

        try {
            boolean isUpdated = InventoryRepo.update(inventory);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Inventory is Updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void IdSearchOnAction(ActionEvent actionEvent) {
        String description = fooditemSearch.getText();

        try {
            Inventory inventory = InventoryRepo.searchByDescription(description);

            if (inventory != null) {
                fID.setText(inventory.getId());
                fDescription.setText(inventory.getDescription());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
