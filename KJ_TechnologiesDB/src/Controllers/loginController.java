/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import static com.sun.javafx.application.PlatformImpl.exit;
import static java.lang.System.exit;
import java.net.URL;
import java.util.ResourceBundle;
import static javafx.application.Platform.exit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class loginController implements Initializable{

    @FXML
    private PasswordField password;

    @FXML
    private TextField login;

    @FXML
    private Button loginButton;

    public boolean checkLogin(String username, String password){
        //Check if username exists in DB and got proper password
        return true;
    }

    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        if(event.getSource()==loginButton){
            changeIntoMainView();
        }
    }
    
    public void changeIntoMainView() throws Exception{
        Stage stage;
        Parent root;
        if(checkLogin("sampleUser","samplePwd") == true){
            stage = (Stage) loginButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if(checkLogin("sampleUser","samplePwd") == false){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Niepowodzenie");
            alert.setContentText("Wprowadzono niepoprawny login lub hasło!");
            alert.show();
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
