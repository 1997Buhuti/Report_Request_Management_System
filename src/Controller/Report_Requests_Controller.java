/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DB.DBConnection;
import Model.BranchModel;
import Model.Report_Requests_Model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author dpman
 */
public class Report_Requests_Controller {
        Connection con = DBConnection.getConnection();
        
        /*
            Code for getting all the report requests
        */
        public ArrayList<Report_Requests_Model> getAllReportRequests() 
            throws ClassNotFoundException, SQLException{
            
            String sql="SELECT * FROM projects_tbl;";
            PreparedStatement pst= con.prepareStatement(sql);
            ResultSet rst= pst.executeQuery(sql);
            
            ArrayList<Report_Requests_Model> Report_Requests= new ArrayList<>();
                while(rst.next()){
                    Report_Requests.add(new Report_Requests_Model(rst.getString(1),
                    rst.getString(2),rst.getString(7),rst.getString(9),rst.getString(8),
                    rst.getString(13),rst.getString(14),rst.getString(5),rst.getString(6),
                    rst.getString(3),rst.getString(4),rst.getString(10),rst.getString(11)));
                }
            return Report_Requests;
        
        }

            public boolean saveReportRequest(Report_Requests_Model request) throws ClassNotFoundException, SQLException{
        
                String sql="insert into projects_tbl(project_id, project_name, department_name, branch_name, Remarks, task_details, recieved_date, created_date, start_date, developer_name, developer_id, request_date, completion_date, CurrentStatus) values(?, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)";
                PreparedStatement pst= con.prepareStatement(sql);
                pst.setString(1, request.getProject_id());
                pst.setString(2, request.getProject_name());
                pst.setString(3, request.getDepartment_name());
                pst.setString(4, request.getBranch_name());
                pst.setString(5, request.getRemarks());
                pst.setString(6, request.getTask_details());
                pst.setString(7, request.getRecieved_date());
                pst.setString(8, request.getCreated_date());
                pst.setString(9, request.getStart_date());
                pst.setString(10, request.getDeveloper_name());
                pst.setString(11, request.getDeveloper_id());
                pst.setString(12, request.getCreated_date());
                pst.setString(13, request.getCompletion_date());
                pst.setString(14, request.getCurent_status());
                
                
                return pst.executeUpdate()>0;

        }
            
        public boolean saveReportRequestWithoutCompletion (Report_Requests_Model request) throws ClassNotFoundException, SQLException{

                String sql="insert into projects_tbl(project_id, project_name, department_name, branch_name, Remarks, task_details, recieved_date, created_date, start_date, developer_name, developer_id, request_date, CurrentStatus) values(?, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst= con.prepareStatement(sql);
                pst.setString(1, request.getProject_id());
                pst.setString(2, request.getProject_name());
                pst.setString(3, request.getDepartment_name());
                pst.setString(4, request.getBranch_name());
                pst.setString(5, request.getRemarks());
                pst.setString(6, request.getTask_details());
                pst.setString(7, request.getRecieved_date());
                pst.setString(8, request.getCreated_date());
                pst.setString(9, request.getStart_date());
                pst.setString(10, request.getDeveloper_name());
                pst.setString(11, request.getDeveloper_id());
                pst.setString(12, request.getCreated_date());
                pst.setString(13, request.getCurent_status());
                
                
                return pst.executeUpdate()>0;

        }

        public ArrayList <String> loadUserNames() throws ClassNotFoundException, SQLException{
        
        String sql="select* from users";
        ArrayList <String> userNames = new ArrayList<>();
        PreparedStatement pst= con.prepareStatement(sql);
        ResultSet rst=pst.executeQuery();
        
        while(rst.next()){
            
            userNames.add(rst.getString(2));
        }
        return userNames;
          
        }
        
        public boolean updateReport_requests (Report_Requests_Model request) throws SQLException, ClassNotFoundException{
        
                PreparedStatement pst = con.prepareStatement("update projects_tbl set project_name=?, department_name=?, branch_name=?, Remarks=?, task_details=?, recieved_date=?, created_date=?, start_date=?, developer_name=?, developer_id=?, request_date=?, completion_date=?, CurrentStatus=? where  project_id=? ");
                pst.setString(14, request.getProject_id());
                pst.setString(1, request.getProject_name());
                pst.setString(2, request.getDepartment_name());
                pst.setString(3, request.getBranch_name());
                pst.setString(4, request.getRemarks());
                pst.setString(5, request.getTask_details());
                pst.setString(6, request.getRecieved_date());
                pst.setString(7, request.getCreated_date());
                pst.setString(8, request.getStart_date());
                pst.setString(9, request.getDeveloper_name());
                pst.setString(10, request.getDeveloper_id());
                pst.setString(11, request.getCreated_date());
                pst.setString(12, request.getCompletion_date());
                pst.setString(13, request.getCurent_status());
                return pst.executeUpdate()>0;
    }
  
    
}
