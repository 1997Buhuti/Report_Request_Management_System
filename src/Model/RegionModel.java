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
public class RegionModel {
    private String RegionCode;
    private String RegionName;

    public String getRegionCode() {
        return RegionCode;
    }

    public void setRegionCode(String RegionCode) {
        this.RegionCode = RegionCode;
    }

    public String getRegionName() {
        return RegionName;
    }

    public void setRegionName(String RegionName) {
        this.RegionName = RegionName;
    }

    public RegionModel() {
    }

    public RegionModel(String RegionCode, String RegionName) {
        this.RegionCode = RegionCode;
        this.RegionName = RegionName;
    }
}
