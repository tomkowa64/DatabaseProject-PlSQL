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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class addProductController implements Initializable{

    @FXML
    private Button goBack;

    @FXML
    private TextField productPriceInput;

    @FXML
    private PasswordField productQuantityInput;

    @FXML
    private TextField productNameInput;

    @FXML
    private Button addProductButton;

    @FXML
    private TextArea productDescInput;

    @FXML
    private ChoiceBox<?> supplierChoiceBox;

    @FXML
    private ChoiceBox<?> categoryChoiceBox;

    @FXML
    private Button addParameterInput;

    @FXML
    private Pane ParametersPane;

    @FXML
    private ChoiceBox<?> parameterChoiceBox;

    @FXML
    private TextField parameterValueInput;
    
    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        if(event.getSource()==goBack){
            stage = (Stage) goBack.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/ProductTable.fxml"));
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
