package com.pronoy.mukhe.todoapplication.Objects;

/**
 * Created by mukhe on 27-Apr-18.
 * This is the object of the Category.
 */

public class Category {
    private String categoryDesc;

    public Category(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }
}
