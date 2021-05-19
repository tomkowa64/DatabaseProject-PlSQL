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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Category;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class addCategoryController implements Initializable{

    @FXML
    private Button goBack;

    @FXML
    private TextField categoryCategoryNameInput;

    @FXML
    private Button addCategoryButton;

    @FXML
    private TextArea categoryDescInput;

    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        if(event.getSource()==goBack){
            stage = (Stage) goBack.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/view/CategoryTable.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if(event.getSource()==addCategoryButton){
            try{  
                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                if(!categoryCategoryNameInput.getText().isEmpty())
                {
                    Connection con=DriverManager.getConnection(  
                    "jdbc:oracle:thin:@localhost:1521:xe","system","oracle");  

                    String query = "{call KJTCompany.INSERT_CATEGORY(?,?)}";

                    CallableStatement stmt = con.prepareCall(query);

                    stmt.setString(1, categoryCategoryNameInput.getText());
                    stmt.setString(2, categoryDescInput.getText());
                    stmt.execute();

                    categoryCategoryNameInput.setText("");
                    categoryDescInput.setText("");

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
        
    }

}
