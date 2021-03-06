/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DB.DBConnection;
import Model.BranchModel;
import Model.DepartmentModel;
import Model.Report_Requests_Model;
import Model.UserModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static jdk.nashorn.internal.runtime.JSType.isNumber;
import utility.TypeCheck;

/**
 *
 * @author dpman
 */
public class BranchController {
    
    //getting the connection object
    Connection con = DBConnection.getConnection();
    
        /*
            Code for getting all branch details
        */
        public ArrayList<BranchModel> getAllBranchDetails() 
            throws ClassNotFoundException, SQLException{
            
            String sql="SELECT * FROM branch_table;";
            PreparedStatement pst= con.prepareStatement(sql);
            ResultSet rst= pst.executeQuery(sql);
            
            ArrayList<BranchModel> BranchDetails= new ArrayList<>();
                while(rst.next()){
                    BranchDetails.add(new BranchModel(rst.getString(1),
                    rst.getString(2)));
                }
            return BranchDetails;
        
        }
    
        /*Function for adding a new branch to the databse */
    public boolean addBranch(BranchModel branch) throws ClassNotFoundException, SQLException{
        
                PreparedStatement pst= con.prepareStatement("insert into branch_table (branch_Code,branch_Name) values(? ,?);");
                pst.setString(1, branch.getBranchCode());
                pst.setString(2, branch.getBranchName());
                return pst.executeUpdate()>0;
    }

        /*Function to update existing branch */
    public boolean updateaddBranch (BranchModel branch) throws SQLException, ClassNotFoundException{
        
                PreparedStatement pst = con.prepareStatement("update branch_table set branch_Name=? where  branch_Code=? ");
                pst.setString(1, branch.getBranchName());
                pst.setString(2, branch.getBranchCode());
                return pst.executeUpdate()>0;
    }
        /*Function to delete existing branch */
    public boolean deleteBranch (String input) throws SQLException, ClassNotFoundException{
        if(TypeCheck.isNumeric(input)){
            PreparedStatement pst = con.prepareStatement("delete from branch_table where  branch_Code=? ");
            pst.setString(1, input);
            return pst.executeUpdate()>0;
        }
        else{
            PreparedStatement pst = con.prepareStatement("delete from branch_table where  branch_Name=? ");
            pst.setString(1, input);
            return pst.executeUpdate()>0;
        }
                
    }
    /*Function to load all the branchNames */
    public ArrayList <String> loadBranchNames() throws ClassNotFoundException, SQLException{
        
        String sql="select* from branch_table";
        ArrayList <String> departmentNames = new ArrayList<>();
        PreparedStatement pst= con.prepareStatement(sql);
        ResultSet rst=pst.executeQuery();
        
        while(rst.next()){
            
            departmentNames.add(rst.getString(2));
        }
        return departmentNames;
          
        }
    
    
}
