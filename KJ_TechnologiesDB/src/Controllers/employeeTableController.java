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
import model.Customer;
import model.Employee;
import model.Supplier;
import model.User;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class employeeTableController implements Initializable{

    @FXML
    private Button goBack;

    @FXML
    private TableView<Employee> EmployeeTable;

    @FXML
    private TableColumn<Employee, Number> employeeIdCol;

    @FXML
    private TableColumn<Employee, String> employeeFirstNameCol;

    @FXML
    private TableColumn<Employee, String> employeeLastNameCol;

    @FXML
    private TableColumn<Employee, String> employeeTitleCol;

    @FXML
    private TableColumn<Employee, String> employeeTitleOfCourtesyCol;

    @FXML
    private TableColumn<Employee, String> employeeBirthDateCol;

    @FXML
    private TableColumn<Employee, String> employeeHireDateCol;

    @FXML
    private TableColumn<Employee, Address> employeeAddressCol;

    @FXML
    private TableColumn<Employee, Number> employeePhoneNumberCol;

    @FXML
    private TableColumn<Employee, String> employeeEmailCol;

    @FXML
    private TableColumn<Employee, User> employeeUserCol;

    @FXML
    private TableColumn<Employee, Void> delEmployeeCol;

    @FXML
    private Button addEmployeeButton;

    @FXML
    private Button showBestEmployeesButton;

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
        else if(event.getSource()==addEmployeeButton){
            stage = (Stage) addEmployeeButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/addEmployee.fxml"));
        }
        else{
            stage = null;
            root = null;
        }
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
        private List getBestEmployeeResultSet() throws SQLException{
            List ll = new LinkedList();
            try{  
                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                Connection con=DriverManager.getConnection(  
                "jdbc:oracle:thin:@localhost:1521:xe","system","oracle");  

                //CUSTOMERS

                String query = "{call SELECT_TOP_EMPLOYEES(?,?)}";

                CallableStatement stmt = con.prepareCall(query);
                
                stmt.setInt(1, 10);
                stmt.registerOutParameter(2, OracleTypes.CURSOR);
                stmt.executeQuery();

                ResultSet cursor = ((OracleCallableStatement)stmt).getCursor(2);

                try{
                while(cursor.next()){
                    int employeeId = cursor.getInt("EmployeeID");
                    String employeeFirstName = cursor.getString("FirstName");
                    String employeeLastName = cursor.getString("LastName");
                    String employeeTitle = cursor.getString("Title");
                    String employeeTitleOfCourtesy = cursor.getString("TitleOfCourtesy");
                    String employeeBirthDate= cursor.getString("BirthDate");
                    String employeeHireDate= cursor.getString("HireDate");

                    int EmployeeAddressId = 0;
                    EmployeeAddressId = cursor.getInt("ADDRESSID");

                    //ADDRESSES

                    String addressQuery = "{call KJTCompany.SELECT_ADDRESSES(?,?)}";

                    CallableStatement addressStmt = con.prepareCall(addressQuery);


                    addressStmt.registerOutParameter(1, OracleTypes.CURSOR);
                    addressStmt.setInt(2, EmployeeAddressId);
                    addressStmt.executeQuery();

                    ResultSet addressCursor = ((OracleCallableStatement)addressStmt).getCursor(1);

                    Address employeeAddress = new Address();


                    int employeePhoneNumber = cursor.getInt("PhoneNumber");
                    String employeeEmail = cursor.getString("Email");

                    int employeeUserId = 0; 
                    employeeUserId = cursor.getInt("UserID");


                    try{
                        while(addressCursor.next()){
                            employeeAddress.setAddressID(addressCursor.getInt("AddressID"));
                            employeeAddress.setAddress(addressCursor.getString("Address"));
                            employeeAddress.setCity(addressCursor.getString("City"));
                            employeeAddress.setRegion(addressCursor.getString("Region"));
                            employeeAddress.setPostalCode(addressCursor.getString("Postalcode"));
                            employeeAddress.setCountry(addressCursor.getString("Country"));

                        }
                    }catch (SQLException ex) {
                        Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    //USERS

                    String usersQuery = "{call KJTUser.SELECT_USERS(?,?)}";

                    CallableStatement usersStmt = con.prepareCall(usersQuery);

                    usersStmt.registerOutParameter(1, OracleTypes.CURSOR);
                    usersStmt.setInt(2, employeeUserId);
                    usersStmt.executeQuery();

                    ResultSet userCursor = ((OracleCallableStatement)usersStmt).getCursor(1);
                    User employeeUser = new User();

                    try{
                        while(userCursor.next()){
                        employeeUser.setUserID(userCursor.getInt("UserID"));
                        employeeUser.setLogin(userCursor.getString("Login"));
                        employeeUser.setPassword(userCursor.getString("Password"));
                        employeeUser.setAccountType(userCursor.getString("AccountType"));
                        }
                    }catch (SQLException ex) {
                        Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    Employee employee = new Employee(employeeId, employeeFirstName, employeeLastName, employeeTitle, employeeTitleOfCourtesy, employeeBirthDate, employeeHireDate,
                    employeeAddress, employeePhoneNumber,employeeEmail, employeeUser);

                    ll.add(employee); 
                }
                }
                    catch (SQLException ex) {
                    Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
                }
                con.close();  
            }
            catch(Exception e){ 
                System.out.println(e);
            }
            return ll;
        }
        

        private List getEmployeeResultSet() throws SQLException{
        List ll = new LinkedList();
        try{  
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            Connection con=DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","system","oracle");  

            //CUSTOMERS
            
            String query = "{call KJTCompany.SELECT_EMPLOYEES(?)}";
            
            CallableStatement stmt = con.prepareCall(query);
            
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.executeQuery();
            
            ResultSet cursor = ((OracleCallableStatement)stmt).getCursor(1);

            try{
            while(cursor.next()){
                int employeeId = cursor.getInt("EmployeeID");
                String employeeFirstName = cursor.getString("FirstName");
                String employeeLastName = cursor.getString("LastName");
                String employeeTitle = cursor.getString("Title");
                String employeeTitleOfCourtesy = cursor.getString("TitleOfCourtesy");
                String employeeBirthDate= cursor.getString("BirthDate");
                String employeeHireDate= cursor.getString("HireDate");
                
                int EmployeeAddressId = 0;
                EmployeeAddressId = cursor.getInt("ADDRESSID");

                //ADDRESSES

                String addressQuery = "{call KJTCompany.SELECT_ADDRESSES(?,?)}";

                CallableStatement addressStmt = con.prepareCall(addressQuery);
                
                addressStmt.registerOutParameter(1, OracleTypes.CURSOR);
                addressStmt.setInt(2, EmployeeAddressId);
                addressStmt.executeQuery();

                ResultSet addressCursor = ((OracleCallableStatement)addressStmt).getCursor(1);
                
                Address employeeAddress = new Address();
                
                
                int employeePhoneNumber = cursor.getInt("PhoneNumber");
                String employeeEmail = cursor.getString("Email");
                
                int employeeUserId = 0; 
                employeeUserId = cursor.getInt("UserID");
                
                try{
                    while(addressCursor.next()){
                        employeeAddress.setAddressID(addressCursor.getInt("AddressID"));
                        employeeAddress.setAddress(addressCursor.getString("Address"));
                        employeeAddress.setCity(addressCursor.getString("City"));
                        employeeAddress.setRegion(addressCursor.getString("Region"));
                        employeeAddress.setPostalCode(addressCursor.getString("Postalcode"));
                        employeeAddress.setCountry(addressCursor.getString("Country"));

                    }
                }catch (SQLException ex) {
                    Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //USERS

                String usersQuery = "{call KJTUser.SELECT_USERS(?,?)}";

                CallableStatement usersStmt = con.prepareCall(usersQuery);

                usersStmt.registerOutParameter(1, OracleTypes.CURSOR);
                usersStmt.setInt(2, employeeUserId);
                usersStmt.executeQuery();

                ResultSet userCursor = ((OracleCallableStatement)usersStmt).getCursor(1);
                User employeeUser = new User();
                
                try{
                    while(userCursor.next()){
                    employeeUser.setUserID(userCursor.getInt("UserID"));
                    employeeUser.setLogin(userCursor.getString("Login"));
                    employeeUser.setPassword(userCursor.getString("Password"));
                    employeeUser.setAccountType(userCursor.getString("AccountType"));
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Employee employee = new Employee(employeeId, employeeFirstName, employeeLastName, employeeTitle, employeeTitleOfCourtesy, employeeBirthDate, employeeHireDate,
                employeeAddress, employeePhoneNumber,employeeEmail, employeeUser);
                
                ll.add(employee); 
            }
            }
                catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            con.close();  
        }
        catch(Exception e){ 
            System.out.println(e);
        }
        return ll;
    }
        
    @FXML
    public void setBestEmployees(){
        ObservableList<Employee> EmployeeResultSet = null;
        try {
            EmployeeResultSet = FXCollections.observableArrayList(getBestEmployeeResultSet());
        } catch (SQLException ex) {
            Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FilteredList<Employee> filteredData = new FilteredList<Employee>(FXCollections.observableList(EmployeeResultSet));
        
        filterInput.textProperty().addListener((observable, oldValue, newValue) ->
            filteredData.setPredicate(createPredicate(newValue))
        );
        
        EmployeeTable.setEditable(true);
        
        
        employeeIdCol.setCellValueFactory(new PropertyValueFactory<Employee,Number>("EmployeeID"));
        employeeFirstNameCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("FirstName"));
        employeeLastNameCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("LastName"));
        employeeTitleCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("Title"));
        employeeTitleOfCourtesyCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("TitleOfCourtesy"));
        employeeBirthDateCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("BirthDate"));
        employeeHireDateCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("HireDate"));
        employeeAddressCol.setCellValueFactory(new PropertyValueFactory<Employee,Address>("Address"));
        employeePhoneNumberCol.setCellValueFactory(new PropertyValueFactory<Employee,Number>("PhoneNumber"));
        employeeEmailCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("Email"));
        employeeUserCol.setCellValueFactory(new PropertyValueFactory<Employee,User>("User"));
        
        delEmployeeCol.setCellFactory(
        new Callback<TableColumn<Employee, Void>, TableCell<Employee, Void>>() {
            @Override
            public TableCell<Employee, Void> call(final TableColumn<Employee, Void> param) {
                final TableCell<Employee, Void> cell = new TableCell<Employee, Void>() {
                    
                    Image image = new Image(getClass().getResourceAsStream("/img/icons/trash.png"), 32, 32, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setGraphic(new ImageView(image));
                        btn.setOnAction((ActionEvent event) -> {
                            Connection con;  
                            try {
                                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                                String query = "{call KJTCompany.DELETE_EMPLOYEE(?)}";
                                CallableStatement stmt = con.prepareCall(query);
                                stmt.setInt(1, getTableView().getItems().get(getIndex()).getEmployeeID());
                                stmt.execute();
                                
                                FilteredList<Employee> filteredData1 = new FilteredList<Employee>(FXCollections.observableList(getEmployeeResultSet()));
                                filteredData1.setPredicate(createPredicate(filterInput.getText()));
                                filterInput.textProperty().addListener((observable, oldValue, newValue) ->
                                    filteredData1.setPredicate(createPredicate(newValue))
                                );
                                EmployeeTable.setItems(filteredData1);
                            } catch (SQLException ex) {
                                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
                            }
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
        
        employeeFirstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        employeeLastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        employeeTitleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        employeeTitleOfCourtesyCol.setCellFactory(TextFieldTableCell.forTableColumn());
        employeeEmailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        
        employeeFirstNameCol.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_EMPLOYEE(?,?,?,?,?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getEmployeeID());
                stmt.setString(2, event.getNewValue());
                for(int i = 3; i <= 8; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setFirstName(event.getNewValue());
        });

        employeeLastNameCol.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_EMPLOYEE(?,?,?,?,?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getEmployeeID());
                stmt.setNull(2, OracleTypes.NULL);
                stmt.setString(3, event.getNewValue());
                for(int i = 4; i <= 8; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setLastName(event.getNewValue());
        });
        
        employeeTitleCol.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_EMPLOYEE(?,?,?,?,?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getEmployeeID());
                for(int i = 2; i <= 3; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.setString(4, event.getNewValue());
                for(int i = 5; i <= 8; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setTitle(event.getNewValue());
        });
        
        employeeTitleOfCourtesyCol.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_EMPLOYEE(?,?,?,?,?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getEmployeeID());
                for(int i = 2; i <= 4; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.setString(5, event.getNewValue());
                for(int i = 6; i <= 8; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setTitleOfCourtesy(event.getNewValue());
        });
        
        employeeEmailCol.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_EMPLOYEE(?,?,?,?,?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getEmployeeID());
                for(int i = 2; i <= 7; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.setString(8, event.getNewValue());
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setEmail(event.getNewValue());
        });
        
        EmployeeTable.setItems(filteredData);
    }
    
    private boolean searchFindsOrder(Employee e, String searchText){
        return (e.getFirstName().toLowerCase().contains(searchText.toLowerCase())) ||
                (e.getLastName().toLowerCase().contains(searchText.toLowerCase()));
    }
    
    private Predicate<Employee> createPredicate(String searchText){
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
        ObservableList<Employee> EmployeeResultSet = null;
        try {
            EmployeeResultSet = FXCollections.observableArrayList(getEmployeeResultSet());
        } catch (SQLException ex) {
            Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FilteredList<Employee> filteredData = new FilteredList<Employee>(FXCollections.observableList(EmployeeResultSet));
        
        filterInput.textProperty().addListener((observable, oldValue, newValue) ->
            filteredData.setPredicate(createPredicate(newValue))
        );
        
        EmployeeTable.setEditable(true);
        
        
        employeeIdCol.setCellValueFactory(new PropertyValueFactory<Employee,Number>("EmployeeID"));
        employeeFirstNameCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("FirstName"));
        employeeLastNameCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("LastName"));
        employeeTitleCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("Title"));
        employeeTitleOfCourtesyCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("TitleOfCourtesy"));
        employeeBirthDateCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("BirthDate"));
        employeeHireDateCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("HireDate"));
        employeeAddressCol.setCellValueFactory(new PropertyValueFactory<Employee,Address>("Address"));
        employeePhoneNumberCol.setCellValueFactory(new PropertyValueFactory<Employee,Number>("PhoneNumber"));
        employeeEmailCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("Email"));
        employeeUserCol.setCellValueFactory(new PropertyValueFactory<Employee,User>("User"));
        
        delEmployeeCol.setCellFactory(
        new Callback<TableColumn<Employee, Void>, TableCell<Employee, Void>>() {
            @Override
            public TableCell<Employee, Void> call(final TableColumn<Employee, Void> param) {
                final TableCell<Employee, Void> cell = new TableCell<Employee, Void>() {
                    
                    Image image = new Image(getClass().getResourceAsStream("/img/icons/trash.png"), 32, 32, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setGraphic(new ImageView(image));
                        btn.setOnAction((ActionEvent event) -> {
                            Connection con;  
                            try {
                                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                                String query = "{call KJTCompany.DELETE_EMPLOYEE(?)}";
                                CallableStatement stmt = con.prepareCall(query);
                                stmt.setInt(1, getTableView().getItems().get(getIndex()).getEmployeeID());
                                stmt.execute();
                                
                                FilteredList<Employee> filteredData1 = new FilteredList<Employee>(FXCollections.observableList(getEmployeeResultSet()));
                                filteredData1.setPredicate(createPredicate(filterInput.getText()));
                                filterInput.textProperty().addListener((observable, oldValue, newValue) ->
                                    filteredData1.setPredicate(createPredicate(newValue))
                                );
                                EmployeeTable.setItems(filteredData1);
                            } catch (SQLException ex) {
                                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
                            }
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
        
        employeeFirstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        employeeLastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        employeeTitleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        employeeTitleOfCourtesyCol.setCellFactory(TextFieldTableCell.forTableColumn());
        employeeEmailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        
        employeeFirstNameCol.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_EMPLOYEE(?,?,?,?,?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getEmployeeID());
                stmt.setString(2, event.getNewValue());
                for(int i = 3; i <= 8; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setFirstName(event.getNewValue());
        });

        employeeLastNameCol.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_EMPLOYEE(?,?,?,?,?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getEmployeeID());
                stmt.setNull(2, OracleTypes.NULL);
                stmt.setString(3, event.getNewValue());
                for(int i = 4; i <= 8; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setLastName(event.getNewValue());
        });
        
        employeeTitleCol.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_EMPLOYEE(?,?,?,?,?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getEmployeeID());
                for(int i = 2; i <= 3; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.setString(4, event.getNewValue());
                for(int i = 5; i <= 8; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setTitle(event.getNewValue());
        });
        
        employeeTitleOfCourtesyCol.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_EMPLOYEE(?,?,?,?,?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getEmployeeID());
                for(int i = 2; i <= 4; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.setString(5, event.getNewValue());
                for(int i = 6; i <= 8; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setTitleOfCourtesy(event.getNewValue());
        });
        
        employeeEmailCol.setOnEditCommit((TableColumn.CellEditEvent<Employee, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_EMPLOYEE(?,?,?,?,?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getEmployeeID());
                for(int i = 2; i <= 7; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.setString(8, event.getNewValue());
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setEmail(event.getNewValue());
        });
        
        EmployeeTable.setItems(filteredData);
    }
}
