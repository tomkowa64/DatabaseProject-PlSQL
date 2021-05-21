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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import oracle.jdbc.OracleTypes;

public class addCustomerController implements Initializable{

    @FXML
    private Button goBack;

    @FXML
    private TextField customerLastNameInput;

    @FXML
    private TextField customerContactTitleInput;

    @FXML
    private TextField customerNipInput;

    @FXML
    private TextField customerPhoneNumberInput;

    @FXML
    private TextField customerEmailInput;

    @FXML
    private TextField customerAddressAddressInput;

    @FXML
    private TextField customerAddresCityInput;

    @FXML
    private TextField customerAddressRegion;

    @FXML
    private TextField customerAddressPostalCodeInput;

    @FXML
    private TextField customerAddressCountryInput;

    @FXML
    private TextField customerUserLoginInput;

    @FXML
    private PasswordField customerUserPasswordInput;

    @FXML
    private TextField customerFirstNameInput;

    @FXML
    private Button addCustomerButton;
    
    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        if(event.getSource()==goBack){
            stage = (Stage) goBack.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/CustomerTable.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if(event.getSource()==addCustomerButton){
            try{  
                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                if(!customerFirstNameInput.getText().isEmpty() &&
                   !customerLastNameInput.getText().isEmpty() &&
                   !customerPhoneNumberInput.getText().isEmpty() &&
                   !customerUserLoginInput.getText().isEmpty() &&
                   !customerUserPasswordInput.getText().isEmpty() &&
                   !customerAddressAddressInput.getText().isEmpty() &&
                   !customerAddresCityInput.getText().isEmpty() &&
                   !customerAddressPostalCodeInput.getText().isEmpty() &&
                   !customerAddressCountryInput.getText().isEmpty())
                {
                    Connection con=DriverManager.getConnection(  
                    "jdbc:oracle:thin:@localhost:1521:xe","system","oracle");  

                    String query = "{call KJTCompany.INSERT_CUSTOMER(?,?,?,?,?,?,?,?,?,?,?,?,?)}";

                    CallableStatement stmt = con.prepareCall(query);

                    stmt.setString(1, customerFirstNameInput.getText());
                    stmt.setString(2, customerLastNameInput.getText());
                    stmt.setString(3, customerContactTitleInput.getText());
                    
                    if(!customerNipInput.getText().isEmpty())
                    {
                        stmt.setLong(4, Long.parseLong(customerNipInput.getText()));
                    }
                    else
                    {
                        stmt.setNull(4, OracleTypes.NULL);
                    }
                    
                    stmt.setInt(5, Integer.parseInt(customerPhoneNumberInput.getText()));
                    stmt.setString(6, customerEmailInput.getText());
                    stmt.setString(7, customerAddressAddressInput.getText());
                    stmt.setString(8, customerAddresCityInput.getText());
                    stmt.setString(9, customerAddressRegion.getText());
                    stmt.setString(10, customerAddressPostalCodeInput.getText());
                    stmt.setString(11, customerAddressCountryInput.getText());
                    stmt.setString(12, customerUserLoginInput.getText());
                    stmt.setString(13, customerUserPasswordInput.getText());
                    stmt.execute();

                    customerFirstNameInput.setText("");
                    customerLastNameInput.setText("");
                    customerContactTitleInput.setText("");
                    customerNipInput.setText("");
                    customerPhoneNumberInput.setText("");
                    customerEmailInput.setText("");
                    customerAddressAddressInput.setText("");
                    customerAddressRegion.setText("");
                    customerAddressPostalCodeInput.setText("");
                    customerAddressPostalCodeInput.setText("");
                    customerAddressCountryInput.setText("");
                    customerUserLoginInput.setText("");
                    customerUserPasswordInput.setText("");

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
