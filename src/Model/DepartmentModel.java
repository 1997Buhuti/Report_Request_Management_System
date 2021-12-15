/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author dpman
 */
public class DepartmentModel {
    private String deparmentCode;
    private String deparmentName;

    public String getDeparmentCode() {
        return deparmentCode;
    }

    public void setDeparmentCode(String deparmentCode) {
        this.deparmentCode = deparmentCode;
    }

    public String getDeparmentName() {
        return deparmentName;
    }

    public void setDeparmentName(String deparmentName) {
        this.deparmentName = deparmentName;
    }

    public DepartmentModel(String deparmentCode, String deparmentName) {
        this.deparmentCode = deparmentCode;
        this.deparmentName = deparmentName;
    }
}
