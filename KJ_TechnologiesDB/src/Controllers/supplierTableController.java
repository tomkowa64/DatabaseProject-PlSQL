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

public class supplierTableController {

    @FXML
    private Button goBack;

    @FXML
    private TableView<?> SupplierTable;

    @FXML
    private TableColumn<?, ?> supplierIdCol;

    @FXML
    private TableColumn<?, ?> supplierCompanyNameCol;

    @FXML
    private TableColumn<?, ?> supplierAddressCol;

    @FXML
    private TableColumn<?, ?> supplierPhoneNumberCol;

    @FXML
    private TableColumn<?, ?> supplierEmailCol;

    @FXML
    private TableColumn<?, ?> supplierWebpageCol;

    @FXML
    private TableColumn<?, ?> delSupplierCol;

    @FXML
    private Button addSupplierButton;

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
        else if(event.getSource()==addSupplierButton){
            stage = (Stage) addSupplierButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/addSupplier.fxml"));
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
