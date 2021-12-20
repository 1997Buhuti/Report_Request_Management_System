/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author dpman
 */
public class DBConnection {

    private static Connection con = null;
    private static DBConnection dbocnnection;
    private static Properties p = null;
    
    /*private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pb_app_data","root","Manakal@2021");
    }*/
    
   static{
            
        try{
               
                p=new Properties (); 
                //p.load(new FileInputStream("src/DB/db_prop.prop")); C:\Users\dpman\Documents\NetBeansProjects\PBApp\dist
                p.load(new FileInputStream("src/db_prop.prop"));
                String dname= (String) p.get ("Dname"); 
                String url= (String) p.get ("URL"); 
                String username= (String) p.get ("Uname"); 
                String password= (String) p.get ("password"); 
                Class.forName(dname); 
                con = DriverManager.getConnection(url, username, password);

        }
        catch(Exception ex){
            ex.printStackTrace();
        }

    }

    public static Connection getConnection(){
        
        
        if(con==null){
            System.out.println("Connection established succesfull");
            //JOptionPane.showMessageDialog(this, "The record updated");
        }
        return con;
    }

}


