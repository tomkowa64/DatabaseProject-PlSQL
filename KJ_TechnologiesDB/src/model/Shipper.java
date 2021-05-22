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
public class Shipper {
    private int ShipperID;
    private String CompanyName;
    private int PhoneNumber;
    private String Email;

    public Shipper() {
    }

    public Shipper(int ShipperID, String CompanyName, int PhoneNumber, String Email) {
        this.ShipperID = ShipperID;
        this.CompanyName = CompanyName;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
    }

    public Shipper(int shipperId, String companyName) {
        this.ShipperID = shipperId;
        this.CompanyName = companyName;
    }

    public int getShipperID() {
        return ShipperID;
    }

    public void setShipperID(int ShipperID) {
        this.ShipperID = ShipperID;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
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

    @Override
    public String toString() {
        return CompanyName;
    }
    
    
}
