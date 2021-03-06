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
import model.Employee;
import model.Supplier;
import model.User;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class supplierTableController implements Initializable{

    @FXML
    private Button goBack;

    @FXML
    private TableView<Supplier> SupplierTable;

    @FXML
    private TableColumn<Supplier, Number> supplierIdCol;

    @FXML
    private TableColumn<Supplier, String> supplierCompanyNameCol;

    @FXML
    private TableColumn<Supplier, Address> supplierAddressCol;

    @FXML
    private TableColumn<Supplier, Number> supplierPhoneNumberCol;

    @FXML
    private TableColumn<Supplier, String> supplierEmailCol;

    @FXML
    private TableColumn<Supplier, String> supplierWebpageCol;

    @FXML
    private TableColumn<Supplier, Void> delSupplierCol;

    @FXML
    private Button addSupplierButton;

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
        else if(event.getSource()==addSupplierButton){
            stage = (Stage) addSupplierButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/addSupplier.fxml"));
        }
        else{
            stage = null;
            root = null;
        }
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }   

        private List getSupplierResultSet() throws SQLException{
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
            
            String query = "{call KJTCompany.SELECT_SUPPLIERS(?)}";
            
            CallableStatement stmt = con.prepareCall(query);
            
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.executeQuery();
            
            ResultSet cursor = ((OracleCallableStatement)stmt).getCursor(1);

            try{
            while(cursor.next()){
                int supplierId = cursor.getInt("SupplierID");
                String supplierCompanyName = cursor.getString("CompanyName");
                
                int supplierAddressId= cursor.getInt("AddressID");
                
                int supplierPhoneNumber = cursor.getInt("PhoneNumber");
                String supplierEmail = cursor.getString("Email");
                String supplierWebpage= cursor.getString("WebPage");
                
                //ADDRESSES

                String addressQuery = "{call KJTCompany.SELECT_ADDRESSES(?,?)}";

                CallableStatement addressStmt = con.prepareCall(addressQuery);
                
                addressStmt.registerOutParameter(1, OracleTypes.CURSOR);
                addressStmt.setInt(2, supplierAddressId);
                addressStmt.executeQuery();

                ResultSet addressCursor = ((OracleCallableStatement)addressStmt).getCursor(1);
                
                Address supplierAddress = new Address();
                
                try{
                    while(addressCursor.next()){
                        supplierAddress.setAddressID(addressCursor.getInt("AddressID"));
                        supplierAddress.setAddress(addressCursor.getString("Address"));
                        supplierAddress.setCity(addressCursor.getString("City"));
                        supplierAddress.setRegion(addressCursor.getString("Region"));
                        supplierAddress.setPostalCode(addressCursor.getString("Postalcode"));
                        supplierAddress.setCountry(addressCursor.getString("Country"));

                    }
                }catch (SQLException ex) {
                    Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
                }

                Supplier supplier = new Supplier(supplierId, supplierCompanyName, supplierAddress, supplierPhoneNumber, supplierEmail, supplierWebpage);
                
                ll.add(supplier); 
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
    
    private boolean searchFindsOrder(Supplier s, String searchText){
        if(s.getEmail() != null){
        return (s.getCompanyName().toLowerCase().contains(searchText.toLowerCase())) ||
                (s.getEmail().toLowerCase().contains(searchText.toLowerCase()));
        }
        else{
            return (s.getCompanyName().toLowerCase().contains(searchText.toLowerCase()));
        }
    }
    
    private Predicate<Supplier> createPredicate(String searchText){
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
        ObservableList<Supplier> SupplierResultSet = null;
        try {
            SupplierResultSet = FXCollections.observableArrayList(getSupplierResultSet());
        } catch (SQLException ex) {
            Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FilteredList<Supplier> filteredData = new FilteredList<Supplier>(FXCollections.observableList(SupplierResultSet));
        
        filterInput.textProperty().addListener((observable, oldValue, newValue) ->
            filteredData.setPredicate(createPredicate(newValue))
        );
        
        SupplierTable.setEditable(true);
        
        
        supplierIdCol.setCellValueFactory(new PropertyValueFactory<Supplier,Number>("SupplierID"));
        supplierCompanyNameCol.setCellValueFactory(new PropertyValueFactory<Supplier,String>("CompanyName"));
        supplierAddressCol.setCellValueFactory(new PropertyValueFactory<Supplier,Address>("Address"));
        supplierPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<Supplier,Number>("PhoneNumber"));
        supplierEmailCol.setCellValueFactory(new PropertyValueFactory<Supplier,String>("Email"));
        supplierWebpageCol.setCellValueFactory(new PropertyValueFactory<Supplier,String>("WebPage"));
        
        delSupplierCol.setCellFactory(
        new Callback<TableColumn<Supplier, Void>, TableCell<Supplier, Void>>() {
            @Override
            public TableCell<Supplier, Void> call(final TableColumn<Supplier, Void> param) {
                final TableCell<Supplier, Void> cell = new TableCell<Supplier, Void>() {
                    
                    Image image = new Image(getClass().getResourceAsStream("/img/icons/trash.png"), 32, 32, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setGraphic(new ImageView(image));
                        btn.setOnAction((ActionEvent event) -> {
                            Connection con;  
                            try {
                                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                                String query = "{call KJTCompany.DELETE_SUPPLIER(?)}";
                                CallableStatement stmt = con.prepareCall(query);
                                stmt.setInt(1, getTableView().getItems().get(getIndex()).getSupplierID());
                                stmt.execute();
                                
                                FilteredList<Supplier> filteredData1 = new FilteredList<Supplier>(FXCollections.observableList(getSupplierResultSet()));
                                filteredData1.setPredicate(createPredicate(filterInput.getText()));
                                filterInput.textProperty().addListener((observable, oldValue, newValue) ->
                                    filteredData1.setPredicate(createPredicate(newValue))
                                );
                                SupplierTable.setItems(filteredData1);
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
        
        supplierCompanyNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        supplierEmailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        supplierWebpageCol.setCellFactory(TextFieldTableCell.forTableColumn());
        
        supplierCompanyNameCol.setOnEditCommit((TableColumn.CellEditEvent<Supplier, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_SUPPLIER(?,?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getSupplierID());
                stmt.setString(2, event.getNewValue());
                stmt.setNull(3, OracleTypes.NULL);
                stmt.setNull(4, OracleTypes.NULL);
                stmt.setNull(5, OracleTypes.NULL);
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setCompanyName(event.getNewValue());
        });
        
        supplierEmailCol.setOnEditCommit((TableColumn.CellEditEvent<Supplier, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_SUPPLIER(?,?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getSupplierID());
                stmt.setString(4, event.getNewValue());
                stmt.setNull(3, OracleTypes.NULL);
                stmt.setNull(2, OracleTypes.NULL);
                stmt.setNull(5, OracleTypes.NULL);
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setEmail(event.getNewValue());
        });
        
        supplierWebpageCol.setOnEditCommit((TableColumn.CellEditEvent<Supplier, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_SUPPLIER(?,?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getSupplierID());
                stmt.setString(5, event.getNewValue());
                stmt.setNull(3, OracleTypes.NULL);
                stmt.setNull(4, OracleTypes.NULL);
                stmt.setNull(2, OracleTypes.NULL);
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setWebPage(event.getNewValue());
        });

        SupplierTable.setItems(filteredData);
    }

}
