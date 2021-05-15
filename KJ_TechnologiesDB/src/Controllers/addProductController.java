/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class addProductController {

    @FXML
    private Button goBack;

    @FXML
    private TextField productPriceInput;

    @FXML
    private PasswordField productQuantityInput;

    @FXML
    private TextField productNameInput;

    @FXML
    private Button addProductButton;

    @FXML
    private TextArea productDescInput;

    @FXML
    private ChoiceBox<?> supplierChoiceBox;

    @FXML
    private ChoiceBox<?> categoryChoiceBox;

    @FXML
    private Button addParameterInput;

    @FXML
    private Pane ParametersPane;

    @FXML
    private ChoiceBox<?> parameterChoiceBox;

    @FXML
    private TextField parameterValueInput;

}
