/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DB.DBConnection;
import Model.UserModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dpman
 */
public class User_Controller {
    
    public Connection Connect() throws ClassNotFoundException, SQLException{
        DBConnection conObj=DBConnection.getInstance();
        Connection con= conObj.getConnection();
        return con;
    }
    
    public boolean saveUser(UserModel user) throws ClassNotFoundException, SQLException{
        
                Connection con= Connect();
                PreparedStatement pst= con.prepareStatement("insert into users (user_id,user_name,password,access_level) values(? ,? ,?, ?);");
                pst.setString(1, user.getId());
                pst.setString(2, user.getName());
                pst.setString(3,user.getPassword());
                pst.setString(4, user.getAccesslevel());
                
                return pst.executeUpdate()>0;

    }
    
    public boolean checkCredentials(String uname , String Password){
        
        try {
            
            DBConnection dbcon = DBConnection.getInstance();
            Connection con=dbcon.getConnection();
            String sql="select* from users where user_name=? and password=?";
            PreparedStatement pst= con.prepareStatement(sql);
            pst.setString(1, uname);
            pst.setString(2, Password);
            
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                System.out.println(rs.getObject("user_name"));
                return true;
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(User_Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(User_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
