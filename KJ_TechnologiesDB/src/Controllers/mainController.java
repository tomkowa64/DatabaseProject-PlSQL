/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class mainController implements Initializable{

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
    private Button exit;

    @FXML
    private BarChart<String, Number> completedOrdersChart;
    
    @FXML
    private CategoryAxis xAxis;
    
    @FXML
    private NumberAxis yAxis;
    
    @FXML
    private void handleExitButtonAction (ActionEvent exitEvent) throws Exception{
        Stage stage = (Stage) exit.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        if(event.getSource()==employees){
            stage = (Stage) employees.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/EmployeeTable.fxml"));
        }
        else if(event.getSource()==customers){
            stage = (Stage) customers.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/CustomerTable.fxml"));
        }
        else if(event.getSource()==products){
            stage = (Stage) products.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/ProductTable.fxml"));
        }
        else if(event.getSource()==orders){
            stage = (Stage) orders.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/OrderTable.fxml"));
        }
        else if(event.getSource()==suppliers){
            stage = (Stage) suppliers.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/SupplierTable.fxml"));
        }
        else if(event.getSource()==shippers){
            stage = (Stage) shippers.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/ShipperTable.fxml"));
        }
        else if(event.getSource()==categories){
            stage = (Stage) categories.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/CategoryTable.fxml"));
        }
        else{
            stage = null;
            root = null;
        }
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void getDataChart() throws SQLException, URISyntaxException, MalformedURLException{
        completedOrdersChart.setTitle("Zamówienia");
        xAxis.setLabel("Miesiac");
        yAxis.setLabel("Ilosc zamowien");
        
        completedOrdersChart.getStylesheets().add(
                getClass().getResource("/css/unpad-chart.css").toURI().toURL().toExternalForm()
        );

        try 
        {
                Class.forName("oracle.jdbc.driver.OracleDriver");
        } 
        catch (ClassNotFoundException e) {
                e.printStackTrace();
        }

        Connection con=DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","system","oracle");  
        
        String query = "begin ? := GETMONTH(); end;";
        
        CallableStatement stmt = con.prepareCall(query);
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.execute();
        
        int currMonth = stmt.getInt(1);
        

        ArrayList<Integer> monthListInt = new ArrayList<Integer>();
        ArrayList<Integer> orderListInt = new ArrayList<Integer>();
        
        for(int i = currMonth; i > 0; ){
            monthListInt.add(i);
            
            String query2 = "begin ? := getOrderForMonthCount(?); end;";

            CallableStatement stmt2 = con.prepareCall(query2);
            stmt2.registerOutParameter(1, Types.INTEGER);
            stmt2.setInt(2, i);
            stmt2.execute();
            orderListInt.add(stmt2.getInt(1));
            i--;
        }
        
        Collections.reverse(orderListInt);
        Collections.reverse(monthListInt);
        
        Map map = new HashMap();
 
        map.put(-5, "Lipiec");        
        map.put(-4, "Sierpień");        
        map.put(-3, "Wrzesień");        
        map.put(-2, "Październik");       
        map.put(-1, "Listopad");        
        map.put(0, "Grudzień");
        map.put(1, "Styczen");
        map.put(2, "Luty");
        map.put(3, "Marzec");
        map.put(4, "Kwiecen");
        map.put(5, "Maj");
        map.put(6, "Czerwiec");
        map.put(7, "Lipiec");
        map.put(8, "Sierpien");
        map.put(9, "Wrzesien");
        map.put(10, "Pazdziernik");
        map.put(11, "Listopad");
        map.put(12, "Grudzien");
      
        
        Map map2 = new HashMap();
        map2.put(1,1);
        map2.put(2,2);
        map2.put(3,3);
        map2.put(4,4);
        map2.put(5,5);
        map2.put(6,6);
        map2.put(7,7);
        map2.put(8,8);
        map2.put(9,9);
        map2.put(10,10);
        map2.put(11,11);
        map2.put(0,12);
        map2.put(-1,11);
        map2.put(-2,10);
        map2.put(-3,9);
        map2.put(-4,8);
        map2.put(-5,7);
        
        XYChart.Series<String,Number> seriesOrders = new XYChart.Series();
        seriesOrders.setName("Zamowienia");
        
        for(int i = 0; i < monthListInt.size(); i++){
            seriesOrders.getData().add(new XYChart.Data(monthListInt.get(i).toString(), orderListInt.get(i)));
        }
        completedOrdersChart.getData().add(seriesOrders);
        con.close();
    } 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            getDataChart();
        } catch (SQLException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
