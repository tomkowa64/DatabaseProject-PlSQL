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

public class orderTableController {

    @FXML
    private Button goBack;

    @FXML
    private TableView<?> OrderTable;

    @FXML
    private TableColumn<?, ?> orderIdCol;

    @FXML
    private TableColumn<?, ?> orderCustomerCol;

    @FXML
    private TableColumn<?, ?> orderEmployeeCol;

    @FXML
    private TableColumn<?, ?> orderOrderDateCol;

    @FXML
    private TableColumn<?, ?> orderReqDateCol;

    @FXML
    private TableColumn<?, ?> orderShipDateCol;

    @FXML
    private TableColumn<?, ?> orderShipperCol;

    @FXML
    private TableColumn<?, ?> orderShipNameCol;

    @FXML
    private TableColumn<?, ?> orderAddressCol;

    @FXML
    private TableColumn<?, ?> delOrderCol;

    @FXML
    private Button addOrderButton;

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
        else if(event.getSource()==addOrderButton){
            stage = (Stage) addOrderButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/addOrder.fxml"));
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
