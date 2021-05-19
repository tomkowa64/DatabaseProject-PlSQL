/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


public class Category {
    private int categoryId;
    private String categoryName;
    private String Description;

    public Category() {
    }

    
    
    public Category(int categoryId, String categoryName, String Description) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.Description = Description;
    }

    
    
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    @Override
    public String toString() {
        return "Category{" + "categoryId=" + categoryId + ", categoryName=" + categoryName + ", Description=" + Description + '}';
    }

    
  
}
