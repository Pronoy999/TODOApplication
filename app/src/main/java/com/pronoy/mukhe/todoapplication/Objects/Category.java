package com.pronoy.mukhe.todoapplication.Objects;

/**
 * Created by mukhe on 27-Apr-18.
 * This is the object of the Category.
 */

public class Category {
    private String categoryDesc;
    private int categoryID;

    public Category(String categoryDesc,int categoryID) {
        this.categoryDesc = categoryDesc;
        this.categoryID=categoryID;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
