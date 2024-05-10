package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.util.Regex;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterFromController {

    @FXML
    private JFXButton cancel;

    @FXML
    private TextField employeeID;

    @FXML
    private TextField password;

    @FXML
    private TextField userId;

    @FXML
    private TextField userName;

    public void initialize(){
        employeeID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                userId.requestFocus();
            }
        });
        userId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                userName.requestFocus();
            }
        });

        userName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                password.requestFocus();
            }
        });
    }
    @FXML
    void cancelOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void registerOnAction(ActionEvent event) {
        String eid = employeeID.getText();
        String uid = userId.getText();
        String uname = userName.getText();
        String pw = password.getText();
            saveUser(eid, uid, uname, pw);
    }

    private void saveUser(String eid, String uid, String uname, String pw) {
        try {
            String sql = "INSERT INTO User VALUES(?, ?, ?, ?)";

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setObject(1, uid);
            pstm.setObject(2, eid);
            pstm.setObject(3, uname);
            pstm.setObject(4, pw);

            if(pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "User Saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something Happened!").show();
        }
    }

    @FXML
    void txtEmployeeIdOnKeyReleased(KeyEvent event) {
       // Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.name,eName);
    }

    @FXML
    void txtPasswordOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.password,password);
    }

    @FXML
    void txtUserIdOnKeyReleased(KeyEvent event) {
    //    Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.name,eName);
    }

    @FXML
    void txtUserNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.name,userName);
    }
}
