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

public class orderTableController {

    @FXML
    private Button goBack;

    @FXML
    private TableView<?> OrderTable;

    @FXML
    private TableColumn<?, ?> orderIdCol;

    @FXML
    private TableColumn<?, ?> orderCustomerCol;

    @FXML
    private TableColumn<?, ?> orderEmployeeCol;

    @FXML
    private TableColumn<?, ?> orderOrderDateCol;

    @FXML
    private TableColumn<?, ?> orderReqDateCol;

    @FXML
    private TableColumn<?, ?> orderShipDateCol;

    @FXML
    private TableColumn<?, ?> orderShipperCol;

    @FXML
    private TableColumn<?, ?> orderShipNameCol;

    @FXML
    private TableColumn<?, ?> orderAddressCol;

    @FXML
    private TableColumn<?, ?> delOrderCol;

    @FXML
    private Button addOrderButton;

    @FXML
    private TextField filterInput;

    @FXML
    private Button filterButton;

}
