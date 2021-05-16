/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addOrderController implements Initializable{

    @FXML
    private Button goBack;

    @FXML
    private TextField orderAddressAddressInput;

    @FXML
    private TextField orderAddressCityInput;

    @FXML
    private TextField orderAddressRegionInput;

    @FXML
    private TextField orderAddressPostalCodeInput;

    @FXML
    private TextField orderAddressCountryInput;

    @FXML
    private Button addOrderButton;

    @FXML
    private ComboBox<?> orderCustomerComboBox;

    @FXML
    private ComboBox<?> orderEmployeeComboBox;

    @FXML
    private ComboBox<?> orderShipperComboBox;

    @FXML
    private ComboBox<?> orderProductComboBox;

    @FXML
    private TextField orderQuantityInput;

    @FXML
    private TextField orderDiscountInput;

    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        if(event.getSource()==goBack){
            stage = (Stage) goBack.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/OrderTable.fxml"));
        }
//        else if(event.getSource()==addCategoryButton){
//            stage = (Stage) addCategoryButton.getScene().getWindow();
//            root = FXMLLoader.load(getClass().getResource("/view/addCategory.fxml"));
//        } Add to Database code TODO
        else{
            stage = null;
            root = null;
        }
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }   
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
