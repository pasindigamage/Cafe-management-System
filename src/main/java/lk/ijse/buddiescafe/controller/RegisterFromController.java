package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.buddiescafe.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterFromController {

    @FXML
    private JFXButton cancel;

    @FXML
    private AnchorPane childRoot;

    @FXML
    private TextField employeeID;

    @FXML
    private TextField password;

    @FXML
    private JFXButton register;

    @FXML
    private TextField userId;

    @FXML
    private TextField userName;

    @FXML
    void cancelOnAction(ActionEvent event) throws IOException {

    }


    @FXML
    void registerOnAction(ActionEvent event) {
        String eid = employeeID.getText();
        String uid = userId.getText();
        String uname = userName.getText();
        String pw = password.getText();

        saveUser(eid,uid,uname,pw);

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
                new Alert(Alert.AlertType.CONFIRMATION, "user saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happend!").show();
        }
    }

}
