/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class mainController {

    @FXML
    private Button employees;

    @FXML
    private Button customers;

    @FXML
    private Button products;

    @FXML
    private Button orders;

    @FXML
    private Button suppliers;

    @FXML
    private Button shippers;

    @FXML
    private Button categories;

    @FXML
    private ImageView exit;

    @FXML
    private AreaChart<?, ?> completedOrdersChart;

}
