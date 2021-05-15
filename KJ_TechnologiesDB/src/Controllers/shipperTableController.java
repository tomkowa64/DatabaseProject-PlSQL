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

public class shipperTableController {

    @FXML
    private Button goBack;

    @FXML
    private TableView<?> ShipperTable;

    @FXML
    private TableColumn<?, ?> shipperIdCol;

    @FXML
    private TableColumn<?, ?> shipperCompanyNameCol;

    @FXML
    private TableColumn<?, ?> shipperPhoneNumberCol;

    @FXML
    private TableColumn<?, ?> shipperEmailCol;

    @FXML
    private TableColumn<?, ?> delShipperCol;

    @FXML
    private Button addShipperButton;

    @FXML
    private TextField filterInput;

    @FXML
    private Button filterButton;

}
