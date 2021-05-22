/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author Tomasz
 */
public class Employee {
    private int EmployeeID;
    private String FirstName;
    private String LastName;
    private String Title;
    private String TitleOfCourtesy;
    private String BirthDate;
    private String HireDate;
    private Address address;
    private int PhoneNumber;
    private String Email;
    private User user;

    public Employee() {
    }
    
    public Employee(int EmployeeID, String FirstName, String LastName) {
        this.EmployeeID = EmployeeID;
        this.FirstName = FirstName;
        this.LastName = LastName;
    }

    public Employee(int EmployeeID, String FirstName, String LastName, String Title, String TitleOfCourtesy, String BirthDate, String HireDate, Address address, int PhoneNumber, String Email, User user) {
        this.EmployeeID = EmployeeID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Title = Title;
        this.TitleOfCourtesy = TitleOfCourtesy;
        this.BirthDate = BirthDate;
        this.HireDate = HireDate;
        this.address = address;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
        this.user = user;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getTitleOfCourtesy() {
        return TitleOfCourtesy;
    }

    public void setTitleOfCourtesy(String TitleOfCourtesy) {
        this.TitleOfCourtesy = TitleOfCourtesy;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String BirthDate) {
        this.BirthDate = BirthDate;
    }

    public String getHireDate() {
        return HireDate;
    }

    public void setHireDate(String HireDate) {
        this.HireDate = HireDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(int PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return EmployeeID + " - " + FirstName + " " + LastName;
    }
    
    
}
