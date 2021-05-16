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

public class employeeTableController {

    @FXML
    private Button goBack;

    @FXML
    private TableView<?> EmployeeTable;

    @FXML
    private TableColumn<?, ?> employeeIdCol;

    @FXML
    private TableColumn<?, ?> employeeFirstNameCol;

    @FXML
    private TableColumn<?, ?> employeeLastNameCol;

    @FXML
    private TableColumn<?, ?> employeeTitleCol;

    @FXML
    private TableColumn<?, ?> employeeTitleOfCourtesyCol;

    @FXML
    private TableColumn<?, ?> employeeBirthDateCol;

    @FXML
    private TableColumn<?, ?> employeeHireDateCol;

    @FXML
    private TableColumn<?, ?> employeeAddressCol;

    @FXML
    private TableColumn<?, ?> employeePhoneNumberCol;

    @FXML
    private TableColumn<?, ?> employeeEmailCol;

    @FXML
    private TableColumn<?, ?> employeeUserCol;

    @FXML
    private TableColumn<?, ?> delEmployeeCol;

    @FXML
    private Button addEmployeeButton;

    @FXML
    private Button showBestEmployeesButton;

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
        else if(event.getSource()==addEmployeeButton){
            stage = (Stage) addEmployeeButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/addEmployee.fxml"));
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
