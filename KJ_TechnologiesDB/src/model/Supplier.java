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
public class Supplier {
    private int SupplierID;
    private String CompanyName;
    private Address address;
    private int PhoneNumber;
    private String Email;
    private String WebPage;

    public Supplier() {
    }

    public Supplier(int SupplierID, String CompanyName, Address address, int PhoneNumber, String Email, String WebPage) {
        this.SupplierID = SupplierID;
        this.CompanyName = CompanyName;
        this.address = address;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
        this.WebPage = WebPage;
    }

    public int getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(int SupplierID) {
        this.SupplierID = SupplierID;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
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

    public String getWebPage() {
        return WebPage;
    }

    public void setWebPage(String WebPage) {
        this.WebPage = WebPage;
    }

    @Override
    public String toString() {
        return CompanyName;
    }
}
