/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Address;
import model.Category;
import model.Customer;
import model.Employee;
import model.Order;
import model.Parameter;
import model.Product;
import model.Shipper;
import model.Supplier;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class orderTableController implements Initializable{

    @FXML
    private Button goBack;

    @FXML
    private TableView<Order> OrderTable;

    @FXML
    private TableColumn<Order, Number> orderIdCol;

    @FXML
    private TableColumn<Order, Customer> orderCustomerCol;

    @FXML
    private TableColumn<Order, Employee> orderEmployeeCol;

    @FXML
    private TableColumn<Order, String> orderOrderDateCol;

    @FXML
    private TableColumn<Order, String> orderReqDateCol;

    @FXML
    private TableColumn<Order, String> orderShipDateCol;

    @FXML
    private TableColumn<Order, Shipper> orderShipperCol;

    @FXML
    private TableColumn<Order, String> orderShipNameCol;

    @FXML
    private TableColumn<Order, Address> orderAddressCol;

    @FXML
    private TableColumn<Order, Void> delOrderCol;

    @FXML
    private Button addOrderButton;

    @FXML
    private TextField filterInput;

    @FXML
    private Button filterButton;
    
    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        if(event.getSource()==goBack){
            stage = (Stage) goBack.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        }
        else if(event.getSource()==addOrderButton){
            stage = (Stage) addOrderButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/addOrder.fxml"));
        }
        else{
            stage = null;
            root = null;
        }
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }   

    
        private List getOrderResultSet() throws SQLException{
        List ll = new LinkedList();
        try{  
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            Connection con=DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","system","oracle");  

            //ORDERS
            
            String query = "{call KJTCompany.SELECT_ORDERS(?)}";
            
            CallableStatement stmt = con.prepareCall(query);
            
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.executeQuery();
            
            ResultSet cursor = ((OracleCallableStatement)stmt).getCursor(1);

            try{
            while(cursor.next()){
                int orderId = cursor.getInt("OrderID");
                
                int customerId = cursor.getInt("CustomerID");
                
                int employeeId = cursor.getInt("EmployeeID");
                
                String orderDate = cursor.getString("OrderDate");
                String requiredDate = cursor.getString("RequiredDate");
                String shippedDate = cursor.getString("ShippedDate");
                
                int shipperId = cursor.getInt("ShipperID");
                
                String shipName = cursor.getString("ShipName");
                
                int addressId = cursor.getInt("ShipAddressID");

                //CUSTOMERS

                String customerQuery = "{call KJTCompany.SELECT_CUSTOMERS_FOR_ORDER(?,?)}";

                CallableStatement customerStmt = con.prepareCall(customerQuery);
                
                
                customerStmt.registerOutParameter(1, OracleTypes.CURSOR);
                customerStmt.setInt(2, customerId);
                customerStmt.executeQuery();

                ResultSet customerCursor = ((OracleCallableStatement)customerStmt).getCursor(1);
                
                Customer orderCustomer = new Customer();
                
                //EMPLOYEE
                
                String employeeQuery = "{call KJTCompany.SELECT_EMPLOYEES_FOR_ORDER(?,?)}";

                CallableStatement employeeStmt = con.prepareCall(employeeQuery);
                
                
                employeeStmt.registerOutParameter(1, OracleTypes.CURSOR);
                employeeStmt.setInt(2, employeeId);
                employeeStmt.executeQuery();

                ResultSet employeeCursor = ((OracleCallableStatement)employeeStmt).getCursor(1);
                
                Employee orderEmployee = new Employee();
                
                //SHIPPER
                
                String shipperQuery = "{call KJTCompany.SELECT_SHIPPERS_FOR_ORDER(?,?)}";

                CallableStatement shipperStmt = con.prepareCall(shipperQuery);
                
                
                shipperStmt.registerOutParameter(1, OracleTypes.CURSOR);
                shipperStmt.setInt(2, shipperId);
                shipperStmt.executeQuery();

                ResultSet shipperCursor = ((OracleCallableStatement)shipperStmt).getCursor(1);
                
                Shipper orderShipper = new Shipper();
                
                //ADDRESS
                
                String addressQuery = "{call KJTCompany.SELECT_ADDRESSES(?,?)}";

                CallableStatement addressStmt = con.prepareCall(addressQuery);
                
                
                addressStmt.registerOutParameter(1, OracleTypes.CURSOR);
                addressStmt.setInt(2, addressId);
                addressStmt.executeQuery();

                ResultSet addressCursor = ((OracleCallableStatement)addressStmt).getCursor(1);
                
                Address orderAddress = new Address();
                
                
                //CUSTOMER
                try{
                    while(customerCursor.next()){
                        orderCustomer.setCustomerID(customerCursor.getInt("CustomerID"));
                        orderCustomer.setFirstName(customerCursor.getString("FirstName"));
                        orderCustomer.setLastName(customerCursor.getString("LastName"));
                        orderCustomer.setContactTitle(null);
                        orderCustomer.setNIP(0);
                        orderCustomer.setAddress(null);
                        orderCustomer.setPhoneNumber(0);
                        orderCustomer.setEmail(null);
                        orderCustomer.setUser(null);
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //EMPLOYEE
                try{
                    while(employeeCursor.next()){
                        orderEmployee.setEmployeeID(employeeCursor.getInt("EmployeeID"));
                        orderEmployee.setFirstName(employeeCursor.getString("FirstName"));
                        orderEmployee.setLastName(employeeCursor.getString("LastName"));
                        orderEmployee.setTitle(null);
                        orderEmployee.setTitleOfCourtesy(null);
                        orderEmployee.setBirthDate(null);
                        orderEmployee.setHireDate(null);
                        orderEmployee.setAddress(null);
                        orderEmployee.setPhoneNumber(0);
                        orderEmployee.setEmail(null);
                        orderEmployee.setUser(null);
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //SHIPPER
                try{
                    while(shipperCursor.next()){
                        orderShipper.setShipperID(0);
                        orderShipper.setCompanyName(shipperCursor.getString("CompanyName"));
                        orderShipper.setPhoneNumber(0);
                        orderShipper.setEmail(null);
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //SHIPPER
                try{
                    while(addressCursor.next()){
                        orderAddress.setAddressID(0);
                        orderAddress.setAddress(addressCursor.getString("Address"));
                        orderAddress.setCity(addressCursor.getString("City"));
                        orderAddress.setRegion(null);
                        orderAddress.setPostalCode(null);
                        orderAddress.setCountry(addressCursor.getString("Country"));
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                customerCursor.close();
                addressCursor.close();
                shipperCursor.close();
                employeeCursor.close();
                
                Order order = new Order(orderId, orderCustomer, orderEmployee, orderDate, requiredDate, shippedDate, orderShipper, shipName, orderAddress);
                
                
                ll.add(order); 
            }
            }
                catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            cursor.close();
            con.close();  
        }
        catch(Exception e){ 
            System.out.println(e);
        }
        return ll;
    }
    
    private boolean searchFindsOrder(Order o, String searchText){
        if(o.getShipName() != null){
            return (o.getShipName().toLowerCase().contains(searchText.toLowerCase())) ||
                    (o.getEmployee().getFirstName().toLowerCase().contains(searchText.toLowerCase())) ||
                    (o.getEmployee().getLastName().toLowerCase().contains(searchText.toLowerCase())) ||
                    (o.getCustomer().getLastName().toLowerCase().contains(searchText.toLowerCase())) ||
                    (o.getCustomer().getFirstName().toLowerCase().contains(searchText.toLowerCase()));
        }
        else{
            return 
                    (o.getEmployee().getFirstName().toLowerCase().contains(searchText.toLowerCase())) ||
                    (o.getCustomer().getFirstName().toLowerCase().contains(searchText.toLowerCase()));
        }
    }
    
    private Predicate<Order> createPredicate(String searchText){
        return item -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return searchFindsOrder(item, searchText);
        };
    }
    
    @FXML
    private void handleResetFilterButtonAction (ActionEvent event) throws Exception {
        if(event.getSource()==filterButton){
            filterInput.setText(null);
        }
    }
        
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Order> OrderResultSet = null;
        try {
            OrderResultSet = FXCollections.observableArrayList(getOrderResultSet());
        } catch (SQLException ex) {
            Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FilteredList<Order> filteredData = new FilteredList<Order>(FXCollections.observableList(OrderResultSet));
        
        filterInput.textProperty().addListener((observable, oldValue, newValue) ->
            filteredData.setPredicate(createPredicate(newValue))
        );
        
        OrderTable.setEditable(true);
        
        
        orderIdCol.setCellValueFactory(new PropertyValueFactory<Order,Number>("OrderID"));
        orderCustomerCol.setCellValueFactory(new PropertyValueFactory<Order,Customer>("Customer"));
        orderEmployeeCol.setCellValueFactory(new PropertyValueFactory<Order,Employee>("Employee"));
        orderOrderDateCol.setCellValueFactory(new PropertyValueFactory<Order,String>("OrderDate"));
        orderReqDateCol.setCellValueFactory(new PropertyValueFactory<Order,String>("RequiredDate"));
        orderShipDateCol.setCellValueFactory(new PropertyValueFactory<Order,String>("ShippedDate"));
        orderShipperCol.setCellValueFactory(new PropertyValueFactory<Order,Shipper>("Shipper"));
        orderShipNameCol.setCellValueFactory(new PropertyValueFactory<Order,String>("ShipName"));
        orderAddressCol.setCellValueFactory(new PropertyValueFactory<Order,Address>("Address"));
        
        delOrderCol.setCellFactory(
        new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>() {
            @Override
            public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
                final TableCell<Order, Void> cell = new TableCell<Order, Void>() {
                    
                    Image image = new Image(getClass().getResourceAsStream("/img/icons/trash.png"), 32, 32, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setGraphic(new ImageView(image));
                        btn.setOnAction((ActionEvent event) -> {
                            Connection con;  
                            try {
                                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                                String query = "{call KJTCompany.DELETE_ORDER(?)}";
                                CallableStatement stmt = con.prepareCall(query);
                                stmt.setInt(1, getTableView().getItems().get(getIndex()).getOrderID());
                                stmt.execute();
                            } catch (SQLException ex) {
                                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            getTableView().getItems().remove(getIndex());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
         }
        ); 
        
        orderReqDateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        orderShipNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        
        orderReqDateCol.setOnEditCommit((TableColumn.CellEditEvent<Order, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_ORDER(?,?,?,?,?,?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getOrderID());
                for(int i = 2; i <= 4; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.setDate(5, java.sql.Date.valueOf(event.getNewValue()));
                for(int i = 6; i <= 9; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        orderShipNameCol.setOnEditCommit((TableColumn.CellEditEvent<Order, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_ORDER(?,?,?,?,?,?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getOrderID());
                for(int i = 2; i <= 7; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.setString(8, event.getNewValue());
                stmt.setNull(9, OracleTypes.NULL);
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        OrderTable.setItems(filteredData);
    }
    
}
