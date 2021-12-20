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
public class UserCredentials {
    
   public String userName;
   public String passWord;
   public String userLevel;

    public UserCredentials(String userName, String passWord, String userLevel) {
        this.userName = userName;
        this.passWord = passWord;
        this.userLevel = userLevel;
    }

    public UserCredentials() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    
    
}
