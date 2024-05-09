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
import javafx.scene.layout.AnchorPane;
import lk.ijse.buddiescafe.model.FoodItems;
import lk.ijse.buddiescafe.model.Order;
import lk.ijse.buddiescafe.model.OrderDetail;
import lk.ijse.buddiescafe.model.PlaceOrder;
import lk.ijse.buddiescafe.model.TM.CartTM;
import lk.ijse.buddiescafe.repository.FoodItemsRepo;
import lk.ijse.buddiescafe.repository.OrdersRepo;
import lk.ijse.buddiescafe.repository.PlaceOrderRepo;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lk.ijse.buddiescafe.controller.LoginFromController.signPerson;

public class OrderFromController {

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Label lblUserName;

    @FXML
    private JFXButton placeButton;

    @FXML
    private TableView<CartTM> tblOrderCart;

    @FXML
    private TextField txtQty;

    @FXML
    private AnchorPane rootNode;

    private double netTotal;

    public void initialize(){
        setDate();
        getFoodItems();
        loadNextOrderId();
        setCashier();
        setCellValueFactory();
    }

    private void loadNextOrderId() {
        try {
            String currentId = OrdersRepo.currentId();
            String nextId = nextId(currentId);

            lblOrderId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("O");
            int id = Integer.parseInt(split[1]);    //2
            return "O" + ++id;

        }
        return "O1";
    }

    private void setCashier(){
        String un = signPerson;
        lblUserName.setText(un);
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblOrderDate.setText(String.valueOf(now));
    }

    private ObservableList<CartTM> cartList = FXCollections.observableArrayList();

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String code = lblDescription.getText();
        String description = cmbItemCode.getValue();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = qty * unitPrice;
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblOrderCart.getSelectionModel().getSelectedIndex();
                if (selectedIndex != -1) {
                    cartList.remove(selectedIndex);
                    tblOrderCart.refresh();
                    calculateNetTotal();
                } else {
                    new Alert(Alert.AlertType.WARNING, "No item selected to remove!").show();
                }
            }
        });

        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            if (code.equals(cartList.get(i).getCode())) {
                qty += cartList.get(i).getQty();
                total = unitPrice * qty;

                cartList.get(i).setQty(qty);
                cartList.get(i).setTotal(total);

                tblOrderCart.refresh();
                calculateNetTotal();
                txtQty.setText("");
                return;
            }
        }

        CartTM cartTm = new CartTM(code, description, qty, unitPrice, total, btnRemove);

        cartList.add(cartTm);

        tblOrderCart.setItems(cartList);
        txtQty.setText("");
        calculateNetTotal();
    }

    private void calculateNetTotal() {
        netTotal = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException {
        String orderId = lblOrderId.getText();
      //  String userId = signPerson;
        String date = String.valueOf(LocalDate.now());

        var order = new Order(orderId, date);

        List<OrderDetail> odList = new ArrayList<>();
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            CartTM tm = cartList.get(i);

            OrderDetail od = new OrderDetail(
                    orderId,
                    tm.getCode(),
                    tm.getQty()
            );
            odList.add(od);
        }

        PlaceOrder po = new PlaceOrder(order, odList);
        boolean isPlaced = PlaceOrderRepo.placeOrder(po);
        if(isPlaced) {
            new Alert(Alert.AlertType.CONFIRMATION, "Order placed successfully!").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Failed to place order!").show();
        }
    }

    private void getFoodItems() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = FoodItemsRepo.getIds();

            for (String id : idList) {
                obList.add(id);
            }
            cmbItemCode.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) {
        String itemCodeValue = cmbItemCode.getValue();
        try {
            FoodItems foodItems = FoodItemsRepo.searchByCode(itemCodeValue);
            if (foodItems != null) {
                lblDescription.setText(foodItems.getId());
                lblUnitPrice.setText(foodItems.getAmount());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {

    }

}
