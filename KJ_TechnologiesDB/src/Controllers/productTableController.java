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
import model.Parameter;
import model.Product;
import model.Supplier;
import model.User;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class productTableController implements Initializable{

    @FXML
    private Button goBack;

    @FXML
    private TableView<Product> ProductTable;

    @FXML
    private TableColumn<Product, Number> productIdCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Parameter> productParametersCol;

    @FXML
    private TableColumn<Product, String> productDescCol;

    @FXML
    private TableColumn<Product, Supplier> productSupplierCol;

    @FXML
    private TableColumn<Product, Category> productCategoryCol;

    @FXML
    private TableColumn<Product, Number> productPriceCol;

    @FXML
    private TableColumn<Product, Number> productQuantityCol;

    @FXML
    private TableColumn<Product, Void> delProductCol;

    @FXML
    private Button addProductButton;

    @FXML
    private Button showBestProductsButton;

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
        else if(event.getSource()==addProductButton){
            stage = (Stage) addProductButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/addProduct.fxml"));
        }
        else{
            stage = null;
            root = null;
        }
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }   
    
    private List getBestProductsResultSet() throws SQLException{
        List ll = new LinkedList();
        try{  
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            Connection con=DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","system","oracle");  

            //PRODUCTS
            
            String query = "{call SELECT_TOP_PRODUCTS(?,?)}";
            
            CallableStatement stmt = con.prepareCall(query);
            
            stmt.setInt(1, 10);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.executeQuery();
            
            ResultSet cursor = ((OracleCallableStatement)stmt).getCursor(2);

            try{
            while(cursor.next()){
                int productId = cursor.getInt("ProductID");
                String productName = cursor.getString("ProductName");
                
                int productParametersId = cursor.getInt("ParametersID");
                
                String productDescription = cursor.getString("Description");
                
                int productSupplierId = cursor.getInt("SupplierID");
                
                int productCategoryId = cursor.getInt("CategoryID");
                
                int productPrice = cursor.getInt("Price");               
                int productUnitsInStock = cursor.getInt("UnitsInStock");

                //PARAMETERS

                String parametersQuery = "{call KJTCompany.SELECT_PARAMETERS(?,?)}";

                CallableStatement parametersStmt = con.prepareCall(parametersQuery);
                
                parametersStmt.registerOutParameter(1, OracleTypes.CURSOR);
                parametersStmt.setInt(2, productParametersId);
                parametersStmt.executeQuery();

                ResultSet parameterCursor = ((OracleCallableStatement)parametersStmt).getCursor(1);
                
                Parameter productParameter = new Parameter();
                
                //SUPPLIER
                
                String supplierQuery = "{call KJTCompany.SELECT_SUPPLIERS_FOR_PRODUCT(?,?)}";

                CallableStatement supplierStmt = con.prepareCall(supplierQuery);
                
                supplierStmt.registerOutParameter(1, OracleTypes.CURSOR);
                supplierStmt.setInt(2, productSupplierId);
                supplierStmt.executeQuery();

                ResultSet supplierCursor = ((OracleCallableStatement)supplierStmt).getCursor(1);
                
                Supplier productSupplier = new Supplier();
                
                //CATEGORY
                
                String categoryQuery = "{call KJTCompany.SELECT_CATEGORIES_FOR_PRODUCTS(?,?)}";

                CallableStatement categoryStmt = con.prepareCall(categoryQuery);
                
                categoryStmt.registerOutParameter(1, OracleTypes.CURSOR);
                categoryStmt.setInt(2, productCategoryId);
                categoryStmt.executeQuery();

                ResultSet categoryCursor = ((OracleCallableStatement)categoryStmt).getCursor(1);
                
                Category productCategory = new Category();
                
                
                //PARAMETERS
                try{
                    while(parameterCursor.next()){
                        productParameter.setParameterID(parameterCursor.getInt("ParametersID"));
                        productParameter.setModel(parameterCursor.getString("Model"));
                        productParameter.setType(parameterCursor.getString("Type"));
                        productParameter.setClock(parameterCursor.getDouble("Clock"));
                        productParameter.setSocket(parameterCursor.getString("Socket"));
                        productParameter.setChipset(parameterCursor.getString("Chipset"));
                        productParameter.setNumOfCores(parameterCursor.getInt("NumOfCores"));
                        productParameter.setNumOfThreads(parameterCursor.getInt("NumOfThreads"));
                        productParameter.setCapacity(parameterCursor.getDouble("Capacity"));
                        productParameter.setCache(parameterCursor.getInt("Cache"));
                        productParameter.setVram(parameterCursor.getInt("VRAM"));
                        productParameter.setFormat(parameterCursor.getString("Format"));
                        productParameter.setInterface(parameterCursor.getString("Interface"));
                        productParameter.setInputs(parameterCursor.getString("Inputs"));
                        productParameter.setReadSpeed(parameterCursor.getInt("ReadSpeed"));
                        productParameter.setWriteSpeed(parameterCursor.getInt("WriteSpeed"));
                        productParameter.setMTBF(parameterCursor.getInt("MTBF"));
                        productParameter.setTDP(parameterCursor.getInt("TDP"));
                        productParameter.setColor(parameterCursor.getString("Color"));
                        productParameter.setHeight(parameterCursor.getInt("Height"));
                        productParameter.setWidth(parameterCursor.getInt("Width"));
                        productParameter.setLength(parameterCursor.getInt("Length"));
                        productParameter.setAccessories(parameterCursor.getString("Accessories"));
                        productParameter.setWarranty(parameterCursor.getInt("Warranty"));
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //SUPPLIER

                try{
                    while(supplierCursor.next()){
                        productSupplier.setSupplierID(supplierCursor.getInt("SupplierID"));
                        productSupplier.setCompanyName(supplierCursor.getString("CompanyName"));
                        productSupplier.setAddress(null);
                        productSupplier.setPhoneNumber(supplierCursor.getInt("PhoneNumber"));
                        productSupplier.setEmail(supplierCursor.getString("Email"));
                        productSupplier.setWebPage(supplierCursor.getString("WebPage"));
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //Category

                try{
                    while(categoryCursor.next()){
                        productCategory.setCategoryId(categoryCursor.getInt("CategoryID"));
                        productCategory.setCategoryName(categoryCursor.getString("CategoryName"));
                        productCategory.setDescription(categoryCursor.getString("Description"));
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Product product = new Product(productId, productName, productParameter, productDescription, productSupplier, productCategory, productPrice, productUnitsInStock);
                
                supplierCursor.close();
                categoryCursor.close();
                parameterCursor.close();
                ll.add(product); 
            }
            }
                catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            cursor.close();
            con.close();  
        }
        catch(SQLException e){ 
            System.out.println(e);
        }
        return ll;
    }

        private List getProductResultSet() throws SQLException{
        List ll = new LinkedList();
        try{  
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            Connection con=DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","system","oracle");  

            //PRODUCTS
            
            String query = "{call KJTCompany.SELECT_PRODUCTS(?)}";
            
            CallableStatement stmt = con.prepareCall(query);
            
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.executeQuery();
            
            ResultSet cursor = ((OracleCallableStatement)stmt).getCursor(1);

            try{
            while(cursor.next()){
                int productId = cursor.getInt("ProductID");
                String productName = cursor.getString("ProductName");
                
                int productParametersId = cursor.getInt("ParametersID");
                
                String productDescription = cursor.getString("Description");
                
                int productSupplierId = cursor.getInt("SupplierID");
                
                int productCategoryId = cursor.getInt("CategoryID");
                
                int productPrice = cursor.getInt("Price");               
                int productUnitsInStock = cursor.getInt("UnitsInStock");

                //PARAMETERS

                String parametersQuery = "{call KJTCompany.SELECT_PARAMETERS(?,?)}";

                CallableStatement parametersStmt = con.prepareCall(parametersQuery);
                
                
                parametersStmt.registerOutParameter(1, OracleTypes.CURSOR);
                parametersStmt.setInt(2, productParametersId);
                parametersStmt.executeQuery();

                ResultSet parameterCursor = ((OracleCallableStatement)parametersStmt).getCursor(1);
                
                Parameter productParameter = new Parameter();
                
                //SUPPLIER
                
                String supplierQuery = "{call KJTCompany.SELECT_SUPPLIERS_FOR_PRODUCT(?,?)}";

                CallableStatement supplierStmt = con.prepareCall(supplierQuery);
                
                supplierStmt.registerOutParameter(1, OracleTypes.CURSOR);
                supplierStmt.setInt(2, productSupplierId);
                supplierStmt.executeQuery();

                ResultSet supplierCursor = ((OracleCallableStatement)supplierStmt).getCursor(1);
                
                Supplier productSupplier = new Supplier();
                
                //CATEGORY
                
                String categoryQuery = "{call KJTCompany.SELECT_CATEGORIES_FOR_PRODUCTS(?,?)}";

                CallableStatement categoryStmt = con.prepareCall(categoryQuery);
                
                categoryStmt.registerOutParameter(1, OracleTypes.CURSOR);
                categoryStmt.setInt(2, productCategoryId);
                categoryStmt.executeQuery();

                ResultSet categoryCursor = ((OracleCallableStatement)categoryStmt).getCursor(1);
                
                Category productCategory = new Category();
                
                //PARAMETERS
                try{
                    while(parameterCursor.next()){
                        productParameter.setParameterID(parameterCursor.getInt("ParametersID"));
                        productParameter.setModel(parameterCursor.getString("Model"));
                        productParameter.setType(parameterCursor.getString("Type"));
                        productParameter.setClock(parameterCursor.getDouble("Clock"));
                        productParameter.setSocket(parameterCursor.getString("Socket"));
                        productParameter.setChipset(parameterCursor.getString("Chipset"));
                        productParameter.setNumOfCores(parameterCursor.getInt("NumOfCores"));
                        productParameter.setNumOfThreads(parameterCursor.getInt("NumOfThreads"));
                        productParameter.setCapacity(parameterCursor.getDouble("Capacity"));
                        productParameter.setCache(parameterCursor.getInt("Cache"));
                        productParameter.setVram(parameterCursor.getInt("VRAM"));
                        productParameter.setFormat(parameterCursor.getString("Format"));
                        productParameter.setInterface(parameterCursor.getString("Interface"));
                        productParameter.setInputs(parameterCursor.getString("Inputs"));
                        productParameter.setReadSpeed(parameterCursor.getInt("ReadSpeed"));
                        productParameter.setWriteSpeed(parameterCursor.getInt("WriteSpeed"));
                        productParameter.setMTBF(parameterCursor.getInt("MTBF"));
                        productParameter.setTDP(parameterCursor.getInt("TDP"));
                        productParameter.setColor(parameterCursor.getString("Color"));
                        productParameter.setHeight(parameterCursor.getInt("Height"));
                        productParameter.setWidth(parameterCursor.getInt("Width"));
                        productParameter.setLength(parameterCursor.getInt("Length"));
                        productParameter.setAccessories(parameterCursor.getString("Accessories"));
                        productParameter.setWarranty(parameterCursor.getInt("Warranty"));
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //SUPPLIER

                try{
                    while(supplierCursor.next()){
                        productSupplier.setSupplierID(supplierCursor.getInt("SupplierID"));
                        productSupplier.setCompanyName(supplierCursor.getString("CompanyName"));
                        productSupplier.setAddress(null);
                        productSupplier.setPhoneNumber(supplierCursor.getInt("PhoneNumber"));
                        productSupplier.setEmail(supplierCursor.getString("Email"));
                        productSupplier.setWebPage(supplierCursor.getString("WebPage"));
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //Category

                try{
                    while(categoryCursor.next()){
                        productCategory.setCategoryId(categoryCursor.getInt("CategoryID"));
                        productCategory.setCategoryName(categoryCursor.getString("CategoryName"));
                        productCategory.setDescription(categoryCursor.getString("Description"));
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Product product = new Product(productId, productName, productParameter, productDescription, productSupplier, productCategory, productPrice, productUnitsInStock);
                
                supplierCursor.close();
                categoryCursor.close();
                parameterCursor.close();
                ll.add(product); 
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
        
    @FXML
    public void setBestProducts(){
        ObservableList<Product> ProductResultSet = null;
        try {
            ProductResultSet = FXCollections.observableArrayList(getBestProductsResultSet());
        } catch (SQLException ex) {
            Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FilteredList<Product> filteredData = new FilteredList<Product>(FXCollections.observableList(ProductResultSet));
        
        filterInput.textProperty().addListener((observable, oldValue, newValue) ->
            filteredData.setPredicate(createPredicate(newValue))
        );
        
        ProductTable.setEditable(true);
        
        productIdCol.setCellValueFactory(new PropertyValueFactory<Product,Number>("ProductID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<Product,String>("ProductName"));
        productParametersCol.setCellValueFactory(new PropertyValueFactory<Product,Parameter>("Parameter"));
        productDescCol.setCellValueFactory(new PropertyValueFactory<Product,String>("Description"));
        productSupplierCol.setCellValueFactory(new PropertyValueFactory<Product,Supplier>("Supplier"));
        productCategoryCol.setCellValueFactory(new PropertyValueFactory<Product,Category>("Category"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<Product,Number>("Price"));
        productQuantityCol.setCellValueFactory(new PropertyValueFactory<Product,Number>("UnitsInStock"));
        
        delProductCol.setCellFactory(
        new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {
                    
                    Image image = new Image(getClass().getResourceAsStream("/img/icons/trash.png"), 32, 32, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setGraphic(new ImageView(image));
                        btn.setOnAction((ActionEvent event) -> {
                            Connection con;  
                            try {
                                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                                String query = "{call KJTCompany.DELETE_PRODUCT(?)}";
                                CallableStatement stmt = con.prepareCall(query);
                                stmt.setInt(1, getTableView().getItems().get(getIndex()).getProductID());
                                stmt.execute();
                                
                                FilteredList<Product> filteredData1 = new FilteredList<Product>(FXCollections.observableList(getProductResultSet()));
                                filteredData1.setPredicate(createPredicate(filterInput.getText()));
                                filterInput.textProperty().addListener((observable, oldValue, newValue) ->
                                    filteredData1.setPredicate(createPredicate(newValue))
                                );
                                ProductTable.setItems(filteredData1);
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
        
        productNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        productDescCol.setCellFactory(TextFieldTableCell.forTableColumn());
        
        productNameCol.setOnEditCommit((TableColumn.CellEditEvent<Product, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_PRODUCT(?,?,?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getProductID());
                stmt.setString(2, event.getNewValue());
                for(int i = 3; i <= 6; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setProductName(event.getNewValue());
        });
        
        productDescCol.setOnEditCommit((TableColumn.CellEditEvent<Product, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_PRODUCT(?,?,?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getProductID());
                stmt.setNull(2, OracleTypes.NULL);
                stmt.setString(3, event.getNewValue());
                for(int i = 4; i <= 6; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setDescription(event.getNewValue());
        });

        ProductTable.setItems(filteredData);
    }
    
    private boolean searchFindsOrder(Product p, String searchText){
        if(p.getDescription() != null){
        return (p.getProductName().toLowerCase().contains(searchText.toLowerCase())) ||
                (p.getDescription().toLowerCase().contains(searchText.toLowerCase())) ||
                (Integer.valueOf(p.getPrice()).equals(searchText.toLowerCase()));
        }
        else{
            return (p.getProductName().toLowerCase().contains(searchText.toLowerCase())) ||
                (Integer.valueOf(p.getPrice()).equals(searchText.toLowerCase()));
        }
    }
    
    private Predicate<Product> createPredicate(String searchText){
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
        ObservableList<Product> ProductResultSet = null;
        try {
            ProductResultSet = FXCollections.observableArrayList(getProductResultSet());
        } catch (SQLException ex) {
            Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FilteredList<Product> filteredData = new FilteredList<Product>(FXCollections.observableList(ProductResultSet));
        
        filterInput.textProperty().addListener((observable, oldValue, newValue) ->
            filteredData.setPredicate(createPredicate(newValue))
        );
        
        ProductTable.setEditable(true);
        
        
        productIdCol.setCellValueFactory(new PropertyValueFactory<Product,Number>("ProductID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<Product,String>("ProductName"));
        productParametersCol.setCellValueFactory(new PropertyValueFactory<Product,Parameter>("Parameter"));
        productDescCol.setCellValueFactory(new PropertyValueFactory<Product,String>("Description"));
        productSupplierCol.setCellValueFactory(new PropertyValueFactory<Product,Supplier>("Supplier"));
        productCategoryCol.setCellValueFactory(new PropertyValueFactory<Product,Category>("Category"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<Product,Number>("Price"));
        productQuantityCol.setCellValueFactory(new PropertyValueFactory<Product,Number>("UnitsInStock"));
        
        delProductCol.setCellFactory(
        new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {
                    
                    Image image = new Image(getClass().getResourceAsStream("/img/icons/trash.png"), 32, 32, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setGraphic(new ImageView(image));
                        btn.setOnAction((ActionEvent event) -> {
                            Connection con;  
                            try {
                                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                                String query = "{call KJTCompany.DELETE_PRODUCT(?)}";
                                CallableStatement stmt = con.prepareCall(query);
                                stmt.setInt(1, getTableView().getItems().get(getIndex()).getProductID());
                                stmt.execute();
                                
                                FilteredList<Product> filteredData1 = new FilteredList<Product>(FXCollections.observableList(getProductResultSet()));
                                filteredData1.setPredicate(createPredicate(filterInput.getText()));
                                filterInput.textProperty().addListener((observable, oldValue, newValue) ->
                                    filteredData1.setPredicate(createPredicate(newValue))
                                );
                                ProductTable.setItems(filteredData1);
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
        
        productNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        productDescCol.setCellFactory(TextFieldTableCell.forTableColumn());
        
        productNameCol.setOnEditCommit((TableColumn.CellEditEvent<Product, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_PRODUCT(?,?,?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getProductID());
                stmt.setString(2, event.getNewValue());
                for(int i = 3; i <= 6; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setProductName(event.getNewValue());
        });
        
        productDescCol.setOnEditCommit((TableColumn.CellEditEvent<Product, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_PRODUCT(?,?,?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getProductID());
                stmt.setNull(2, OracleTypes.NULL);
                stmt.setString(3, event.getNewValue());
                for(int i = 4; i <= 6; i++)
                {
                    stmt.setNull(i, OracleTypes.NULL);
                }
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setDescription(event.getNewValue());
        });

        ProductTable.setItems(filteredData);
    }
    
}
