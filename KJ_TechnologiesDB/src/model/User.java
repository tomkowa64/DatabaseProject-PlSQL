/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


public class User {
    private int UserID;
    private String Login;
    private String Password;
    private String AccountType;

    public User() {
    }

    public User(int UserID, String Login, String Password, String AccountType) {
        this.UserID = UserID;
        this.Login = Login;
        this.Password = Password;
        this.AccountType = AccountType;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String Login) {
        this.Login = Login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String AccountType) {
        this.AccountType = AccountType;
    }

    @Override
    public String toString() {
        return Login;
    }
    
    
}
