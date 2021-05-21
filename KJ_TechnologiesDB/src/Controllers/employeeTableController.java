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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    
  
        
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Employee> EmployeeResultSet = null;
        try {
            EmployeeResultSet = FXCollections.observableArrayList(getEmployeeResultSet());
        } catch (SQLException ex) {
            Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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

        EmployeeTable.setItems(EmployeeResultSet);
    }

    
}
