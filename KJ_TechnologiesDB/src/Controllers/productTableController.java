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

public class productTableController {

    @FXML
    private Button goBack;

    @FXML
    private TableView<?> ProductTable;

    @FXML
    private TableColumn<?, ?> productIdCol;

    @FXML
    private TableColumn<?, ?> productNameCol;

    @FXML
    private TableColumn<?, ?> productParametersCol;

    @FXML
    private TableColumn<?, ?> productDescCol;

    @FXML
    private TableColumn<?, ?> productSupplierCol;

    @FXML
    private TableColumn<?, ?> productCategoryCol;

    @FXML
    private TableColumn<?, ?> productPriceCol;

    @FXML
    private TableColumn<?, ?> productQuantityCol;

    @FXML
    private TableColumn<?, ?> delProductCol;

    @FXML
    private Button addProductButton;

    @FXML
    private Button showBestProductsButton;

    @FXML
    private TextField filterInput;

    @FXML
    private Button filterButton;

}
