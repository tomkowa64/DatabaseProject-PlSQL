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
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Category;
import model.Customer;
import model.Supplier;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class addProductController implements Initializable{

    @FXML
    private Button goBack;

    @FXML
    private TextField productPriceInput;

    @FXML
    private PasswordField productQuantityInput;

    @FXML
    private TextField productNameInput;

    @FXML
    private Button addProductButton;

    @FXML
    private TextArea productDescInput;

    @FXML
    private ChoiceBox<Supplier> supplierChoiceBox;

    @FXML
    private ChoiceBox<Category> categoryChoiceBox;

    @FXML
    private Button addParameterInput;

    @FXML
    private Pane ParametersPane;

    @FXML
    private ChoiceBox<String> parameterChoiceBox;

    @FXML
    private TextField parameterValueInput;
    
    private List addedParameters = new ArrayList<String>();
    private List parametersValues = new ArrayList<String>();
    
    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        if(event.getSource()==goBack){
            stage = (Stage) goBack.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/ProductTable.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if(event.getSource()==addProductButton){
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            
            if(!productNameInput.getText().isEmpty() &&
               !productPriceInput.getText().isEmpty() &&
               !productQuantityInput.getText().isEmpty() &&
               !addedParameters.isEmpty())
            {
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
                String query = "{call KJTCompany.INSERT_PRODUCT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                CallableStatement stmt = con.prepareCall(query);
                
                stmt.setString(1, productNameInput.getText());
                stmt.setString(2, productDescInput.getText());
                
                if(categoryChoiceBox.getValue() == null)
                {
                    stmt.setNull(3, OracleTypes.NULL);
                }
                else
                {
                    stmt.setInt(3, categoryChoiceBox.getValue().getCategoryId());
                }
                
                if(supplierChoiceBox.getValue() == null)
                {
                    stmt.setNull(4, OracleTypes.NULL);
                }
                else
                {
                    stmt.setInt(4, supplierChoiceBox.getValue().getSupplierID());
                }
                
                stmt.setInt(5, Integer.parseInt(productPriceInput.getText()));
                stmt.setInt(6, Integer.parseInt(productQuantityInput.getText()));
                
                if(addedParameters.contains("Model"))
                {
                    stmt.setString(7, parametersValues.get(addedParameters.indexOf("Model")).toString());
                }
                else
                {
                    stmt.setNull(7, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Typ"))
                {
                    stmt.setString(8, parametersValues.get(addedParameters.indexOf("Typ")).toString());
                }
                else
                {
                    stmt.setNull(8, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Taktowanie zegara"))
                {
                    stmt.setInt(9, Integer.parseInt(parametersValues.get(addedParameters.indexOf("Taktowanie zegara")).toString()));
                }
                else
                {
                    stmt.setNull(9, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Socket"))
                {
                    stmt.setString(10, parametersValues.get(addedParameters.indexOf("Socket")).toString());
                }
                else
                {
                    stmt.setNull(10, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Chipset"))
                {
                    stmt.setString(11, parametersValues.get(addedParameters.indexOf("Chipset")).toString());
                }
                else
                {
                    stmt.setNull(11, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Liczba rdzeni"))
                {
                    stmt.setInt(12, Integer.parseInt(parametersValues.get(addedParameters.indexOf("Liczba rdzeni")).toString()));
                }
                else
                {
                    stmt.setNull(12, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Liczba wątków"))
                {
                    stmt.setInt(13, Integer.parseInt(parametersValues.get(addedParameters.indexOf("Liczba wątków")).toString()));
                }
                else
                {
                    stmt.setNull(13, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Pojemność"))
                {
                    stmt.setInt(14, Integer.parseInt(parametersValues.get(addedParameters.indexOf("Pojemność")).toString()));
                }
                else
                {
                    stmt.setNull(14, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Cache"))
                {
                    stmt.setInt(15, Integer.parseInt(parametersValues.get(addedParameters.indexOf("Cache")).toString()));
                }
                else
                {
                    stmt.setNull(15, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("VRAM"))
                {
                    stmt.setInt(16, Integer.parseInt(parametersValues.get(addedParameters.indexOf("VRAM")).toString()));
                }
                else
                {
                    stmt.setNull(16, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Format"))
                {
                    stmt.setString(17, parametersValues.get(addedParameters.indexOf("Format")).toString());
                }
                else
                {
                    stmt.setNull(17, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Interface"))
                {
                    stmt.setString(18, parametersValues.get(addedParameters.indexOf("Interface")).toString());
                }
                else
                {
                    stmt.setNull(18, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Złącza"))
                {
                    stmt.setString(19, parametersValues.get(addedParameters.indexOf("Złącza")).toString());
                }
                else
                {
                    stmt.setNull(19, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Prędkość odczytu"))
                {
                    stmt.setInt(20, Integer.parseInt(parametersValues.get(addedParameters.indexOf("Prędkość odczytu")).toString()));
                }
                else
                {
                    stmt.setNull(20, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Prędkość zapisu"))
                {
                    stmt.setInt(21, Integer.parseInt(parametersValues.get(addedParameters.indexOf("Prędkość zapisu")).toString()));
                }
                else
                {
                    stmt.setNull(21, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("MTBF"))
                {
                    stmt.setInt(22, Integer.parseInt(parametersValues.get(addedParameters.indexOf("MTBF")).toString()));
                }
                else
                {
                    stmt.setNull(22, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("TDP"))
                {
                    stmt.setInt(23, Integer.parseInt(parametersValues.get(addedParameters.indexOf("TDP")).toString()));
                }
                else
                {
                    stmt.setNull(23, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Kolor"))
                {
                    stmt.setString(24, parametersValues.get(addedParameters.indexOf("Kolor")).toString());
                }
                else
                {
                    stmt.setNull(24, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Wysokość"))
                {
                    stmt.setInt(25, Integer.parseInt(parametersValues.get(addedParameters.indexOf("Wysokość")).toString()));
                }
                else
                {
                    stmt.setNull(25, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Szerokość"))
                {
                    stmt.setInt(26, Integer.parseInt(parametersValues.get(addedParameters.indexOf("Szerokość")).toString()));
                }
                else
                {
                    stmt.setNull(26, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Długość"))
                {
                    stmt.setInt(27, Integer.parseInt(parametersValues.get(addedParameters.indexOf("Długość")).toString()));
                }
                else
                {
                    stmt.setNull(27, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Akcesoria"))
                {
                    stmt.setString(28, parametersValues.get(addedParameters.indexOf("Akcesoria")).toString());
                }
                else
                {
                    stmt.setNull(28, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Gwarancja"))
                {
                    stmt.setInt(29, Integer.parseInt(parametersValues.get(addedParameters.indexOf("Gwarancja")).toString()));
                }
                else
                {
                    stmt.setNull(29, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Maksymalna moc"))
                {
                    stmt.setInt(30, Integer.parseInt(parametersValues.get(addedParameters.indexOf("Maksymalna moc")).toString()));
                }
                else
                {
                    stmt.setNull(30, OracleTypes.NULL);
                }
                
                if(addedParameters.contains("Rodzaj zabezpieczeń"))
                {
                    stmt.setString(31, parametersValues.get(addedParameters.indexOf("Rodzaj zabezpieczeń")).toString());
                }
                else
                {
                    stmt.setNull(31, OracleTypes.NULL);
                }
                
                stmt.execute();
                
                /*
                    -"Model", string
                    -"Typ", string
                    -"Taktowanie zegara", int
                    -"Socket", string
                    -"Chipset", string
                    -"Liczba rdzeni", int
                    -"Liczba wątków", int
                    -"Pojemność", int
                    -"Cache", int
                    -"VRAM", int
                    -"Format", string
                    -"Interface", string
                    -"Złącza", string
                    -"Prędkość odczytu", int
                    -"Prędkość zapisu", int
                    -"MTBF", int
                    -"TDP", int
                    -"Kolor", string
                    -"Wysokość", int
                    -"Szerokość", int
                    -"Długość", int
                    -"Akcesoria", string
                    -"Gwarancja", int
                    -"Maksymalna moc", int
                    -"Rodzaj zabezpieczeń" string
                */
                
                stmt.close();
                con.close();
            }
            
            productNameInput.setText("");
            productDescInput.setText("");
            productPriceInput.setText("");
            productQuantityInput.setText("");
            ParametersPane.getChildren().clear();
            addedParameters.clear();
            parametersValues.clear();
        }
        else if(event.getSource()==addParameterInput){
            if(!addedParameters.contains(parameterChoiceBox.getValue()))
            {
                addedParameters.add(parameterChoiceBox.getValue());
                parametersValues.add(parameterValueInput.getText());
            }
            else
            {
                int index = addedParameters.indexOf(parameterChoiceBox.getValue());
                parametersValues.set(index, parameterValueInput.getText());
            }
            
            parameterValueInput.setText("");
            
            ParametersPane.getChildren().clear();
            for(int i = 0; i < addedParameters.size(); i++)
            {
                ParametersPane.getChildren().add(new Label(addedParameters.get(i).toString().concat(": ").concat(parametersValues.get(i).toString())));
            }
            
            for(int i = 1; i < ParametersPane.getChildren().size(); i++)
            {
                ParametersPane.getChildren().get(i).setLayoutY(ParametersPane.getChildren().get(i - 1).getLayoutY() + 20);
            }
        }
    }   

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List supplierLi = new LinkedList();
        List categoryLi = new LinkedList();
        
        try{
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection con=DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
            
            String query = "{call KJTCompany.SELECT_SUPPLIERS(?)}";
            CallableStatement stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.executeQuery();
            ResultSet cursor = ((OracleCallableStatement)stmt).getCursor(1);
            
            try{
                while(cursor.next()){
                    int supplierId = cursor.getInt("SUPPLIERID");
                    String companyName = cursor.getString("COMPANYNAME");
                    
                    supplierLi.add(new Supplier(supplierId, companyName));
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            cursor.close();
            stmt.close();
            
            query = "{call KJTCompany.SELECT_CATEGORIES(?)}";
            stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.executeQuery();
            cursor = ((OracleCallableStatement)stmt).getCursor(1);
            
            try{
                while(cursor.next()){
                    int categoryId = cursor.getInt("CATEGORYID");
                    String categoryName = cursor.getString("CATEGORYNAME");
                    String categoryDesc = cursor.getString("DESCRIPTION");
                    
                    categoryLi.add(new Category(categoryId, categoryName, categoryDesc));
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(categoryTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            cursor.close();
            stmt.close();
        }
        catch(Exception e){ 
            System.out.println(e);
        }
        
        ObservableList<Supplier> supplierList = FXCollections.observableArrayList(supplierLi);
        supplierChoiceBox.setItems(supplierList);
        
        ObservableList<Category> categoryList = FXCollections.observableArrayList(categoryLi);
        categoryChoiceBox.setItems(categoryList);
        
        List parametersLi = new LinkedList(Arrays.asList(
            "Model",
            "Typ",
            "Taktowanie zegara",
            "Socket",
            "Chipset",
            "Liczba rdzeni",
            "Liczba wątków",
            "Pojemność",
            "Cache",
            "VRAM",
            "Format",
            "Interface",
            "Złącza",
            "Prędkość odczytu",
            "Prędkość zapisu",
            "MTBF",
            "TDP",
            "Kolor",
            "Wysokość",
            "Szerokość",
            "Długość",
            "Akcesoria",
            "Gwarancja",
            "Maksymalna moc",
            "Rodzaj zabezpieczeń"
        ));
        ObservableList<String> parametersList = FXCollections.observableArrayList(parametersLi);
        parameterChoiceBox.setItems(parametersList);
    }
}
