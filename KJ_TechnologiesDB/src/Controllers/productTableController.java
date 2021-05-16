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

public class productTableController {

    @FXML
    private Button goBack;

    @FXML
    private TableView<?> ProductTable;

    @FXML
    private TableColumn<?, ?> productIdCol;

    @FXML
    private TableColumn<?, ?> productNameCol;

    @FXML
    private TableColumn<?, ?> productParametersCol;

    @FXML
    private TableColumn<?, ?> productDescCol;

    @FXML
    private TableColumn<?, ?> productSupplierCol;

    @FXML
    private TableColumn<?, ?> productCategoryCol;

    @FXML
    private TableColumn<?, ?> productPriceCol;

    @FXML
    private TableColumn<?, ?> productQuantityCol;

    @FXML
    private TableColumn<?, ?> delProductCol;

    @FXML
    private Button addProductButton;

    @FXML
    private Button showBestProductsButton;

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
        else if(event.getSource()==addProductButton){
            stage = (Stage) addProductButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/addProduct.fxml"));
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
