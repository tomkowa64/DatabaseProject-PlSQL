/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Tomasz
 */
public class Customer {
    private int CustomerID;
    private String FirstName;
    private String LastName;
    private String ContactTitle;
    private int NIP;
    private Address address;
    private int PhoneNumber;
    private String Email;
    private User user;

    public Customer() {
    }

    public Customer(int CustomerID, String FirstName, String LastName, String ContactTitle, int NIP, Address address, int PhoneNumber, String Email, User user) {
        this.CustomerID = CustomerID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.ContactTitle = ContactTitle;
        this.NIP = NIP;
        this.address = address;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
        this.user = user;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int CustomerID) {
        this.CustomerID = CustomerID;
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

    public String getContactTitle() {
        return ContactTitle;
    }

    public void setContactTitle(String ContactTitle) {
        this.ContactTitle = ContactTitle;
    }

    public int getNIP() {
        return NIP;
    }

    public void setNIP(int NIP) {
        this.NIP = NIP;
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
        return "Customer{" + "CustomerID=" + CustomerID + ", FirstName=" + FirstName + ", LastName=" + LastName + ", ContactTitle=" + ContactTitle + ", NIP=" + NIP + ", address=" + address + ", PhoneNumber=" + PhoneNumber + ", Email=" + Email + ", user=" + user + '}';
    }
    
    
    
}
