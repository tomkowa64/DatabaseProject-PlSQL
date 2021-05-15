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

public class categoryTableController {

    @FXML
    private Button goBack;

    @FXML
    private TableView<?> CategoryTable;

    @FXML
    private TableColumn<?, ?> categoryIdCol;

    @FXML
    private TableColumn<?, ?> categoryNameCol;

    @FXML
    private TableColumn<?, ?> categoryDescCol;

    @FXML
    private TableColumn<?, ?> delCategoryCol;

    @FXML
    private Button addCategoryCol;

    @FXML
    private TextField filterInput;

    @FXML
    private Button filterButton;

}
