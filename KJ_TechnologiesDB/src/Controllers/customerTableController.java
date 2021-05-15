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

public class customerTableController {

    @FXML
    private Button goBack;

    @FXML
    private TableView<?> CustomerTable;

    @FXML
    private TableColumn<?, ?> customerIdCol;

    @FXML
    private TableColumn<?, ?> customerFirstNameCol;

    @FXML
    private TableColumn<?, ?> customerLastNameCol;

    @FXML
    private TableColumn<?, ?> customerTitleCol;

    @FXML
    private TableColumn<?, ?> customerNipCol;

    @FXML
    private TableColumn<?, ?> customerAddressCol;

    @FXML
    private TableColumn<?, ?> customerPhoneNumberCol;

    @FXML
    private TableColumn<?, ?> customerEmailCol;

    @FXML
    private TableColumn<?, ?> customerUserCol;

    @FXML
    private TableColumn<?, ?> delCustomerCol;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button showBestCustomersButton;

    @FXML
    private TextField filterInput;

    @FXML
    private Button filterButton;

}
