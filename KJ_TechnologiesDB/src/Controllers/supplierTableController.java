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

public class supplierTableController {

    @FXML
    private Button goBack;

    @FXML
    private TableView<?> SupplierTable;

    @FXML
    private TableColumn<?, ?> supplierIdCol;

    @FXML
    private TableColumn<?, ?> supplierCompanyNameCol;

    @FXML
    private TableColumn<?, ?> supplierAddressCol;

    @FXML
    private TableColumn<?, ?> supplierPhoneNumberCol;

    @FXML
    private TableColumn<?, ?> supplierEmailCol;

    @FXML
    private TableColumn<?, ?> supplierWebpageCol;

    @FXML
    private TableColumn<?, ?> delSupplierCol;

    @FXML
    private Button addSupplierButton;

    @FXML
    private TextField filterInput;

    @FXML
    private Button filterButton;

}
