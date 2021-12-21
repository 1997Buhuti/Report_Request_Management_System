/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DB.DBConnection;
import Model.UserModel;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    
    Connection con = DBConnection.getConnection();
    
    public boolean saveUser(UserModel user) throws ClassNotFoundException, SQLException{
        
                PreparedStatement pst= con.prepareStatement("insert into users (user_id,user_name,password,access_level) values(? ,? ,?, ?);");
                pst.setString(1, user.getId());
                pst.setString(2, user.getName());
                pst.setString(3,user.getPassword());
                pst.setString(4, user.getAccesslevel());
                
                return pst.executeUpdate()>0;

    }
    
    public String encrypt_password(String passsword){
       
        try {
  
        // Static getInstance method is called with hashing MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
  
        // digest() method is called to calculate message digest
        //  of an input digest() return array of byte
        byte[] messageDigest = md.digest(passsword.getBytes());
  
        // Convert byte array into signum representation
        BigInteger no = new BigInteger(1, messageDigest);
  
        // Convert message digest into hex value
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
            return hashtext;
        } 
  
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public String get_encrypt_password(String passsword){
       
        try {
  
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(passsword.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                    hashtext = "0" + hashtext;
            }
            return hashtext;
        } 
  
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    public boolean checkCredentials(String uname , String Password){
        
        try {
           
            String sql="select* from users where user_name=? and password=?";
            String encryptedPassword = get_encrypt_password(Password);
            PreparedStatement pst= con.prepareStatement(sql);
            pst.setString(1, uname);
            pst.setString(2, encryptedPassword);
            
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                System.out.println(rs.getObject("user_name"));
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(User_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean changePassword(String userName, String PW){
        
        try {
            String encryptedPW = encrypt_password(PW);
            String sql= "Update users set password=? where user_name=?";
            PreparedStatement pst= con.prepareStatement(sql);
            pst.setString(1, encryptedPW);
            pst.setString(2, userName);
            return pst.executeUpdate()>0;

        } catch (SQLException ex) {
            Logger.getLogger(User_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
