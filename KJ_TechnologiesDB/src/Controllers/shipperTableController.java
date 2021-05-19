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
import model.Shipper;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class shipperTableController implements Initializable{

    @FXML
    private Button goBack;

    @FXML
    private TableView<Shipper> ShipperTable;

    @FXML
    private TableColumn<Shipper, Number> shipperIdCol;

    @FXML
    private TableColumn<Shipper, String> shipperCompanyNameCol;

    @FXML
    private TableColumn<Shipper, Number> shipperPhoneNumberCol;

    @FXML
    private TableColumn<Shipper, String> shipperEmailCol;

    @FXML
    private TableColumn<Shipper, Void> delShipperCol;

    @FXML
    private Button addShipperButton;

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
        else if(event.getSource()==addShipperButton){
            stage = (Stage) addShipperButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/addShipper.fxml"));
        }
        else{
            stage = null;
            root = null;
        }
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }   

        private List getShippersResultSet() throws SQLException{
        List ll = new LinkedList();
        try{  
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            Connection con=DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","system","oracle");  

            String query = "{call KJTCompany.SELECT_SHIPPERS(?)}";
            
            CallableStatement stmt = con.prepareCall(query);
            
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.executeUpdate();
            
            ResultSet cursor = ((OracleCallableStatement)stmt).getCursor(1);
            
            while(cursor.next()){
                int shipperId = cursor.getInt("ShipperID");
                String companyName = cursor.getString("CompanyName");
                int phoneNumber = cursor.getInt("PhoneNumber");
                String email = cursor.getString("Email");
                
                Shipper shipper = new Shipper(shipperId,companyName,phoneNumber,email);
                ll.add(shipper); 
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
        ObservableList<Shipper> ShippersResultSet = null;
        try {
            ShippersResultSet = FXCollections.observableArrayList(getShippersResultSet());
        } catch (SQLException ex) {
            Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ShipperTable.setEditable(true);
        
        
        shipperIdCol.setCellValueFactory(new PropertyValueFactory<Shipper,Number>("ShipperID"));
        shipperCompanyNameCol.setCellValueFactory(new PropertyValueFactory<Shipper,String>("CompanyName"));
        shipperPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<Shipper, Number>("PhoneNumber"));
        shipperEmailCol.setCellValueFactory(new PropertyValueFactory<Shipper,String>("Email"));
        
        delShipperCol.setCellFactory(
            new Callback<TableColumn<Shipper, Void>, TableCell<Shipper, Void>>() {
                @Override
                public TableCell<Shipper, Void> call(final TableColumn<Shipper, Void> param) {
                    final TableCell<Shipper, Void> cell = new TableCell<Shipper, Void>() {

                        Image image = new Image(getClass().getResourceAsStream("/img/icons/trash.png"), 32, 32, false, false);
                        private final Button btn = new Button();

                        {
                            btn.setGraphic(new ImageView(image));
                            btn.setOnAction((ActionEvent event) -> {
                                Connection con;  
                                try {
                                    con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                                    String query = "{call KJTCompany.DELETE_SHIPPER(?)}";
                                    CallableStatement stmt = con.prepareCall(query);
                                    stmt.setInt(1, getTableView().getItems().get(getIndex()).getShipperID());
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
        
        shipperCompanyNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        shipperEmailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        
        shipperCompanyNameCol.setOnEditCommit((TableColumn.CellEditEvent<Shipper, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_SHIPPER(?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getShipperID());
                stmt.setString(2, event.getNewValue());
                stmt.setNull(3, OracleTypes.NULL);
                stmt.setNull(4, OracleTypes.NULL);
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        shipperEmailCol.setOnEditCommit((TableColumn.CellEditEvent<Shipper, String> event) -> {
            Connection con;  
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.UPDATE_SHIPPER(?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                stmt.setInt(1, event.getTableView().getItems().get(event.getTablePosition().getRow()).getShipperID());
                stmt.setNull(2, OracleTypes.NULL);
                stmt.setNull(3, OracleTypes.NULL);
                stmt.setString(4, event.getNewValue());
                stmt.execute();
            } catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        ShipperTable.setItems(ShippersResultSet);
    }
}
