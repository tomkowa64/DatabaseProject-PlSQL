/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class addEmployeeController implements Initializable{

    @FXML
    private Button goBack;

    @FXML
    private TextField employeeLastNameInput;

    @FXML
    private TextField employeeTitleInput;

    @FXML
    private TextField employeeTitleOfCourtesyInput;

    @FXML
    private DatePicker employeeBirthDateInput;

    @FXML
    private TextField employeePhoneNumberInput;

    @FXML
    private TextField employeeEmailInput;

    @FXML
    private TextField employeeAddressAddressInput;

    @FXML
    private TextField employeeAddressCityInput;

    @FXML
    private TextField employeeAddressRegionInput;

    @FXML
    private TextField employeeAddressPostalCodeInput;

    @FXML
    private TextField employeeAddressCountryInput;

    @FXML
    private TextField employeeUserLoginInput;

    @FXML
    private PasswordField employeeUserPasswordInput;

    @FXML
    private TextField employeeFirstNameInput;

    @FXML
    private Button addEmployeeButton;
    
    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        if(event.getSource()==goBack){
            stage = (Stage) goBack.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/EmployeeTable.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if(event.getSource()==addEmployeeButton){
            try{  
                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                if(!employeeFirstNameInput.getText().isEmpty() &&
                   !employeeLastNameInput.getText().isEmpty() &&
                   !employeePhoneNumberInput.getText().isEmpty() &&
                   !employeeEmailInput.getText().isEmpty() &&
                   !employeeAddressAddressInput.getText().isEmpty() &&
                   !employeeAddressCityInput.getText().isEmpty() &&
                   !employeeAddressPostalCodeInput.getText().isEmpty() &&
                   !employeeAddressCountryInput.getText().isEmpty() &&
                   !employeeUserLoginInput.getText().isEmpty() &&
                   !employeeUserPasswordInput.getText().isEmpty())
                {
                    Connection con=DriverManager.getConnection(  
                    "jdbc:oracle:thin:@localhost:1521:xe","system","oracle");  

                    String query = "{call KJTCompany.INSERT_EMPLOYEE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

                    CallableStatement stmt = con.prepareCall(query);

                    stmt.setString(1, employeeFirstNameInput.getText());
                    stmt.setString(2, employeeLastNameInput.getText());
                    stmt.setString(3, employeeTitleInput.getText());
                    stmt.setString(4, employeeTitleOfCourtesyInput.getText());
                    stmt.setDate(5, java.sql.Date.valueOf(employeeBirthDateInput.getValue()));
                    stmt.setDate(6, new Date(System.currentTimeMillis()));
                    stmt.setInt(7, Integer.parseInt(employeePhoneNumberInput.getText()));
                    stmt.setString(8, employeeEmailInput.getText());
                    stmt.setString(9, employeeAddressAddressInput.getText());
                    stmt.setString(10, employeeAddressCityInput.getText());
                    stmt.setString(11, employeeAddressRegionInput.getText());
                    stmt.setString(12, employeeAddressPostalCodeInput.getText());
                    stmt.setString(13, employeeAddressCountryInput.getText());
                    stmt.setString(14, employeeUserLoginInput.getText());
                    stmt.setString(15, employeeUserPasswordInput.getText());
                    stmt.execute();

                    employeeFirstNameInput.setText("");
                    employeeLastNameInput.setText("");
                    employeeTitleInput.setText("");
                    employeeTitleOfCourtesyInput.setText("");
                    employeeBirthDateInput.setValue(new Date(System.currentTimeMillis()).toLocalDate());
                    employeePhoneNumberInput.setText("");
                    employeeEmailInput.setText("");
                    employeeAddressAddressInput.setText("");
                    employeeAddressCityInput.setText("");
                    employeeAddressRegionInput.setText("");
                    employeeAddressPostalCodeInput.setText("");
                    employeeAddressCountryInput.setText("");
                    employeeUserLoginInput.setText("");
                    employeeUserPasswordInput.setText("");

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
