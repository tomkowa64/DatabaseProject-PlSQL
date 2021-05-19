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

public class addShipperController implements Initializable{

    @FXML
    private Button goBack;

    @FXML
    private TextField shipperPhoneNumberInput;

    @FXML
    private TextField shipperEmailInput;

    @FXML
    private TextField shipperCompanyNameInput;

    @FXML
    private Button addShipperButton;
    
    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        if(event.getSource()==goBack){
            stage = (Stage) goBack.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/ShipperTable.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if(event.getSource()==addShipperButton){
            try{  
                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                if(!shipperCompanyNameInput.getText().isEmpty() && !shipperPhoneNumberInput.getText().isEmpty())
                {
                    Connection con=DriverManager.getConnection(  
                    "jdbc:oracle:thin:@localhost:1521:xe","system","oracle");  

                    String query = "{call KJTCompany.INSERT_SHIPPER(?,?,?)}";

                    CallableStatement stmt = con.prepareCall(query);

                    stmt.setString(1, shipperCompanyNameInput.getText());
                    stmt.setString(2, shipperPhoneNumberInput.getText());
                    stmt.setString(3, shipperEmailInput.getText());
                    stmt.execute();

                    shipperCompanyNameInput.setText("");
                    shipperPhoneNumberInput.setText("");
                    shipperEmailInput.setText("");

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
