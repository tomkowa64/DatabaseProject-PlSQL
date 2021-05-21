/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addSupplierController implements Initializable{

    @FXML
    private Button goBack;

    @FXML
    private TextField supplierPhoneNumberInput;

    @FXML
    private TextField supplierEmailInput;

    @FXML
    private TextField supplierAddressAddressInput;

    @FXML
    private TextField supplierAddressCityInput;

    @FXML
    private TextField supplierAddressRegionInput;

    @FXML
    private TextField supplierAddressPostalCodeInput;

    @FXML
    private TextField supplierAddressCountryInput;

    @FXML
    private TextField supplierCompanyNameInput;

    @FXML
    private Button addSupplierButton;

    @FXML
    private TextField supplierWebpageInput;

    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        if(event.getSource()==goBack){
            stage = (Stage) goBack.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/SupplierTable.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if(event.getSource()==addSupplierButton){
            try{  
                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                if(!supplierCompanyNameInput.getText().isEmpty() &&
                   !supplierPhoneNumberInput.getText().isEmpty() &&
                   !supplierEmailInput.getText().isEmpty() &&
                   !supplierWebpageInput.getText().isEmpty() &&
                   !supplierAddressAddressInput.getText().isEmpty() &&
                   !supplierAddressCityInput.getText().isEmpty() &&
                   !supplierAddressPostalCodeInput.getText().isEmpty() &&
                   !supplierAddressCountryInput.getText().isEmpty())
                {
                    Connection con=DriverManager.getConnection(  
                    "jdbc:oracle:thin:@localhost:1521:xe","system","oracle");  

                    String query = "{call KJTCompany.INSERT_SUPPLIER(?,?,?,?,?,?,?,?,?)}";

                    CallableStatement stmt = con.prepareCall(query);

                    stmt.setString(1, supplierCompanyNameInput.getText());
                    stmt.setInt(2, Integer.parseInt(supplierPhoneNumberInput.getText()));
                    stmt.setString(3, supplierEmailInput.getText());
                    stmt.setString(4, supplierWebpageInput.getText());
                    stmt.setString(5, supplierAddressAddressInput.getText());
                    stmt.setString(6, supplierAddressCityInput.getText());
                    stmt.setString(7, supplierAddressRegionInput.getText());
                    stmt.setString(8, supplierAddressPostalCodeInput.getText());
                    stmt.setString(9, supplierAddressCountryInput.getText());
                    stmt.execute();

                    supplierCompanyNameInput.setText("");
                    supplierPhoneNumberInput.setText("");
                    supplierEmailInput.setText("");
                    supplierWebpageInput.setText("");
                    supplierAddressAddressInput.setText("");
                    supplierAddressCityInput.setText("");
                    supplierAddressRegionInput.setText("");
                    supplierAddressPostalCodeInput.setText("");
                    supplierAddressCountryInput.setText("");

                    con.close();  
                }
            }
            catch(SQLException e){ 
                System.out.println(e);
            }
        }
    }   
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
