/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class shipperTableController {

    @FXML
    private Button goBack;

    @FXML
    private TableView<?> ShipperTable;

    @FXML
    private TableColumn<?, ?> shipperIdCol;

    @FXML
    private TableColumn<?, ?> shipperCompanyNameCol;

    @FXML
    private TableColumn<?, ?> shipperPhoneNumberCol;

    @FXML
    private TableColumn<?, ?> shipperEmailCol;

    @FXML
    private TableColumn<?, ?> delShipperCol;

    @FXML
    private Button addShipperButton;

    @FXML
    private TextField filterInput;

    @FXML
    private Button filterButton;
    
    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        if(event.getSource()==goBack){
            stage = (Stage) goBack.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        }
        else if(event.getSource()==addShipperButton){
            stage = (Stage) addShipperButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/addShipper.fxml"));
        }
        else{
            stage = null;
            root = null;
        }
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }   


}
