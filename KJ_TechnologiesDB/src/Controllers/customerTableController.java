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
import model.*;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class customerTableController implements Initializable{

    @FXML
    private Button goBack;

    @FXML
    private TableView<Customer> CustomerTable;

    @FXML
    private TableColumn<Customer, Number> customerIdCol;

    @FXML
    private TableColumn<Customer, String> customerFirstNameCol;

    @FXML
    private TableColumn<Customer, String> customerLastNameCol;

    @FXML
    private TableColumn<Customer, String> customerTitleCol;

    @FXML
    private TableColumn<Customer, Number> customerNipCol;

    @FXML
    private TableColumn<Customer, Address> customerAddressCol;

    @FXML
    private TableColumn<Customer, Number> customerPhoneNumberCol;

    @FXML
    private TableColumn<Customer, String> customerEmailCol;

    @FXML
    private TableColumn<Customer, User> customerUserCol;

    @FXML
    private TableColumn<Customer, Void> delCustomerCol;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button showBestCustomersButton;

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
        else if(event.getSource()==addCustomerButton){
            stage = (Stage) addCustomerButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/addCustomer.fxml"));
        }
        else{
            stage = null;
            root = null;
        }
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }   

        private List getCustomersResultSet() throws SQLException{
        List ll = new LinkedList();
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
            stmt.executeUpdate();
            
            ResultSet cursor = ((OracleCallableStatement)stmt).getCursor(1);
            
            while(cursor.next()){
                int customerId = cursor.getInt("CustomerID");
                String customerFirstName = cursor.getString("FirstName");
                String customerLastName = cursor.getString("LastName");
                String customerTitle = cursor.getString("ContactTitle");
                int customerNIP = cursor.getInt("NIP");
                Address customerAddress = cursor.getObject(, Address);
                int customerPhoneNumber = cursor.getInt("PhoneNumber");
                String customerEmail = cursor.getString("Email");
                int customerUserID = cursor.getInt("UserID");
                
                
                Customer customer = new Customer(customerId, customerFirstName, customerLastName, customerTitle, customerNIP, )
                ll.add(category); 
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
        ObservableList<Customer> CustomersResultSet = null;
        try {
            CustomersResultSet = FXCollections.observableArrayList(getCustomersResultSet());
        } catch (SQLException ex) {
            Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        CustomerTable.setEditable(true);
        
        
        customerIdCol.setCellValueFactory(new PropertyValueFactory<Customer,Number>("CusomerId"));
        customerFirstNameCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("FirstName"));
        customerLastNameCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("LastName"));
        customerTitleCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("ContactTitle"));
        customerNipCol.setCellValueFactory(new PropertyValueFactory<Customer,Number>("NIP"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<Customer,Address>("AddressID"));
        customerPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<Customer,Number>("PhoneNumber"));
        customerEmailCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("Email"));
        customerUserCol.setCellValueFactory(new PropertyValueFactory<Customer,User>("UserID"));
        
        delCustomerCol.setCellFactory(
        new Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>>() {
            @Override
            public TableCell<Customer, Void> call(final TableColumn<Customer, Void> param) {
                final TableCell<Customer, Void> cell = new TableCell<Customer, Void>() {
                    
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
        
        customerFirstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        customerLastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        customerTitleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        customerEmailCol.setCellFactory(TextFieldTableCell.forTableColumn());

        CustomerTable.setItems(CustomersResultSet);
    }
    
    
}
