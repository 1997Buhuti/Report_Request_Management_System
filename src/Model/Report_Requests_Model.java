/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import utility.Organization;

/**
 *
 * @author dpman
 */
public class Report_Requests_Model {
    
    private String project_id;
    private String project_name;
    private String recieved_date;
    private String created_date;
    private String start_date;
    private String completion_date;
    private String curent_status;
    private String remarks;
    private String task_details;
    private String department_name;
    private String branch_name;
    private String developer_name;
    private String developer_id;
    private Organization organization;
    
    /*Conatructor without the completion date*/
    public Report_Requests_Model(String ProjId, String ProjName, String recieved_date, String created_date, String starting_date, String proj_status, String remarks, String task_details, Organization org, String developers, String dev_Id) {
        this.project_id = ProjId;
        this.project_name = ProjName;
        this.recieved_date = recieved_date;
        this.created_date = created_date;
        this.start_date = starting_date;
        //this.completion_date = completion_date;
        this.curent_status = proj_status;
        this.remarks = remarks;
        this.task_details = task_details;
        this.developer_name = developers;
        this.developer_id = dev_Id;
        this.organization = org;
    }
    
    /*Conatructor with completion date*/
    public Report_Requests_Model(String ProjId, String ProjName, String recieved_date, String created_date, String starting_date, String completion_Date ,String proj_status, String remarks, String task_details, Organization org, String developers, String dev_Id) {
        this.project_id = ProjId;
        this.project_name = ProjName;
        this.recieved_date = recieved_date;
        this.created_date = created_date;
        this.start_date = starting_date;
        this.completion_date = completion_Date;
        this.curent_status = proj_status;
        this.remarks = remarks;
        this.task_details = task_details;
        this.developer_name = developers;
        this.developer_id = dev_Id;
        this.organization = org;
    }
    

     
    public Report_Requests_Model() {
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getRecieved_date() {
        return recieved_date;
    }

    public void setRecieved_date(String recieved_date) {
        this.recieved_date = recieved_date;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getCompletion_date() {
        return completion_date;
    }

    public void setCompletion_date(String completion_date) {
        this.completion_date = completion_date;
    }

    public String getCurent_status() {
        return curent_status;
    }

    public void setCurent_status(String curent_status) {
        this.curent_status = curent_status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTask_details() {
        return task_details;
    }

    public void setTask_details(String task_details) {
        this.task_details = task_details;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getDeveloper_name() {
        return developer_name;
    }

    public void setDeveloper_name(String developer_name) {
        this.developer_name = developer_name;
    }

    public String getDeveloper_id() {
        return developer_id;
    }

    public void setDeveloper_id(String developer_id) {
        this.developer_id = developer_id;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

   
    
}
