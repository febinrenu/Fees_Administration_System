/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fees_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author fkr77
 */
public class DBConnection {
    public static Connection getConnection(){
        Connection con = null;
        try{
            Class.forName("org.apache.derby.iapi.jdbc.AutoloadedDriver");
            con= DriverManager.getConnection("jdbc:derby://localhost:1527/fees_management", "root", "root");
        }catch(Exception e){
            e.printStackTrace();
        }
        return con;
    }
}
