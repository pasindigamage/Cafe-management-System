package lk.ijse.buddiescafe.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.buddiescafe.db.DbConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginFromController {

    @FXML
    private PasswordField password;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private JFXButton signIn;

    @FXML
    private TextField userName;

    @FXML
    void letsSignInOnAction (ActionEvent event) throws IOException, SQLException {
        String un = userName.getText();
        String pw = password.getText();
        try {
            checkCredential(un, pw);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPS! something went wrong").show();
        }

    }

    public static String signPerson;

    private void checkCredential(String un, String pw) throws SQLException, IOException {
        String sql = "SELECT userName, password FROM User WHERE userName = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, un);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String dbPw = resultSet.getString(2);

            if(dbPw.equals(pw)) {
                navigateToTheDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "Password is incorrect!").show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "User Name is not found!").show();
        }
    }

    private void navigateToTheDashboard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/mainDashboard.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void linkForgotPasswordOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/changePassword.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.show();

    }

    @FXML
    void linkRegistrationOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/register.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.show();

    }

}