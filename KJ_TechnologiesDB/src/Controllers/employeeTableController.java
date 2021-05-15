/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class employeeTableController {

    @FXML
    private Button goBack;

    @FXML
    private TableView<?> employeeTable;

    @FXML
    private TableColumn<?, ?> employeeIdCol;

    @FXML
    private TableColumn<?, ?> employeeFirstNameCol;

    @FXML
    private TableColumn<?, ?> employeeLastNameCol;

    @FXML
    private TableColumn<?, ?> employeeTitleCol;

    @FXML
    private TableColumn<?, ?> employeeTitleOfCourtesy;

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

}
