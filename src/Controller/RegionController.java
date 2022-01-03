/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DB.DBConnection;
import Model.BranchModel;
import Model.DepartmentModel;
import Model.RegionModel;
import Model.Report_Requests_Model;
import Model.UserModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import utility.TypeCheck;

/**
 *
 * @author dpman
 */
public class RegionController {
    Connection con = DBConnection.getConnection();
    
    /*Code to get all the region details */
     public ArrayList<RegionModel> getAllRegionDetails() 
            throws ClassNotFoundException, SQLException{
            
            String sql="SELECT * FROM region_table;";
            PreparedStatement pst= con.prepareStatement(sql);
            ResultSet rst= pst.executeQuery(sql);
            
            ArrayList<RegionModel> RegionDetails= new ArrayList<>();
                while(rst.next()){
                    RegionDetails.add(new RegionModel(rst.getString(1),
                    rst.getString(2)));
                }
            return RegionDetails;
        
    }
    
     /*
        This Code is to add a new region
     */
    public boolean addRegion(RegionModel region) throws ClassNotFoundException, SQLException{
        PreparedStatement pst = con.prepareStatement("insert into region_table (Region_Id,Region_Name) values(? ,?);");
        pst.setString(1, region.getRegionCode());
        pst.setString(2, region.getRegionName());
        return pst.executeUpdate()>0;
    }

    /*This Code is to update a region */
    public boolean updateRegion (RegionModel region) throws SQLException, ClassNotFoundException{
        System.out.println("inside controller");
        PreparedStatement pst = con.prepareStatement("update region_table set Region_Name=? where  Region_ID=? ");
        pst.setString(1, region.getRegionCode());
        pst.setString(2, region.getRegionName());
        return pst.executeUpdate()>0;
    }
    /* This code is to delete a region */
    public boolean deleteRegion (String input) throws SQLException, ClassNotFoundException{
        
        if(TypeCheck.isNumeric(input)){
            PreparedStatement pst = con.prepareStatement("delete from region_table where  Region_ID=?");
            pst.setString(1, input);
            return pst.executeUpdate()>0;
        }
        else{
            PreparedStatement pst = con.prepareStatement("delete from region_table where  Region_Name=?");
            pst.setString(1, input);
            return pst.executeUpdate()>0;
        }
    }
    
    public ArrayList <String> loadRegionNames() throws ClassNotFoundException, SQLException{
        
        String sql="select* from region_table";
        ArrayList <String> regionNames = new ArrayList<>();
        PreparedStatement pst= con.prepareStatement(sql);
        ResultSet rst=pst.executeQuery();
        
        while(rst.next()){
            
            regionNames.add(rst.getString(2));
        }
        return regionNames;
          
        }
    
    
}
