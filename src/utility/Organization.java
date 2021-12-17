/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

/**
 *
 * @author dpman
 */
public class Organization {
    
    private String Type;
    private String OrgName;

    public Organization() {
        
    }

    public Organization(String Type, String OrgName) {
        this.Type = Type;
        this.OrgName = OrgName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getOrgName() {
        return OrgName;
    }

    public void setOrgName(String OrgName) {
        this.OrgName = OrgName;
    }
    
    
    
}
