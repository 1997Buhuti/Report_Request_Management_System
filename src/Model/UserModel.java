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
public class UserModel {
    private String id;
    private String name;
    private String password;
    private String accesslevel;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccesslevel(String accesslevel) {
        this.accesslevel = accesslevel;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getAccesslevel() {
        return accesslevel;
    }

    public UserModel(String id, String name, String password, String accesslevel) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.accesslevel = accesslevel;
    }

    
}
