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
public class Product {
    private int ProductID;
    private String ProductName;
    private Parameter parameter;
    private String Description;
    private Supplier supplier;
    private Category category;
    private int Price;
    private int UnitsInStock;

    public Product() {
    }

    public Product(int ProductID, String ProductName, Parameter parameter, String Description, Supplier supplier, Category category, int Price, int UnitsInStock) {
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.parameter = parameter;
        this.Description = Description;
        this.supplier = supplier;
        this.category = category;
        this.Price = Price;
        this.UnitsInStock = UnitsInStock;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int ProductID) {
        this.ProductID = ProductID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public int getUnitsInStock() {
        return UnitsInStock;
    }

    public void setUnitsInStock(int UnitsInStock) {
        this.UnitsInStock = UnitsInStock;
    }

    @Override
    public String toString() {
        return ProductName;
    }
    
    
}
