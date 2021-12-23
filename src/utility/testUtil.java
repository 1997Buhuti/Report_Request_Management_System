/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import Model.Report_Requests_Model;

/**
 *
 * @author dpman
 */
public class testUtil {
    public static void main(String[] args) {
        Organization org = new Organization("Branch","Malwana");
        Report_Requests_Model model = new Report_Requests_Model("proj123",
                "TestProj","2021-12-23","2021-12-24","2021-12-25","2021-12-26",
                "2021-12-26","status is this..","nothing","",org,"Suranga","dev012");
        System.out.println("Completion date= "+model.getCompletion_date());
    }
}
