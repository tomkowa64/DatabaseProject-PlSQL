/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;


import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import model.Employee;
import model.Product;
import model.Shipper;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class addOrderController implements Initializable{

    @FXML
    private Button goBack;

    @FXML
    private Button addOrderButton;

    @FXML
    private ComboBox<Customer> orderCustomerComboBox;

    @FXML
    private ComboBox<Employee> orderEmployeeComboBox;

    @FXML
    private ComboBox<Shipper> orderShipperComboBox;

    @FXML
    private ComboBox<Product> orderProductComboBox;

    @FXML
    private TextField orderQuantityInput;

    @FXML
    private TextField orderDiscountInput;

    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        if(event.getSource()==goBack){
            stage = (Stage) goBack.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/OrderTable.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if(event.getSource()==addOrderButton){
            try{  
                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                if(!orderQuantityInput.getText().isEmpty())
                {
                    Connection con=DriverManager.getConnection(  
                    "jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                    
                    String addressQuery = "{call KJTCompany.SELECT_ADDRESS_FOR_CUSTOMER(?,?)}";
                    CallableStatement addressStmt = con.prepareCall(addressQuery);
                    addressStmt.registerOutParameter(1, OracleTypes.CURSOR);
                    addressStmt.setInt(2, orderCustomerComboBox.getValue().getCustomerID());
                    addressStmt.executeQuery();
                    ResultSet addressCursor = ((OracleCallableStatement)addressStmt).getCursor(1);
                    addressCursor.next();

                    String query = "{call KJTCompany.INSERT_ORDER(?,?,?,?,?,?)}";

                    CallableStatement stmt = con.prepareCall(query);

                    stmt.setInt(1, orderCustomerComboBox.getValue().getCustomerID());
                    stmt.setInt(2, orderEmployeeComboBox.getValue().getEmployeeID());
                    stmt.setDate(3, new Date(System.currentTimeMillis()));
                    stmt.setInt(4, orderShipperComboBox.getValue().getShipperID());
                    stmt.setString(5, orderCustomerComboBox.getValue().getFirstName().concat(" ").concat(orderCustomerComboBox.getValue().getLastName()));
                    stmt.setInt(6, addressCursor.getInt("AddressID"));
                    stmt.execute();
                    
                    String lastOrderIdQuery = "{call SELECT_LAST_ORDER_ID(?)}";
                    CallableStatement lastOrderStmt = con.prepareCall(lastOrderIdQuery);
                    lastOrderStmt.registerOutParameter(1, OracleTypes.CURSOR);
                    lastOrderStmt.executeQuery();
                    ResultSet lastOrderCursor = ((OracleCallableStatement)lastOrderStmt).getCursor(1);
                    lastOrderCursor.next();
                    
                    String selProductQuery = "{call KJTCompany.SELECT_PRODUCT(?,?)}";
                    CallableStatement selProductStmt = con.prepareCall(selProductQuery);
                    selProductStmt.registerOutParameter(1, OracleTypes.CURSOR);
                    selProductStmt.setInt(2, orderProductComboBox.getValue().getProductID());
                    selProductStmt.executeQuery();
                    ResultSet selProductCursor = ((OracleCallableStatement)selProductStmt).getCursor(1);
                    selProductCursor.next();
                    
                    stmt.close();
                    query = "{call KJTCompany.INSERT_ORDERDETAILS(?,?,?,?,?)}";
                    stmt = con.prepareCall(query);
                    
                    System.out.println(Float.parseFloat(orderDiscountInput.getText()) / 100);
                    
                    stmt.setInt(1, lastOrderCursor.getInt("ORDERID"));
                    stmt.setInt(2, orderProductComboBox.getValue().getProductID());
                    stmt.setInt(3, selProductCursor.getInt("PRICE"));
                    stmt.setInt(4, Integer.parseInt(orderQuantityInput.getText()));
                    stmt.setFloat(5, Float.parseFloat(orderDiscountInput.getText()) / 100);
                    stmt.execute();
                    
                    orderQuantityInput.setText("");
                    orderDiscountInput.setText("");

                    selProductCursor.close();
                    selProductStmt.close();
                    lastOrderCursor.close();
                    lastOrderStmt.close();
                    stmt.close();
                    addressCursor.close();
                    addressStmt.close();
                    con.close();  
                }
            }
            catch(SQLException e){ 
                System.out.println(e);
            }
        }
    }   
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List customerLi = new LinkedList();
        List employeeLi = new LinkedList();
        List shippersLi = new LinkedList();
        List productsLi = new LinkedList();
        try{
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection con=DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","system","oracle");  
            
            String query = "{call KJTCompany.SELECT_CUSTOMERS(?)}";
            CallableStatement stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.executeQuery();
            ResultSet cursor = ((OracleCallableStatement)stmt).getCursor(1);
            
            try{
                while(cursor.next()){
                    int customerId = cursor.getInt("CustomerID");
                    String customerFirstName = cursor.getString("FIRSTNAME");
                    String customerLastName = cursor.getString("LASTNAME");
                    
                    customerLi.add(new Customer(customerId, customerFirstName, customerLastName));
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            stmt.close();
            cursor.close();
            query = "{call KJTCompany.SELECT_EMPLOYEES(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.executeQuery();
            cursor = ((OracleCallableStatement)stmt).getCursor(1);
            
            try{
                while(cursor.next()){
                    int employeeId = cursor.getInt("EmployeeID");
                    String employeeFirstName = cursor.getString("FirstName");
                    String employeeLastName = cursor.getString("LastName");
                    
                    employeeLi.add(new Employee(employeeId, employeeFirstName, employeeLastName));
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            stmt.close();
            cursor.close();
            query = "{call KJTCompany.SELECT_SHIPPERS(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.executeQuery();
            cursor = ((OracleCallableStatement)stmt).getCursor(1);
            
            try{
                while(cursor.next()){
                    int shipperId = cursor.getInt("ShipperID");
                    String companyName = cursor.getString("CompanyName");
                    
                    shippersLi.add(new Shipper(shipperId, companyName));
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            stmt.close();
            cursor.close();
            query = "{call KJTCompany.SELECT_PRODUCTS(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.executeQuery();
            cursor = ((OracleCallableStatement)stmt).getCursor(1);
            
            try{
                while(cursor.next()){
                    int productId = cursor.getInt("ProductID");
                    String productName = cursor.getString("ProductName");
                    
                    productsLi.add(new Product(productId, productName));
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            stmt.close();
            cursor.close();
        }
        catch(Exception e){ 
            System.out.println(e);
        }
        
        ObservableList<Customer> customerList = FXCollections.observableArrayList(customerLi);
        orderCustomerComboBox.setItems(customerList);
        new AutoCompleteComboBoxListener<>(orderCustomerComboBox);
        
        ObservableList<Employee> employeeList = FXCollections.observableArrayList(employeeLi);
        orderEmployeeComboBox.setItems(employeeList);
        new AutoCompleteComboBoxListener<>(orderEmployeeComboBox);
        
        ObservableList<Shipper> shipperList = FXCollections.observableArrayList(shippersLi);
        orderShipperComboBox.setItems(shipperList);
        new AutoCompleteComboBoxListener<>(orderShipperComboBox);
        
        ObservableList<Product> productList = FXCollections.observableArrayList(productsLi);
        orderProductComboBox.setItems(productList);
        new AutoCompleteComboBoxListener<>(orderProductComboBox);
    }
}
