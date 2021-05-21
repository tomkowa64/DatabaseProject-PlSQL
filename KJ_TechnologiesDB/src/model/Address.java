/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


public class Address {
    private int AddressID;
    private String Address;
    private String City;
    private String Region;
    private String PostalCode;
    private String Country;

    public Address() {
    }

    public Address(int AddressID, String Address, String City, String Region, String PostalCode, String Country) {
        this.AddressID = AddressID;
        this.Address = Address;
        this.City = City;
        this.Region = Region;
        this.PostalCode = PostalCode;
        this.Country = Country;
    }

    public int getAddressID() {
        return AddressID;
    }

    public void setAddressID(int AddressID) {
        this.AddressID = AddressID;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String Region) {
        this.Region = Region;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String PostalCode) {
        this.PostalCode = PostalCode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    @Override
    public String toString() {
       return Address + ", " + City + ", " + Country;
    }

    
    
}
