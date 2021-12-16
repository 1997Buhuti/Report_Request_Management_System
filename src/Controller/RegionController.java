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

/**
 *
 * @author dpman
 */
public class RegionController {
    Connection con = DBConnection.getConnection();
    
    public boolean addRegion(RegionModel region) throws ClassNotFoundException, SQLException{
        
                PreparedStatement pst= con.prepareStatement("insert into region_table (region_Code,region_Name) values(? ,?);");
                pst.setString(1, region.getRegionCode());
                pst.setString(2, region.getRegionName());
                return pst.executeUpdate()>0;
    }

    public boolean updateRegion (RegionModel region) throws SQLException, ClassNotFoundException{
        
                PreparedStatement pst = con.prepareStatement("update region_table set Region ID=?, Region Name=? where  Region Name=? ");
                pst.setString(1, region.getRegionCode());
                pst.setString(2, region.getRegionName());
                return pst.executeUpdate()>0;
    }
    
    public boolean deleteRegion (RegionModel region) throws SQLException, ClassNotFoundException{
        
                PreparedStatement pst = con.prepareStatement("delete from region_table where  Region ID=? ");
                pst.setString(1, region.getRegionCode());
                return pst.executeUpdate()>0;
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
