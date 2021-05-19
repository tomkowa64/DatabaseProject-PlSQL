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
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
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
import model.Category;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class categoryTableController implements Initializable{

    @FXML
    private Button goBack;

    @FXML
    private TableView<Category> CategoryTable;

    @FXML
    private TableColumn<Category, Number> categoryIdCol;

    @FXML
    private TableColumn<Category, String> categoryNameCol;

    @FXML
    private TableColumn<Category, String> categoryDescCol;

    @FXML
    private TableColumn<Category, Void> delCategoryCol;

    @FXML
    private Button addCategoryButton;

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
        else if(event.getSource()==addCategoryButton){
            stage = (Stage) addCategoryButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/addCategory.fxml"));
        }
        else{
            stage = null;
            root = null;
        }
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }   

    private List getCategoriesResultSet() throws SQLException{
        List ll = new LinkedList();
        try{  
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            Connection con=DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","system","oracle");  

            String query = "{call KJTCompany.SELECT_CATEGORIES(?)}";
            
            CallableStatement stmt = con.prepareCall(query);
            
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.executeUpdate();
            
            ResultSet cursor = ((OracleCallableStatement)stmt).getCursor(1);
            
            while(cursor.next()){
                int catId = cursor.getInt("categoryId");
                String catName = cursor.getString("categoryName");
                String catDesc = cursor.getString("Description");
                
                Category category = new Category(catId,catName,catDesc);
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
        ObservableList<Category> CategoriesResultSet = null;
        try {
            CategoriesResultSet = FXCollections.observableArrayList(getCategoriesResultSet());
        } catch (SQLException ex) {
            Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        CategoryTable.setEditable(true);
        
        
        categoryIdCol.setCellValueFactory(new PropertyValueFactory<Category,Number>("CategoryId"));
        categoryNameCol.setCellValueFactory(new PropertyValueFactory<Category,String>("CategoryName"));
        categoryDescCol.setCellValueFactory(new PropertyValueFactory<Category,String>("Description"));
        
        delCategoryCol.setCellFactory(
        new Callback<TableColumn<Category, Void>, TableCell<Category, Void>>() {
            @Override
            public TableCell<Category, Void> call(final TableColumn<Category, Void> param) {
                final TableCell<Category, Void> cell = new TableCell<Category, Void>() {
                    
                    Image image = new Image(getClass().getResourceAsStream("/img/icons/trash.png"), 32, 32, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setGraphic(new ImageView(image));
                        btn.setOnAction((ActionEvent event) -> {
                            Connection con;  
                            try {
                                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                                String query = "{call KJTCompany.DELETE_CATEGORY(?)}";
                                CallableStatement stmt = con.prepareCall(query);
                                stmt.setInt(1, getTableView().getItems().get(getIndex()).getCategoryId());
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
        
        categoryNameCol.setOnEditCommit((TableColumn.CellEditEvent<Category, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_CATEGORY(?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getCategoryId());
                stmt.setString(2, event.getNewValue());
                stmt.setNull(3, OracleTypes.NULL);
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        categoryDescCol.setOnEditCommit((TableColumn.CellEditEvent<Category, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_CATEGORY(?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getCategoryId());
                stmt.setNull(2, OracleTypes.NULL);
                stmt.setString(3, event.getNewValue());
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        categoryNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryDescCol.setCellFactory(TextFieldTableCell.forTableColumn());

        CategoryTable.setItems(CategoriesResultSet);
    }
}
