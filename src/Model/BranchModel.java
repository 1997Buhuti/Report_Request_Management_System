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
public class BranchModel {
    private String BranchCode;
    private String BranchName;

    public String getBranchCode() {
        return BranchCode;
    }

    public void setBranchCode(String BranchCode) {
        this.BranchCode = BranchCode;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String BranchName) {
        this.BranchName = BranchName;
    }

    public BranchModel(String BranchCode, String BranchName) {
        this.BranchCode = BranchCode;
        this.BranchName = BranchName;
    }

    public BranchModel() {
    }


}
