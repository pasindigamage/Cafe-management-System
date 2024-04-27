package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

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

    }

}
