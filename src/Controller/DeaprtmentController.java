/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DB.DBConnection;
import Model.DepartmentModel;
import Model.Report_Requests_Model;
import Model.UserModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author dpman
 */
public class DeaprtmentController {
    Connection con = DBConnection.getConnection();
    
    public boolean addDepartment(DepartmentModel dept) throws ClassNotFoundException, SQLException{
        
                PreparedStatement pst= con.prepareStatement("insert into department_table (Department Id,Department Name) values(? ,?);");
                pst.setString(1, dept.getDeparmentCode());
                pst.setString(2, dept.getDeparmentName());
                return pst.executeUpdate()>0;
    }

    public boolean updateDepartment (DepartmentModel dept) throws SQLException, ClassNotFoundException{
        
                PreparedStatement pst = con.prepareStatement("update department_table set Department Id=?, Department Name=? where  Department Id=? ");
                pst.setString(1, dept.getDeparmentCode());
                pst.setString(2, dept.getDeparmentName());
                pst.setString(3, dept.getDeparmentCode());
                return pst.executeUpdate()>0;
    }
    
    public boolean deleteDepartment (DepartmentModel dept) throws SQLException, ClassNotFoundException{
        
                PreparedStatement pst = con.prepareStatement("delete from department_table where  Department Id=? ");
                pst.setString(1, dept.getDeparmentCode());
                return pst.executeUpdate()>0;
    }
    
    public ArrayList <String> loadDeparmentNames() throws ClassNotFoundException, SQLException{
        
        String sql="select* from department_table";
        ArrayList <String> departmentNames = new ArrayList<>();
        PreparedStatement pst= con.prepareStatement(sql);
        ResultSet rst=pst.executeQuery();
        
        while(rst.next()){
            
            departmentNames.add(rst.getString(2));
        }
        return departmentNames;
          
        }
    
    
}
