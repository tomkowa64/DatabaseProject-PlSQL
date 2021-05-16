/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kj_technologiesdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class oracleSession {
    //Sample SELECT query / TODO CRUD Session implementation
    public ResultSet selectSession(){
        ResultSet rs = null;
        try{  
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            Connection con=DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","system","oracle");  

            Statement stmt=con.createStatement();  

            rs = stmt.executeQuery("select * from KJ_T.CATEGORIES");  
            while(rs.next())  
            System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  

            con.close();  
        }
        catch(Exception e){ 
            System.out.println(e);
        }
        return rs;
    }
}
