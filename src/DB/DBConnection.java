/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dpman
 */
public class DBConnection {
    
    private static final String DB_DRIVER_CLASS = "driver.class.name";
    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_URL = "db.url";
    
    private static Connection con = null;
    private static DBConnection dbocnnection;
    
    /*private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pb_app_data","root","Manakal@2021");
    }*/
    
   static{
            
        try{
               /*
                p=new Properties (); 
                p.load(new FileInputStream("src/DB/db_prop.prop")); 
                String dname= (String) p.get ("Dname"); 
                String url= (String) p.get ("URL"); 
                String username= (String) p.get ("Uname"); 
                String password= (String) p.get ("password"); 
                Class.forName(dname); 
                con = DriverManager.getConnection(url, username, password);
                
                p=new Properties ();
                Class.forName(Properties.getProperty(DB_DRIVER_CLASS));
                Properties.load(new FileInputStream())*/

               Properties prop = new Properties();
               prop.load(new FileInputStream("src/db_prop.prop"));
               Class.forName(prop.getProperty(DB_DRIVER_CLASS));
               con = DriverManager.getConnection(prop.getProperty(DB_URL),prop.getProperty(DB_USERNAME),prop.getProperty(DB_PASSWORD));
               //DB_URL,DB_USERNAME,DB_PASSWORD

        }
        catch(FileNotFoundException ex){
            try {
                Properties prop = new Properties();
                prop.load(new FileInputStream("./db_prop.prop"));
                Class.forName(prop.getProperty(DB_DRIVER_CLASS));
                con = DriverManager.getConnection(prop.getProperty(DB_URL),prop.getProperty(DB_USERNAME),prop.getProperty(DB_PASSWORD));
                ex.printStackTrace();
            } catch (IOException ex1) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (ClassNotFoundException ex1) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (SQLException ex1) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex1);
            }
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



