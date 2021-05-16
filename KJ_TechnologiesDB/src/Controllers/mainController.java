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
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class mainController implements Initializable{

    @FXML
    private Button employees;

    @FXML
    private Button customers;

    @FXML
    private Button products;

    @FXML
    private Button orders;

    @FXML
    private Button suppliers;

    @FXML
    private Button shippers;

    @FXML
    private Button categories;

    @FXML
    private Button exit;

    @FXML
    private AreaChart<?, ?> completedOrdersChart;
    
    @FXML
    private void handleExitButtonAction (ActionEvent exitEvent) throws Exception{
        Stage stage = (Stage) exit.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        if(event.getSource()==employees){
            stage = (Stage) employees.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/EmployeeTable.fxml"));
        }
        else if(event.getSource()==customers){
            stage = (Stage) customers.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/CustomerTable.fxml"));
        }
        else if(event.getSource()==products){
            stage = (Stage) products.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/ProductTable.fxml"));
        }
        else if(event.getSource()==orders){
            stage = (Stage) orders.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/OrderTable.fxml"));
        }
        else if(event.getSource()==suppliers){
            stage = (Stage) suppliers.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/SupplierTable.fxml"));
        }
        else if(event.getSource()==shippers){
            stage = (Stage) shippers.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/ShipperTable.fxml"));
        }
        else if(event.getSource()==categories){
            stage = (Stage) categories.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/CategoryTable.fxml"));
        }
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
