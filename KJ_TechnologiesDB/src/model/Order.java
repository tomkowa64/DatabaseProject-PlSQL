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
public class Order {
    private int OrderID;
    private Customer customer;
    private Employee employee;
    private String OrderDate;
    private String RequiredDate;
    private String ShippedDate;
    private Shipper shipper;
    private String ShipName;
    private Address address;

    public Order() {
    }

    public Order(int OrderID, Customer customer, Employee employee, String OrderDate, String RequiredDate, String ShippedDate, Shipper shipper, String ShipName, Address address) {
        this.OrderID = OrderID;
        this.customer = customer;
        this.employee = employee;
        this.OrderDate = OrderDate;
        this.RequiredDate = RequiredDate;
        this.ShippedDate = ShippedDate;
        this.shipper = shipper;
        this.ShipName = ShipName;
        this.address = address;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String OrderDate) {
        this.OrderDate = OrderDate;
    }

    public String getRequiredDate() {
        return RequiredDate;
    }

    public void setRequiredDate(String RequiredDate) {
        this.RequiredDate = RequiredDate;
    }

    public String getShippedDate() {
        return ShippedDate;
    }

    public void setShippedDate(String ShippedDate) {
        this.ShippedDate = ShippedDate;
    }

    public Shipper getShipper() {
        return shipper;
    }

    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    public String getShipName() {
        return ShipName;
    }

    public void setShipName(String ShipName) {
        this.ShipName = ShipName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Order{" + "OrderID=" + OrderID + ", customer=" + customer + ", employee=" + employee + ", OrderDate=" + OrderDate + ", RequiredDate=" + RequiredDate + ", ShippedDate=" + ShippedDate + ", shipper=" + shipper + ", ShipName=" + ShipName + ", address=" + address + '}';
    }
    
    
}
